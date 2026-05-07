package de.trinimon.dictionary.translation.adapter.in.web.mapper;

import de.trinimon.dictionary.translation.adapter.in.web.PageResultResource;
import de.trinimon.dictionary.translation.domain.model.PageResult;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PageResultResourceMapper {

    public <T, R> PageResultResource<R> map(
            PageResult<T> domain,
            Function<T, R> itemMapper
    ) {
        return new PageResultResource<>(
                domain.items().stream().map(itemMapper).toList(),
                domain.page(),
                domain.size(),
                domain.totalElements()
        );
    }
}
