package de.trinimon.dictionary.configuration;

import de.trinimon.dictionary.translation.application.TranslateService;
import de.trinimon.dictionary.translation.application.port.in.TranslateUseCase;
import de.trinimon.dictionary.translation.application.port.out.TranslationStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranslationConfiguration {

    @Bean
    TranslateUseCase translateUseCase(TranslationStore translationStore) {
        return new TranslateService(translationStore);
    }
}
