package ru.practicum.mainservice.events.mapper;

import org.mapstruct.Mapper;
import ru.practicum.mainservice.events.dto.Location;
import ru.practicum.mainservice.events.entity.LocationsEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LocationsMapper {
    LocationsEntity toEntity(Location location);

    Location toDto(LocationsEntity entity);
}
