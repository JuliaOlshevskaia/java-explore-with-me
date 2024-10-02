package ru.practicum.mainservice.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.events.dto.StateEnum;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.requests.entity.RequestsEntity;

import java.util.List;

@Repository
public interface RequestsRepository extends JpaRepository<RequestsEntity, Integer> {
    RequestsEntity findAllByEventIdAndRequesterId(Integer eventId, Integer requesterId);

    List<RequestsEntity> findAllByEventIdAndStatus(Integer eventId, StateEnum status);

    List<RequestsEntity> findAllByRequesterId(Integer requesterId);

    List<RequestsEntity> findAllByEventIn(List<EventsEntity> events);

    List<RequestsEntity> findAllByIdIn(List<Integer> ids);
}
