package ru.practicum.mainservice.users.service;

import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    UserDto registerUser(NewUserRequest request);

    void delete(Integer userId);

    void isUserExists(Integer userId);
}
