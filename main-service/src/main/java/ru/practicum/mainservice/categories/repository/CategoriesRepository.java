package ru.practicum.mainservice.categories.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Integer> {
    List<CategoriesEntity> findAllByIdIn(List<Integer> idCategories);
}
