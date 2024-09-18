package ru.practicum.mainservice.categories.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.dto.NewCategoryDto;
import ru.practicum.mainservice.categories.service.CategoriesService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    private final CategoriesService service;

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto request) {
        return service.addCategory(request);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Integer catId) {
        service.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Integer catId, @RequestBody NewCategoryDto request) {
        return service.updateCategory(catId, request);
    }


}
