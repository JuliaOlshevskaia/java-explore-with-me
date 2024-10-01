package ru.practicum.mainservice.users.mapper;

import lombok.Generated;
import org.mapstruct.Mapper;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;
import ru.practicum.mainservice.users.dto.UserShortDto;
import ru.practicum.mainservice.users.entity.UserEntity;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Generated
@Mapper(componentModel = SPRING)
public interface UserMapper {
    UserEntity toEntity(NewUserRequest newUser);

    UserDto toDto(UserEntity entity);

    List<UserDto> toDtoList(List<UserEntity> entities);

    UserShortDto toShortDto(UserEntity entity);

}
