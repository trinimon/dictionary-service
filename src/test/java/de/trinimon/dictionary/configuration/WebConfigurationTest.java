package de.trinimon.dictionary.configuration;

import de.trinimon.dictionary.translation.adapter.in.web.TranslationController;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.PageResultResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.PagingResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.TranslationResourceMapper;
import de.trinimon.dictionary.translation.application.port.in.TranslateUseCase;
import de.trinimon.dictionary.translation.domain.model.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TranslationController.class)
@Import(WebConfiguration.class)
class WebConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TranslateUseCase translateUseCase;
    @MockitoBean
    private PagingResourceMapper pagingResourceMapper;
    @MockitoBean
    private PageResultResourceMapper pageResultResourceMapper;
    @MockitoBean
    private TranslationResourceMapper translationResourceMapper;

    @Test
    void testEnumConverterRegistration() throws Exception {
        // When
        when(translateUseCase.wordOfTheDay(Language.ENGLISH)).thenReturn(null);
        when(translationResourceMapper.mapFromModel(null)).thenReturn( null);

        mockMvc.perform(get("/translations/word-of-the-day?language=en"))
                .andExpect(status().isOk());
        // Then
        verify(translateUseCase).wordOfTheDay(Language.ENGLISH);
    }
}