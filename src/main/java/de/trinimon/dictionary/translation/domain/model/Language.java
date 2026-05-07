package de.trinimon.dictionary.translation.domain.model;

import de.trinimon.dictionary.translation.domain.service.exceptions.UnsupportedLanguageException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Language {

    GERMAN("DE"),
    ENGLISH("EN"),
    SPANISH("ES");

    private final String isoCode;

    public static Language fromIsoCode(String isoCode) {
        return Arrays.stream(Language.values())
                .filter(enumValue -> enumValue.getIsoCode().equalsIgnoreCase(isoCode)).findFirst()
                .orElseThrow(() -> new UnsupportedLanguageException(
                        String.format("Cannot resolve Language for ISO code '%s'.", isoCode)));

    }

    Language(String isoCode) {
        this.isoCode = isoCode;
    }
}
