package de.trinimon.dictionary.translation.adapter.in.web;

import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalKeywordException;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalPagingException;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedLanguageException;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedTranslationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalPagingException.class)
    public ResponseEntity<String> handleIllegalPagingException(IllegalPagingException exception) {
        return new ResponseEntity<>("Invalid paging parameters: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedTranslationException.class)
    public ResponseEntity<String> handleUnsupportedTranslationException(UnsupportedTranslationException exception) {
        return new ResponseEntity<>("Translation unsupported: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalKeywordException.class)
    public ResponseEntity<String> handleIllegalKeywordException(IllegalKeywordException exception) {
        return new ResponseEntity<>("Invalid keyword: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedLanguageException.class)
    public ResponseEntity<String> handleUnsupportedLanguageException(UnsupportedLanguageException exception) {
        return new ResponseEntity<>("Language is not supported: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        Throwable throwable = exception.getCause();
        while (throwable != null) {
            if (throwable instanceof UnsupportedLanguageException matchingException) {
                return new ResponseEntity<>("Language is not supported: " + matchingException.getMessage(), HttpStatus.BAD_REQUEST);
            }
            throwable = throwable.getCause();
        }
        return ResponseEntity.badRequest()
                .body("Invalid value for parameter '" + exception.getName() + "': " + exception.getValue());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
