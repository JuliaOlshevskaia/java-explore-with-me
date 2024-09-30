package ru.practicum.mainservice.compilations.mapper;

import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.compilations.dto.CompilationDto;
import ru.practicum.mainservice.compilations.dto.NewCompilationDto;
import ru.practicum.mainservice.compilations.entity.CompilationEntity;
import ru.practicum.mainservice.compilations.entity.CompilationEventEntity;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;
import ru.practicum.mainservice.users.dto.UserShortDto;
import ru.practicum.mainservice.users.entity.UserEntity;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Generated
@Mapper(componentModel = SPRING)
public interface CompilationMapper {
//    @Mapping(target = "events", source = null)
//    CompilationEntity toEntity(NewCompilationDto newCompilation);
    CompilationDto toDto(CompilationEntity entity);

}
