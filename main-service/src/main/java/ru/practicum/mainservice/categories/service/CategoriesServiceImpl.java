package ru.practicum.mainservice.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.dto.NewCategoryDto;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;
import ru.practicum.mainservice.categories.mapper.CategoriesMapper;
import ru.practicum.mainservice.categories.repository.CategoriesRepository;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;
import ru.practicum.mainservice.users.entity.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository repository;
    private final CategoriesMapper mapper;

    @Override
    public CategoryDto addCategory(NewCategoryDto request) {
        CategoriesEntity entity = mapper.toEntity(request);
        CategoriesEntity entityCreated = repository.save(entity);
        return mapper.toDto(entityCreated);
    }

    @Override
    public CategoryDto updateCategory(Integer catId, NewCategoryDto request) {
        CategoriesEntity entity = findCategoryById(catId);
        entity.setName(request.getName());
        CategoriesEntity entityChanged = repository.save(entity);
        return mapper.toDto(entityChanged);
    }

    @Override
    public void deleteCategory(Integer catId) {
        CategoriesEntity entity = findCategoryById(catId);
        repository.delete(entity);
    }

    public CategoriesEntity findCategoryById(Integer categoryId) {
        return repository.findById(categoryId).get();
    }
}
