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
import ru.practicum.mainservice.errors.exceptions.DataNotFoundException;
import ru.practicum.mainservice.errors.exceptions.EventValidationException;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.events.repository.EventsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository repository;
    private final CategoriesMapper mapper;
    private final EventsRepository eventsRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto request) {
        CategoriesEntity entity = mapper.toEntity(request);
        CategoriesEntity entityCreated = repository.save(entity);
        return mapper.toDto(entityCreated);
    }

    @Override
    public CategoryDto updateCategory(Integer catId, NewCategoryDto request) {
        if (!(repository.existsById(catId))) {
            throw new DataNotFoundException("Category with id=" + catId + " was not found");
        }

        CategoriesEntity entity = findCategoryById(catId);
        entity.setName(request.getName());
        CategoriesEntity entityChanged = repository.save(entity);
        return mapper.toDto(entityChanged);
    }

    @Override
    public void deleteCategory(Integer catId) {
        if (!(repository.existsById(catId))) {
            throw new DataNotFoundException("Category with id=" + catId + " was not found");
        }
        List<EventsEntity> eventsEntities = eventsRepository.findAllByCategoryId(catId);
        if (eventsEntities.size() > 0) {
            throw new EventValidationException("The category is not empty");
        }
        CategoriesEntity entity = findCategoryById(catId);
        repository.delete(entity);
    }

    public CategoriesEntity findCategoryById(Integer categoryId) {
        return repository.findById(categoryId).get();
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);
        List<CategoriesEntity> entities = repository.findAll(pageParam).toList();
        return mapper.toDtoList(entities);
    }

    @Override
    public CategoryDto getCategory(Integer catId) {
        if (!(repository.existsById(catId))) {
            throw new DataNotFoundException("Category with id=" + catId + " was not found");
        }

        CategoriesEntity entity = repository.findById(catId).get();
        return mapper.toDto(entity);
    }
}
