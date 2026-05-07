package de.trinimon.dictionary.translation.adapter.in.web.mapper;

import de.trinimon.dictionary.translation.adapter.in.web.PagingResource;
import de.trinimon.dictionary.translation.domain.model.Paging;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagingResourceMapper {

    Paging mapFromResource(PagingResource resource);

}
