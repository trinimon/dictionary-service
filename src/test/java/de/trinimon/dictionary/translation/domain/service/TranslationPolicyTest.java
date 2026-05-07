package de.trinimon.dictionary.translation.domain.service;

import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalKeywordException;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalPagingException;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedTranslationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TranslationPolicyTest {

    @Test
    void assertValidKeywordAcceptsNonBlankKeyword() {
        assertDoesNotThrow(() -> TranslationPolicy.assertValidKeyword("keyword"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void assertValidKeywordRejectsBlankKeyword(String value) {
        assertThatThrownBy(() -> TranslationPolicy.assertValidKeyword(value))
                .isInstanceOf(IllegalKeywordException.class)
                .hasMessage("The keyword must not be blank.");
    }

    @ParameterizedTest
    @MethodSource("validLanguagePairs")
    void assertTranslationAllowedAcceptsSupportedLanguagePair(Language sourceLanguage, Language targetLanguage) {
        assertDoesNotThrow(() -> TranslationPolicy.assertTranslationAllowed(sourceLanguage, targetLanguage));
    }

    static Stream<Arguments> validLanguagePairs() {
        return Stream.of(
                arguments(Language.GERMAN, Language.ENGLISH),
                arguments(Language.GERMAN, Language.SPANISH),
                arguments(Language.ENGLISH, Language.GERMAN),
                arguments(Language.SPANISH, Language.GERMAN));
    }

    @ParameterizedTest
    @MethodSource("invalidLanguagePairs")
    void assertTranslationAllowedRejectsUnsupportedLanguagePair(Language sourceLanguage, Language targetLanguage) {
        assertThatThrownBy(() -> TranslationPolicy.assertTranslationAllowed(sourceLanguage, targetLanguage))
                .isInstanceOf(UnsupportedTranslationException.class)
                .hasMessageStartingWith("Translation is not supported: ");
    }

    static Stream<Arguments> invalidLanguagePairs() {
        return Stream.of(
                arguments(Language.GERMAN, Language.GERMAN),
                arguments(Language.ENGLISH, Language.ENGLISH),
                arguments(Language.SPANISH, Language.SPANISH),
                arguments(Language.ENGLISH, Language.SPANISH),
                arguments(Language.SPANISH, Language.ENGLISH));
    }

    @Test
    void assertValidPagingAcceptsValidPaging() {
        assertDoesNotThrow(() -> TranslationPolicy.assertValidPaging(new Paging(0, 1)));
        assertDoesNotThrow(() -> TranslationPolicy.assertValidPaging(new Paging(1, 100)));
    }

    @ParameterizedTest
    @MethodSource("invalidPaging")
    void assertValidPagingRejectsNegativePage(int page, int size) {
        assertThatThrownBy(() -> TranslationPolicy.assertValidPaging(new Paging(page, size)))
                .isInstanceOf(IllegalPagingException.class)
                .hasMessageStartingWith("Paging parameter are invalid: ");
    }

    static Stream<Arguments> invalidPaging() {
        return Stream.of(arguments(-1, 10), arguments(0, 0), arguments(0, 1000));
    }
}
