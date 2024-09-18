package ru.practicum.mainservice.requests.mapper;

import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;
import ru.practicum.mainservice.requests.entity.RequestsEntity;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;
import ru.practicum.mainservice.users.dto.UserShortDto;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Generated
@Mapper(componentModel = SPRING)
public interface RequestsMapper {
    @Mapping(target = "requester", source = "entity.requester.id")
    @Mapping(target = "event", source = "entity.event.id")
    ParticipationRequestDto toDto(RequestsEntity entity);

    @Mapping(target = "requester", source = "entity.requester.id")
    @Mapping(target = "event", source = "entity.event.id")
    List<ParticipationRequestDto> toListDto(List<RequestsEntity> entity);
}
