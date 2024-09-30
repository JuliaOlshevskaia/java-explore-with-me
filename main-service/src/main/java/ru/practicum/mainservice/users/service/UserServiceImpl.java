package ru.practicum.mainservice.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.errors.exceptions.DataNotFoundException;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;
import ru.practicum.mainservice.users.entity.UserEntity;
import ru.practicum.mainservice.users.mapper.UserMapper;
import ru.practicum.mainservice.users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);
        List<UserEntity> entities;
        if (ids == null || ids.size() == 0) {
            entities = repository.findAll(pageParam).getContent();
        } else {
            entities = repository.findAllByIdIn(ids, pageParam);
        }
        return mapper.toDtoList(entities);
    }

    @Override
    public UserDto registerUser(NewUserRequest request) {
        UserEntity entity = mapper.toEntity(request);
        UserEntity entityCreated = repository.save(entity);
        return mapper.toDto(entityCreated);
    }

    @Override
    public void delete(Integer userId) {
        UserEntity entity = findUserById(userId);
        repository.delete(entity);
    }

    public UserEntity findUserById(Integer userId) {
        isUserExists(userId);
        return repository.findById(userId).get();
    }

    @Override
    public void isUserExists(Integer userId) {
        if (!(repository.existsById(userId))) {
            throw new DataNotFoundException("User with id=" + userId + " was not found");
        }
    }
}
