package de.trinimon.dictionary.translation.domain.service.exceptions;

public class UnsupportedLanguageException extends RuntimeException {
    public UnsupportedLanguageException(String message) {
        super(message);
    }
}
