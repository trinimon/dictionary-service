package de.trinimon.dictionary.configuration;

import de.trinimon.dictionary.translation.application.port.in.TranslateUseCase;
import de.trinimon.dictionary.translation.application.port.out.TranslationStore;
import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TranslationConfiguration.class)
class TranslationConfigurationTest {

    @MockitoBean
    TranslationStore translationStore;

    @Autowired
    TranslateUseCase useCase;

    @Test
    void wiresTranslateUseCaseAndDelegatesToStore() {
        // Given
        Paging paging = new Paging(0, 1);
        PageResult<Translation> resultMock = new PageResult<>(List.of(), 0, 1, 1);
        // When
        Mockito.when(translationStore.findByKeyword(
                Language.GERMAN.getIsoCode(),
                Language.ENGLISH.getIsoCode(), "word", paging)).thenReturn(resultMock);
        PageResult<Translation> result = useCase.translate(Language.GERMAN, Language.ENGLISH, "word", paging);
        // Then
        assertSame(resultMock, result);
    }
}