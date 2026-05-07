package de.trinimon.dictionary.translation.adapter.out.persistence.mixin;

import de.trinimon.dictionary.translation.adapter.out.persistence.JpaTranslationRepository;
import de.trinimon.dictionary.translation.adapter.out.persistence.TranslationEntity;
import de.trinimon.dictionary.translation.domain.model.Paging;
import org.instancio.Instancio;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.when;

public interface TranslationEntityMixIn {

    default TranslationEntity translationEntityExistsFor(String sourceLanguage, String targetLanguage, Paging paging) {
        TranslationEntity entity = Instancio.create(TranslationEntity.class);
        Pageable pageable = PageRequest.of(paging.page(), paging.size());

        when(getRepository().findByKeyword(sourceLanguage, targetLanguage, entity.getSource(), pageable))
                .thenReturn(new PageImpl<>(List.of(entity), pageable, 1));

        return entity;
    }

    default TranslationEntity wordOfTheDayExists(String language) {
        TranslationEntity entity = Instancio.create(TranslationEntity.class);

        when(getRepository().wordOfTheDay(language)).thenReturn(entity);

        return entity;
    }

    JpaTranslationRepository getRepository();

}
