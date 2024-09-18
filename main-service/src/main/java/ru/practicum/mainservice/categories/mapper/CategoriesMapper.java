package ru.practicum.mainservice.categories.mapper;

import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.dto.NewCategoryDto;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Generated
@Mapper(componentModel = SPRING)
public interface CategoriesMapper {
    CategoriesEntity toEntity(NewCategoryDto newCategory);
    CategoryDto toDto(CategoriesEntity entity);
    List<CategoryDto> toDtoList(List<CategoriesEntity> entities);

}
