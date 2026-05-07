package de.trinimon.dictionary.translation.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TranslationResource", description = "A translation pair with searched keyword and found translation")
public record TranslationResource(

        @Schema(description = "Text to translate", example = "word")
        String source,

        @Schema(description = "Translated text", example = "Wort")
        String target
) {
}
