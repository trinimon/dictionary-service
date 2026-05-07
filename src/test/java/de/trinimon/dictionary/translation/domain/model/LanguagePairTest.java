package de.trinimon.dictionary.translation.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LanguagePairTest {

    @Test
    void ofCreatesPair() {
        LanguagePair pair = LanguagePair.of(Language.GERMAN, Language.ENGLISH);

        assertEquals(Language.GERMAN, pair.source());
        assertEquals(Language.ENGLISH, pair.target());
    }

    @Test
    void constructorRejectsNullSource() {
        assertThrows(NullPointerException.class, () -> new LanguagePair(null, Language.ENGLISH));
    }

    @Test
    void constructorRejectsNullTarget() {
        assertThrows(NullPointerException.class, () -> new LanguagePair(Language.GERMAN, null));
    }
}
