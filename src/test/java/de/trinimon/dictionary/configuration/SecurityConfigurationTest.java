package de.trinimon.dictionary.configuration;

import de.trinimon.dictionary.translation.adapter.in.web.TranslationController;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.PageResultResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.PagingResourceMapper;
import de.trinimon.dictionary.translation.adapter.in.web.mapper.TranslationResourceMapper;
import de.trinimon.dictionary.translation.application.port.in.TranslateUseCase;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TranslationController.class)
@Import({
        SecurityConfiguration.class,
        SecurityConfigurationTest.BeanTestConfiguration.class
})
@TestPropertySource(properties = {
        "de.trinimon.security.enabled=true"
})
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TranslateUseCase translateUseCase;

    @MockitoBean
    JwtDecoder jwtDecoder;

    @Test
    void wordOfTheDayReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/translations/word-of-the-day")
                        .param("language", "es"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void wordOfTheDayReturnsOkWithMappedResource() throws Exception {
        mockMvc.perform(get("/api/translations/word-of-the-day")
                        .param("language", "es")
                        .with(jwt()))
                .andExpect(status().isOk());
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