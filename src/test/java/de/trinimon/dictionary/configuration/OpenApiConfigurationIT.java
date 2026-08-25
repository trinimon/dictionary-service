package de.trinimon.dictionary.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureRestTestClient
@ActiveProfiles("it")
class OpenApiConfigurationIT {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void apiDocsEndpointIsAvailable() {
        restTestClient.get()
                .uri("/v3/api-docs")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.info.title").isEqualTo("Trinimon Dictionaries")
                .jsonPath("$.info.version").isEqualTo("1.0")
                .jsonPath("$.servers[0].url").isEqualTo("http://localhost:8001")
                .jsonPath("$.servers[0].description").isEqualTo("Local application");
    }

    @Test
    void swaggerUiIsAvailable() {
        restTestClient.get()
                .uri("/swagger-ui/index.html")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith("text/html")
                .expectBody(String.class)
                .consumeWith(result -> {
                    String body = result.getResponseBody();
                    org.assertj.core.api.Assertions.assertThat(body).isNotBlank();
                    org.assertj.core.api.Assertions.assertThat(body.toLowerCase())
                            .contains("<html")
                            .contains("swagger");
                });
    }
}