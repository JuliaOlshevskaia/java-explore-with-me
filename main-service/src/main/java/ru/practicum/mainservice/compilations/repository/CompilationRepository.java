package ru.practicum.mainservice.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.compilations.entity.CompilationEntity;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<CompilationEntity, Integer> {
    List<CompilationEntity> findAllByPinnedIs(Boolean pinned, Pageable pageable);
}
