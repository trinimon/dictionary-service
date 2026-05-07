package de.trinimon.dictionary.translation.adapter.in.web;

import de.trinimon.dictionary.translation.adapter.in.web.mapper.PageResultResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.PagingResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.TranslationResourceMapper;
import de.trinimon.dictionary.translation.application.port.in.TranslateUseCase;
import de.trinimon.dictionary.translation.domain.model.Language;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class TranslationController {

    private final TranslateUseCase translateUseCase;
    private final TranslationResourceMapper translationResourceMapper;
    private final PagingResourceMapper pagingResourceMapper;
    private final PageResultResourceMapper pageResultResourceMapper;

    @Operation(
            summary = "Translates keywords",
            description = "Returns paginated translations matching the keyword"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with paginated results",
            content = @Content(schema = @Schema(implementation = PageResultResource.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid parameters")
    @GetMapping("/translations")
    public ResponseEntity<PageResultResource<TranslationResource>> translate(
            @Parameter(
                    description = "ISO language code",
                    schema = @Schema(type = "string", allowableValues = {"DE", "EN", "ES"}))
            @RequestParam("sourceLanguage") Language sourceLanguage,
            @Parameter(
                    description = "ISO language code",
                    schema = @Schema(type = "string", allowableValues = {"EN", "ES", "DE"}))
            @RequestParam("targetLanguage") Language targetLanguage,
            @Parameter(
                    description = "Word to translate",
                    schema = @Schema(type = "string"))
            @RequestParam("keyword") String keyword,
            @ParameterObject PagingResource pagingResource) {
        return new ResponseEntity<>(pageResultResourceMapper.map(
                translateUseCase.translate(
                        sourceLanguage, targetLanguage, sanitize(keyword),
                        pagingResourceMapper.mapFromResource(pagingResource)),
                translationResourceMapper::mapFromModel), HttpStatus.OK);
    }

    @Operation(
            summary = "Word of the day",
            description = "Returns the word of the day according to the given language"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful response with word of the day and its translation",
            content = @Content(schema = @Schema(implementation = TranslationResource.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid parameter")
    @GetMapping("/translations/word-of-the-day")
    public ResponseEntity<TranslationResource> wordOfTheDay(
            @Parameter(
                    description = "ISO language code",
                    schema = @Schema(type = "string", allowableValues = {"DE", "EN", "ES"}))
            @RequestParam("language") Language language) {
        return new ResponseEntity<>(translationResourceMapper.mapFromModel(translateUseCase.wordOfTheDay(language)), HttpStatus.OK);
    }

    private static String sanitize(String keyword) {
        return Objects.requireNonNullElse(keyword, "").trim()
                .replaceAll("[\\\\%_]", "");
    }

}
