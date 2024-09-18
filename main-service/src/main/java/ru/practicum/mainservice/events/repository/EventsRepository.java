package ru.practicum.mainservice.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.events.dto.StateEnum;
import ru.practicum.mainservice.events.entity.EventsEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<EventsEntity, Integer> {
    List<EventsEntity> findAllByInitiatorId(Integer userId, Pageable pageable);
    EventsEntity findAllByIdAndInitiatorId(Integer eventId, Integer userId);
    List<EventsEntity> findAllByInitiatorId(Integer userId);
    List<EventsEntity> findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Integer> usersId,
                                                                                                          List<String> statesId,
                                                                                                          List<Integer> categoriesId,
                                                                                                          LocalDateTime startDate,
                                                                                                          LocalDateTime endDate,
                                                                                                          Pageable pageable);
}
