package de.trinimon.dictionary.instancio;

import de.trinimon.dictionary.translation.domain.model.Paging;
import org.instancio.Random;
import org.instancio.generator.Generator;

public class PagingGenerator implements Generator<Paging> {

    public Paging generate(Random random) {
        return new Paging(0,10);
    }
}
