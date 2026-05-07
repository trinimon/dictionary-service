package de.trinimon.dictionary.translation.adapter.in.web.mapper;

import de.trinimon.dictionary.translation.adapter.in.web.TranslationResource;
import de.trinimon.dictionary.translation.domain.model.Translation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslationResourceMapper {

    TranslationResource mapFromModel(Translation translation);

}
