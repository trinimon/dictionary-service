package de.trinimon.dictionary.translation.adapter.out.persistence;

import de.trinimon.dictionary.translation.adapter.out.persistence.mixin.TranslationEntityMixIn;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslationStoreAdapterTest implements TranslationEntityMixIn {

    @Mock
    private JpaTranslationRepository repository;

    @Mock
    private TranslationMapper mapper;

    private TranslationStoreAdapter adapter;

    @BeforeEach
    void init() {
        adapter = new TranslationStoreAdapter(repository, mapper);
    }

    @Test
    void findByKeywordMapsPageFromRepository() {
        // Given
        Paging paging = Instancio.create(Paging.class);
        TranslationEntity entity = translationEntityExistsFor("DE", "EN", paging);
        Translation translation = Instancio.create(Translation.class);

        // When
        when(mapper.mapFromEntity(entity)).thenReturn(translation);
        PageResult<Translation> result = adapter.findByKeyword("DE", "EN", entity.getSource(), paging);

        // Then
        PageResult<Translation> expected = new PageResult<>(List.of(translation), paging.page(), paging.size(), 1);
        assertEquals(expected, result);
    }

    @Test
    void wordOfTheDayMapsEntityFromRepository() {
        // Given
        TranslationEntity entity = wordOfTheDayExists("ES");
        Translation translation = Instancio.create(Translation.class);

        // When
        when(mapper.mapFromEntity(entity)).thenReturn(translation);
        Translation result = adapter.wordOfTheDay("ES");

        //  Then
        assertSame(translation, result);
    }

    @Override
    public JpaTranslationRepository getRepository() {
        return repository;
    }
}
