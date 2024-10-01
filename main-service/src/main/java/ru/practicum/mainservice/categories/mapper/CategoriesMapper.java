package ru.practicum.mainservice.categories.mapper;

import org.mapstruct.Mapper;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.dto.NewCategoryDto;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoriesMapper {
    CategoriesEntity toEntity(NewCategoryDto newCategory);

    CategoryDto toDto(CategoriesEntity entity);

    List<CategoryDto> toDtoList(List<CategoriesEntity> entities);

}
