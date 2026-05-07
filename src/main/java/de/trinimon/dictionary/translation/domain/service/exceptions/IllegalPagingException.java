package de.trinimon.dictionary.translation.domain.service.exceptions;

public class IllegalPagingException extends RuntimeException {
    public IllegalPagingException(String message) {
        super(message);
    }
}
