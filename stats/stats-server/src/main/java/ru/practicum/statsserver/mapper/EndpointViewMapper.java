package ru.practicum.statsserver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.statsdto.dto.EndpointHit;
import ru.practicum.statsserver.entity.EndpointViewEntity;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EndpointViewMapper {
    EndpointHit toEndpointHit(EndpointViewEntity endpointViewEntity);

    @Mapping(target = "timestamp", source = "date")
    EndpointViewEntity toEntity(EndpointHit endpointHit, LocalDateTime date);
}
