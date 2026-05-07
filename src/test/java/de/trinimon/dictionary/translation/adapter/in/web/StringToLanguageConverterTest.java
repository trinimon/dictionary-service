package de.trinimon.dictionary.translation.adapter.in.web;

import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedLanguageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringToLanguageConverterTest {

    private final StringToLanguageConverter converter = new StringToLanguageConverter();

    @ParameterizedTest
    @CsvSource({"de, GERMAN", "DE, GERMAN", "en, ENGLISH", "EN, ENGLISH", "es, SPANISH", "ES, SPANISH"})
    void convertResolvesLanguageCaseInsensitive(String isoCode, Language language) {
        assertEquals(language, converter.convert(isoCode));
    }

    @Test
    void convertRejectsNull() {
        assertThatThrownBy(() ->
                converter.convert(null))
                .isInstanceOf(UnsupportedLanguageException.class)
                .hasMessageStartingWith("");
    }
}
