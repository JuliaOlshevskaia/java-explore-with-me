package ru.practicum.mainservice.compilations.mapper;

import lombok.Generated;
import org.mapstruct.Mapper;
import ru.practicum.mainservice.compilations.dto.CompilationDto;
import ru.practicum.mainservice.compilations.entity.CompilationEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Generated
@Mapper(componentModel = SPRING)
public interface CompilationMapper {
//    @Mapping(target = "events", source = null)
//    CompilationEntity toEntity(NewCompilationDto newCompilation);
    CompilationDto toDto(CompilationEntity entity);

}
