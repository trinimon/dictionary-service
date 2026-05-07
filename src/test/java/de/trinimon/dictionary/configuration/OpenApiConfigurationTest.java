package de.trinimon.dictionary.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OpenApiConfiguration.class)
class OpenApiConfigurationTest {

    @Autowired
    OpenApiConfiguration config;

    @Test
    void openApiConfigurationGetsLoaded() {
        assertThat(config).isNotNull();
    }
}