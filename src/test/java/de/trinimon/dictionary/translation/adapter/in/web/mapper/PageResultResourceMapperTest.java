package de.trinimon.dictionary.translation.adapter.in.web.mapper;

import de.trinimon.dictionary.translation.adapter.in.web.PageResultResource;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageResultResourceMapperTest {

    private final PageResultResourceMapper mapper = new PageResultResourceMapper();

    @Test
    void mapTransformsItemsAndKeepsMetadata() {
        PageResult<String> domain = new PageResult<>(List.of("a", "bb"), 1, 5, 12);

        PageResultResource<Integer> result = mapper.map(domain, String::length);

        assertEquals(List.of(1, 2), result.items());
        assertEquals(1, result.page());
        assertEquals(5, result.size());
        assertEquals(12, result.total());
    }
}
