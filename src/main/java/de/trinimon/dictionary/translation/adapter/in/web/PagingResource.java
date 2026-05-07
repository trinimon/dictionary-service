package de.trinimon.dictionary.translation.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PagingResource", description = "Provides page and size values for pagination")
public record PagingResource(

        @Schema(example = "0", description = "Zero based page number")
        int page,

        @Schema(example = "10", description = "Page size")
        int size
) {
}