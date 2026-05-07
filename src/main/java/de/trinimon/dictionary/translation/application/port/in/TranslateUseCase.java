package de.trinimon.dictionary.translation.application.port.in;

import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;

public interface TranslateUseCase {

    PageResult<Translation> translate(Language sourceLanguage, Language targetLanguage, String keyword, Paging paging);

    Translation wordOfTheDay(Language language);

}
