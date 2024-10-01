package ru.practicum.mainservice.requests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;
import ru.practicum.mainservice.requests.entity.RequestsEntity;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RequestsMapper {
    @Mapping(target = "requester", source = "entity.requester.id")
    @Mapping(target = "event", source = "entity.event.id")
    ParticipationRequestDto toDto(RequestsEntity entity);

    @Mapping(target = "requester", source = "entity.requester.id")
    @Mapping(target = "event", source = "entity.event.id")
    List<ParticipationRequestDto> toListDto(List<RequestsEntity> entity);
}
