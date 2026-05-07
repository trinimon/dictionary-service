package de.trinimon.dictionary.instancio;

import org.instancio.Node;
import org.instancio.Random;
import org.instancio.generator.Generator;

public class FieldNameStringGenerator implements Generator<String> {
    private final Node node;

    public FieldNameStringGenerator(Node node) {
        this.node = node;
    }

    public String generate(Random random) {
        if (node.getField() == null) {
            return "string";
        } else {
            return node.getField().getName();
        }
    }
}
