package de.trinimon.dictionary.translation.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "PageResultResource", description = "Provides a page of matching translations including paging information")
public record PageResultResource<T>(

        @Schema(description = "List of items in the result page")
        List<T> items,

        @Schema(example = "0", description = "Zero based page number")
        int page,

        @Schema(example = "10", description = "Page size requested")
        int size,

        @Schema(example = "17", description = "Number of matching translations in total")
        long total
) {
}
