package ru.practicum.mainservice.events.mapper;

import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.Location;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.events.entity.LocationsEntity;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Generated
@Mapper(componentModel = SPRING)
public interface LocationsMapper {
    LocationsEntity toEntity(Location location);
    Location toDto(LocationsEntity entity);
}
