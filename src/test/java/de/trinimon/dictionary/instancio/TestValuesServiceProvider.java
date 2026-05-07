package de.trinimon.dictionary.instancio;

import de.trinimon.dictionary.translation.domain.model.Paging;
import org.instancio.Node;
import org.instancio.generator.Generator;
import org.instancio.generators.Generators;
import org.instancio.spi.InstancioServiceProvider;

import java.util.HashMap;
import java.util.Map;

public class TestValuesServiceProvider implements InstancioServiceProvider {

    @Override
    @SuppressWarnings("unused") // random and generatorsApi
    public InstancioServiceProvider.GeneratorProvider getGeneratorProvider() {
        final Map<Class<?>, Generator<?>> generators = new HashMap<>();

        generators.put(Long.class, random -> 0L);
        generators.put(Integer.class, random -> 0);
        generators.put(Short.class, random -> (short) 0);
        generators.put(Byte.class, random -> (byte) 0);
        generators.put(Double.class, random -> 0.0d);
        generators.put(Float.class, random -> 0.0f);
        generators.put(Boolean.class, random -> false);

        return (Node node, Generators generatorsApi) -> {
            if (node.getTargetClass() == String.class) {
                return new FieldNameStringGenerator(node);
            }
            if (node.getTargetClass() == Paging.class) {
                return new PagingGenerator();
            }
            return generators.get(node.getTargetClass());
        };
    }
}
