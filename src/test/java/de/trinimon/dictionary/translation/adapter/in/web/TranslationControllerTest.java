package de.trinimon.dictionary.translation.adapter.in.web;

import de.trinimon.dictionary.translation.adapter.in.web.mapper.PageResultResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.PagingResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.TranslationResourceMapper;
import de.trinimon.dictionary.translation.application.port.in.TranslateUseCase;
import de.trinimon.dictionary.translation.domain.model.Language;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import de.trinimon.dictionary.translation.domain.model.Paging;
import de.trinimon.dictionary.translation.domain.model.Translation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static de.trinimon.dictionary.translation.domain.model.Language.ENGLISH;
import static de.trinimon.dictionary.translation.domain.model.Language.GERMAN;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TranslationController.class)
@Import(TranslationControllerTest.BeanTestConfiguration.class)
class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TranslateUseCase translateUseCase;

    @Test
    void translateReturnsOkWithMappedPageResult() throws Exception {
        Paging paging = new Paging(0, 10);
        Translation translation = new Translation(1L, "Tier", "animal");

        when(translateUseCase.translate(GERMAN, ENGLISH, "tier", paging))
                .thenReturn(new PageResult<>(List.of(translation), 0, 10, 1));

        mockMvc.perform(get("/translations")
                        .param("keyword", "t\\i%e_r") // check sanitation!
                        .param("sourceLanguage", "de")
                        .param("targetLanguage", "en")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].source").value("Tier"))
                .andExpect(jsonPath("$.items[0].target").value("animal"));
    }

    @Test
    void wordOfTheDayReturnsOkWithMappedResource() throws Exception {
        Translation translation = new Translation(2L, "Wort", "word");

        when(translateUseCase.wordOfTheDay(Language.SPANISH)).thenReturn(translation);

        mockMvc.perform(get("/translations/word-of-the-day")
                        .param("language","es"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("Wort"))
                .andExpect(jsonPath("$.target").value("word"));
    }

    @TestConfiguration
    static class BeanTestConfiguration {
        @Bean
        TranslationResourceMapper translationResourceMapper() {
            return Mappers.getMapper(TranslationResourceMapper.class);
        }

        @Bean
        PagingResourceMapper pagingResourceMapper() {
            return Mappers.getMapper(PagingResourceMapper.class);
        }

        @Bean
        PageResultResourceMapper pageResultResourceMapper() {
            return new PageResultResourceMapper();
        }
    }
}