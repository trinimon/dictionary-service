package de.trinimon.dictionary.translation.adapter.out.persistence;

import de.trinimon.dictionary.translation.domain.model.Translation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslationMapper {

    Translation mapFromEntity(TranslationEntity entity);

}
