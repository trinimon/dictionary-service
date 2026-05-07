package de.trinimon.dictionary.translation.adapter.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("it")
class JpaTranslationRepositoryTest {

    @Autowired
    JpaTranslationRepository repo;

    @Test
    @Sql("/scripts/JpaTranslationRepositoryTest.sql")
    void wordOfTheDayReturnsWord() {
        TranslationEntity expected = new TranslationEntity();
        expected.setSource("casa");
        expected.setTarget("Haus");

        assertThat(repo.wordOfTheDay("ES"))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    @Sql("/scripts/JpaTranslationRepositoryTest.sql")
    void findByKeywordReturnsPage() {
        TranslationEntity expected = new TranslationEntity();
        expected.setSource("a car insurance");
        expected.setTarget("eine Auto Versicherung");

        Page<TranslationEntity> result = repo.findByKeyword("EN", "DE", "car", PageRequest.of(0, 2));

        assertEquals(2, result.getContent().size());
        assertEquals(3, result.getTotalElements());
        assertEquals("car", result.getContent().getFirst().getSource());

        assertThat(result.getContent().get(1))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }


}