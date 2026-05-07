package de.trinimon.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DictionaryApplication {

    private DictionaryApplication() {
        /* This utility class should not be instantiated */
    }

    static void main(String[] args) {
        SpringApplication.run(DictionaryApplication.class, args);
    }

}
