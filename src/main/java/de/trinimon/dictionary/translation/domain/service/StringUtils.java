package de.trinimon.dictionary.translation.domain.service;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
