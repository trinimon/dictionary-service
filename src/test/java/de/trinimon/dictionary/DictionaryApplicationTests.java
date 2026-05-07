package de.trinimon.dictionary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("it")
class DictionaryApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void constructorIsPrivate() throws NoSuchMethodException {
		Constructor<DictionaryApplication> constructor = DictionaryApplication.class.getDeclaredConstructor();
		assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
	}

}
