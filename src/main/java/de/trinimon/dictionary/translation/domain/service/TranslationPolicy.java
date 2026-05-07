package de.trinimon.dictionary.translation.domain.service;

import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.model.LanguagePair;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalKeywordException;
import de.trinimon.dictionary.translation.domain.service.exceptions.IllegalPagingException;
import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedTranslationException;

import java.util.Set;

public final class TranslationPolicy {
    private static final Set<LanguagePair> SUPPORTED = Set.of(
            LanguagePair.of(Language.GERMAN, Language.ENGLISH),
            LanguagePair.of(Language.GERMAN, Language.SPANISH),
            LanguagePair.of(Language.SPANISH, Language.GERMAN),
            LanguagePair.of(Language.ENGLISH, Language.GERMAN));

    private TranslationPolicy() {
    }

    public static void assertValidKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new IllegalKeywordException("The keyword must not be blank.");
        }
    }

    public static void assertTranslationAllowed(Language source, Language target) {
        if (!SUPPORTED.contains(LanguagePair.of(source, target))) {
            //noinspection UnnecessaryUnicodeEscape
            throw new UnsupportedTranslationException(
                    "Translation is not supported: %s \u2192 %s".formatted(source, target));
        }
    }

    public static void assertValidPaging(Paging paging) {
        if (paging.page() < 0 || paging.size() <= 0 || paging.size() > 100) {
            //noinspection UnnecessaryUnicodeEscape
            throw new IllegalPagingException(
                    "Paging parameter are invalid: %d \u2192 %d".formatted(paging.page(), paging.size()));
        }
    }

}
