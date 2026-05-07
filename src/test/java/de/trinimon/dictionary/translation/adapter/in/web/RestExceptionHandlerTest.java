package de.trinimon.dictionary.translation.adapter.in.web;

import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalKeywordException;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalPagingException;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedLanguageException;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedTranslationException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestExceptionHandlerTest {

    private final RestExceptionHandler handler = new RestExceptionHandler();

    private final MethodParameter parameter = Mockito.mock(MethodParameter.class);

    @Test
    void handleEntityNotFoundReturnsNotFound() {
        statusAndBodyAreEqual(
                handler.handleEntityNotFound(new EntityNotFoundException("Not found")),
                HttpStatus.NOT_FOUND, "Not found");
    }

    @Test
    void handleIllegalPagingExceptionReturnsBadRequest() {
        statusAndBodyAreEqual(
                handler.handleIllegalPagingException(new IllegalPagingException("Illegal paging")),
                HttpStatus.BAD_REQUEST, "Invalid paging parameters: Illegal paging");
    }

    @Test
    void handleUnsupportedTranslationExceptionReturnsBadRequest() {
        statusAndBodyAreEqual(
                handler.handleUnsupportedTranslationException(new UnsupportedTranslationException("Unsupported pair")),
                HttpStatus.BAD_REQUEST, "Translation unsupported: Unsupported pair");
    }

    @Test
    void handleIllegalKeywordExceptionReturnsBadRequest() {
        statusAndBodyAreEqual(
                handler.handleIllegalKeywordException(new IllegalKeywordException("Blank")),
                HttpStatus.BAD_REQUEST, "Invalid keyword: Blank");
    }

    @Test
    void handleUnsupportedLanguageExceptionReturnsBadRequest() {
        statusAndBodyAreEqual(
                handler.handleUnsupportedLanguageException(new UnsupportedLanguageException("FR")),
                HttpStatus.BAD_REQUEST, "Language is not supported: FR");
    }

    @Test
    void handleExceptionReturnsInternalServerError() {
        statusAndBodyAreEqual(
                handler.handleException(new RuntimeException("Boom")),
                HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
    }

    @Test
    void handleTypeMismatchReturnsLanguageMessageWhenCauseChainContainsUnsupportedLanguage() {
        statusAndBodyAreEqual(
                handler.handleTypeMismatch(
                        new MethodArgumentTypeMismatchException("FR", String.class, "from", parameter,
                                new RuntimeException(new UnsupportedLanguageException("Cannot resolve Language")))),
                HttpStatus.BAD_REQUEST, "Language is not supported: Cannot resolve Language");
    }

    @Test
    void handleTypeMismatchReturnsGenericMessageForOtherErrors() {
        statusAndBodyAreEqual(
                handler.handleTypeMismatch(
                        new MethodArgumentTypeMismatchException(-1, Integer.class, "size", parameter,
                                new RuntimeException("Other"))),
                HttpStatus.BAD_REQUEST, "Invalid value for parameter 'size': -1");
    }

    private static void statusAndBodyAreEqual(ResponseEntity<String> response, HttpStatus status, String body) {
        assertEquals(status, response.getStatusCode());
        assertEquals(body, response.getBody());
    }

}
