package de.trinimon.dictionary.translation.application;

import de.trinimon.dictionary.translation.application.port.out.TranslationStore;
import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalKeywordException;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalPagingException;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedTranslationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static de.trinimon.dictionary.translation.domain.model.Language.ENGLISH;
import static de.trinimon.dictionary.translation.domain.model.Language.GERMAN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslateServiceTest {

    @Mock
    private TranslationStore translationStore;

    private TranslateService service;

    @BeforeEach
    void init() {
        service = new TranslateService(translationStore);
    }

    @Test
    void translateReturnsSearchResult() {
        Paging paging = new Paging(1, 10);
        PageResult<Translation> expected = new PageResult<>(List.of(new Translation(1L, "Wort", "word")), 1, 10, 1);

        when(translationStore.findByKeyword(
                GERMAN.getIsoCode(),
                ENGLISH.getIsoCode(), "wort", paging)).thenReturn(expected).thenReturn(/* no 2nd call */ null);

        assertSame(expected, service.translate(GERMAN, ENGLISH, "wort", paging));
    }

    @Test
    void translateRejectsBlankKeyword() {
        assertThatThrownBy(() ->
                service.translate(GERMAN, ENGLISH, "   ", new Paging(1, 10)))
                .isInstanceOf(IllegalKeywordException.class);
        verifyNoInteractions(translationStore);
    }

    @Test
    void translateRejectsUnsupportedLanguagePair() {
        assertThatThrownBy(() ->
                service.translate(GERMAN, GERMAN, "Wort", new Paging(1, 10)))
                .isInstanceOf(UnsupportedTranslationException.class);
        verifyNoInteractions(translationStore);
    }

    @Test
    void translateRejectsInvalidPaging() {
        assertThatThrownBy(() ->
                service.translate(GERMAN, ENGLISH, "Wort", new Paging(1, 0)))
                .isInstanceOf(IllegalPagingException.class);
        verifyNoInteractions(translationStore);
    }

    @Test
    void wordOfTheDayDelegatesToStore() {
        Translation expected = new Translation(2L, "Wort", "word");
        when(translationStore.wordOfTheDay("ES")).thenReturn(expected);
        assertEquals(expected, service.wordOfTheDay(Language.SPANISH));
    }
}
