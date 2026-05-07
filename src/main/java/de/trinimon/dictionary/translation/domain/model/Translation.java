package de.trinimon.dictionary.translation.domain.model;

public record Translation(
        Long id,
        String source,
        String target) {
}