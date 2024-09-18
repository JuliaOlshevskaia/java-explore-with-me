package ru.practicum.mainservice.categories.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.dto.NewCategoryDto;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;

import java.util.List;

public interface CategoriesService {
    CategoryDto addCategory(NewCategoryDto request);
    void deleteCategory(Integer catId);
    CategoryDto updateCategory(Integer catId, NewCategoryDto request);
}
