package ru.practicum.mainservice.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.compilations.entity.CompilationEventEntity;
import ru.practicum.mainservice.users.entity.UserEntity;

import java.util.List;

//@Repository
//public interface CompilationEventRepository extends JpaRepository<CompilationEventEntity, Integer> {
//    List<CompilationEventEntity> findAllByCompilationId(Integer compilation);
//
//    void deleteByCompilationId(Integer compilation);
//}
