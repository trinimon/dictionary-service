package de.trinimon.dictionary.translation.domain.model;

import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedLanguageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LanguageTest {

    @ParameterizedTest
    @CsvSource({"de, GERMAN", "DE, GERMAN", "en, ENGLISH", "EN, ENGLISH", "es, SPANISH", "ES, SPANISH"})
    void fromIsoCodeReturnsLanguageWithoutException(String isoCode, Language language) {
        assertEquals(language, Language.fromIsoCode(isoCode));
    }

    @Test
    void fromIsoCodeThrowsForUnknownIsoCode() {
        UnsupportedLanguageException exception =
                assertThrows(UnsupportedLanguageException.class, () -> Language.fromIsoCode("FR"));
        assertEquals("Cannot resolve Language for ISO code 'FR'.", exception.getMessage());
    }
}
