package ru.practicum.mainservice.events.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.dto.Location;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.users.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EventsMapper {
    @Mapping(target = "category", source = "category")
    @Mapping(target = "eventDate", source = "eventDate")
    EventsEntity toEntity(NewEventDto newCategory, CategoriesEntity category, LocalDateTime eventDate);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "createdOn", source = "createdDate")
    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "id", source = "entity.id")
    EventFullDto toDto(EventsEntity entity, CategoryDto category, UserShortDto initiator, Location location, String createdDate, String eventDate);

    @Mapping(target = "eventDate", source = "eventDate")
    List<EventShortDto> toListShortDto(List<EventsEntity> entities);

    EventShortDto toShortDto(EventsEntity entity);
}
