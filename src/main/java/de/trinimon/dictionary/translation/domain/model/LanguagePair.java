package de.trinimon.dictionary.translation.domain.model;

import java.util.Objects;

public record LanguagePair(Language source, Language target) {

    public LanguagePair {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
    }

    public static LanguagePair of(Language source, Language target) {
        return new LanguagePair(source, target);
    }

}
