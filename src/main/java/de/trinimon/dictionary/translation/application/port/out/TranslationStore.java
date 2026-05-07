package de.trinimon.dictionary.translation.application.port.out;

import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;

public interface TranslationStore {

    PageResult<Translation> findByKeyword(final String sourceLanguage,
                                          final String targetLanguage,
                                          final String keyword, Paging paging);

    Translation wordOfTheDay(final String language);

}