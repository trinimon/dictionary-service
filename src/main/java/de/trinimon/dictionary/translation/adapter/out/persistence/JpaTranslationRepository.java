package de.trinimon.dictionary.translation.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaTranslationRepository extends JpaRepository<TranslationEntity, Long> {

    @Query(name = "Translation.findByKeyword")
    Page<TranslationEntity> findByKeyword(@Param("sourceLanguage") String sourceLanguage,
                                          @Param("targetLanguage") String targetLanguage,
                                          @Param("keyword") String keyword, Pageable pageable);

    @Query(name = "Translation.wordOfTheDay")
    TranslationEntity wordOfTheDay(@Param("language") String language);

}
