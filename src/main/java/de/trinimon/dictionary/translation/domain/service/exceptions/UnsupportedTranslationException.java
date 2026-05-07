package de.trinimon.dictionary.translation.domain.service.exceptions;

public class UnsupportedTranslationException extends RuntimeException {
    public UnsupportedTranslationException(String message) {
        super(message);
    }
}
