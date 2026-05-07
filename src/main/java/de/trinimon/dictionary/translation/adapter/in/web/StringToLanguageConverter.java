package de.trinimon.dictionary.translation.adapter.in.web;


import de.trinimon.dictionary.translation.domain.model.Language;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class StringToLanguageConverter implements Converter<String, Language> {
    @Override
    public Language convert(String source) {
        return Language.fromIsoCode(Objects.requireNonNullElse(source, "").toUpperCase());
    }
}