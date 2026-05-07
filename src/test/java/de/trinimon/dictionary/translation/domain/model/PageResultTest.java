package de.trinimon.dictionary.translation.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageResultTest {

    @Test
    void mapTransformsItemsAndKeepsPagingMetadata() {
        // Given
        PageResult<String> source = new PageResult<>(List.of("x", "xxx"), 0, 100, 2);
        // When: String::length ist mapping function
        PageResult<Integer> mapped = source.map(String::length);
        // Then
        assertEquals(List.of(1, 3), mapped.items());
        assertEquals(0, mapped.page());
        assertEquals(100, mapped.size());
        assertEquals(2, mapped.totalElements());
    }
}
