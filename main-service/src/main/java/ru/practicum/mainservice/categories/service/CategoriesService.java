package ru.practicum.mainservice.categories.service;

import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.dto.NewCategoryDto;

import java.util.List;

public interface CategoriesService {
    CategoryDto addCategory(NewCategoryDto request);

    void deleteCategory(Integer catId);

    CategoryDto updateCategory(Integer catId, NewCategoryDto request);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Integer catId);
}
