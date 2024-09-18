package ru.practicum.mainservice.categories.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.categories.dto.CategoryDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoriesController {

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return null;
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Integer catId) {
        return null;
    }



}
