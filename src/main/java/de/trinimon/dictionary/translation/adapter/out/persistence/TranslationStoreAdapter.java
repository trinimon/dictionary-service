package de.trinimon.dictionary.translation.adapter.out.persistence;

import de.trinimon.dictionary.translation.application.port.out.TranslationStore;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TranslationStoreAdapter implements TranslationStore {

    private final JpaTranslationRepository jpaRepository;
    private final TranslationMapper mapper;

    public TranslationStoreAdapter(final JpaTranslationRepository jpaRepository,
                                   final TranslationMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    public PageResult<Translation> findByKeyword(final String sourceLanguage,
                                                 final String targetLanguage,
                                                 final String keyword, Paging paging) {
        Page<TranslationEntity> page =
                jpaRepository.findByKeyword(
                        sourceLanguage, targetLanguage,
                        keyword, PageRequest.of(paging.page(), paging.size()));

        return new PageResult<>(
                // page.getContent().stream().map(mapper::mapFromEntity).toList(),
                page.stream().map(mapper::mapFromEntity).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }

    @Cacheable(
            cacheNames = "wordOfTheDay",
            key = "#language + ':' + T(java.time.LocalDate).now(T(java.time.ZoneId).of('Europe/Berlin'))"
    )
    public Translation wordOfTheDay(String language) {
        return mapper.mapFromEntity(jpaRepository.wordOfTheDay(language));
    }

}
