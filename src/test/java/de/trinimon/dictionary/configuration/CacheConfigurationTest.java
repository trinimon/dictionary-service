package de.trinimon.dictionary.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CacheConfiguration.class)
@TestPropertySource(properties = "de.trinimon.dictionary.cache.enabled=true")
class CacheConfigurationTest {

    @Autowired
    CacheManager cacheManager;

    @Test
    void cacheManagerGetsLoaded() {
        assertThat(cacheManager).isNotNull();
        assertThat(cacheManager.getCache("wordOfTheDay")).isNotNull();
    }
}