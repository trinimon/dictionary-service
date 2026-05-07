package de.trinimon.dictionary.translation.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    @SuppressWarnings("ConstantConditions")
    void isBlankReturnsTrueForNull() {
        assertTrue(StringUtils.isBlank(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void isBlankReturnsTrueForBlankStrings(String value) {
        assertTrue(StringUtils.isBlank(value));
    }

    @Test
    void isBlankReturnsFalseForNonBlankString() {
        assertFalse(StringUtils.isBlank("value"));
    }
}
