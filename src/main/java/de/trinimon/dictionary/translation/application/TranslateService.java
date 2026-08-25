package de.trinimon.dictionary.translation.application;

import de.trinimon.dictionary.translation.application.port.in.TranslateUseCase;
import de.trinimon.dictionary.translation.application.port.out.TranslationStore;
import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;
import lombok.AllArgsConstructor;

import java.util.Locale;

import static de.trinimon.dictionary.translation.domain.service.TranslationPolicy.*;

@AllArgsConstructor
public class TranslateService implements TranslateUseCase {

    private final TranslationStore translationStore;

    public PageResult<Translation> translate(Language sourceLanguage, Language targetLanguage, String keyword, Paging paging) {
        assertValidPaging(paging);
        assertValidKeyword(keyword);
        assertTranslationAllowed(sourceLanguage, targetLanguage);

        return translationStore.findByKeyword(
                sourceLanguage.getIsoCode(),
                targetLanguage.getIsoCode(),
                lowercaseForCaseInsensitiveSearch(keyword), paging);
    }

    public Translation wordOfTheDay(Language language) {
        return translationStore.wordOfTheDay(language.getIsoCode());
    }

    private String lowercaseForCaseInsensitiveSearch(String keyword) {
        return keyword.toLowerCase(Locale.ROOT);
    }

}
