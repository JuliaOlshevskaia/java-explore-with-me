package ru.practicum.mainservice.events.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.events.dto.StateEnum;
import ru.practicum.mainservice.events.entity.EventsEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<EventsEntity, Integer> {
    @Query("select ee from EventsEntity ee " +
            "where ee.initiator.id = (:usersId) "
    )
    List<EventsEntity> getByUser(@Param("usersId") Integer userId, Pageable pageable);

    Page<EventsEntity> findAll(Pageable pageable);

    EventsEntity findAllByIdAndInitiatorId(Integer eventId, Integer userId);

    List<EventsEntity> findAllByInitiatorId(Integer userId);

    List<EventsEntity> findAllByCategoryId(Integer catId);

    @Query("select ee from EventsEntity ee " +
            "where ee.initiator.id in (:usersId) "
//            "and ee.category.id in (:categoriesId) " +
//            "and ee.state in (:statesId) " +
//            "and ee.eventDate > (:rangeStart) " +
//            "and ee.eventDate < (:rangeEnd) "
    )
    List<EventsEntity> getAllEvents(List<Integer> usersId,
//                                                                                                          List<StateEnum> statesId,
//                                                                                                          List<Integer> categoriesId,
//                                                                                                          LocalDateTime rangeStart,
//                                                                                                          LocalDateTime rangeEnd,
                                                                                                          Pageable pageable);

    List<EventsEntity> findAllByInitiatorIdInAndStateInAndCategoryIdIn(List<Integer> usersId,
                                                                                                          List<StateEnum> statesId,
                                                                                                          List<Integer> categoriesId,
                                                                                                          Pageable pageable);

    List<EventsEntity> findAllByInitiatorIdIn(List<Integer> usersId, Pageable pageable);

    List<EventsEntity> findAllByStateIn(List<StateEnum> statesId, Pageable pageable);

    List<EventsEntity> findAllByCategoryIdIn(List<Integer> categoriesId, Pageable pageable);

    List<EventsEntity> findAllByInitiatorIdInAndStateIn(List<Integer> usersId,
                                                                                                          List<StateEnum> statesId,
                                                                                                          Pageable pageable);

    List<EventsEntity> findAllByInitiatorIdInAndCategoryIdIn(List<Integer> usersId,
                                                                                                          List<Integer> categoriesId,
                                                                                                          Pageable pageable);

    List<EventsEntity> findAllByStateInAndCategoryIdIn(List<StateEnum> statesId,
                                                                                                          List<Integer> categoriesId,
                                                                                                          Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.paid = (:paid) " +
            "and ee.category.id in (:categories) " +
            "and ee.eventDate > (:rangeStart) " +
            "and ee.eventDate < (:rangeEnd) " +
            "and (lower(ee.annotation) like lower(concat('%', :text, '%')) " +
            "or lower(ee.description) like lower(concat('%', :text, '%')))"
    )
    List<EventsEntity> search(@Param("text") String text, @Param("categories") List<Integer> categories,
                              @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                              @Param("rangeEnd") LocalDateTime rangeEnd, Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.paid = (:paid) " +
            "and ee.category.id in (:categories) " +
            "and ee.eventDate > (:rangeStart) " +
            "and (lower(ee.annotation) like lower(concat('%', :text, '%')) " +
            "or lower(ee.description) like lower(concat('%', :text, '%')))"
    )
    List<EventsEntity> search(@Param("text") String text, @Param("categories") List<Integer> categories,
                              @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                              Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.eventDate > (:rangeStart) " +
            "and (lower(ee.annotation) like lower(concat('%', :text, '%')) " +
            "or lower(ee.description) like lower(concat('%', :text, '%')))"
    )
    List<EventsEntity> search(@Param("text") String text, @Param("rangeStart") LocalDateTime rangeStart,
                              Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.category.id in (:categories) " +
            "and ee.eventDate >= (:rangeStart) "
    )
    List<EventsEntity> search(@Param("categories") List<Integer> categories,
                              @Param("rangeStart") LocalDateTime rangeStart);


//    List<EventsEntity> findAllByCategoryIdIn(List<Integer> categories, Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.paid = (:paid) " +
            "and ee.eventDate > (:rangeStart) "
    )
    List<EventsEntity> search(
                              @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                              Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.paid = (:paid) " +
            "and ee.category.id in (:categories) " +
            "and ee.eventDate > (:rangeStart) " +
            "and ee.eventDate < (:rangeEnd) " +
            "and ((ee.confirmedRequests < ee.participantLimit) or (ee.participantLimit = 0)) " +
            "and (lower(ee.annotation) like lower(concat('%', :text, '%')) " +
            "or lower(ee.description) like lower(concat('%', :text, '%')))")
    List<EventsEntity> searchAvailable(@Param("text") String text, @Param("categories") List<Integer> categories,
                              @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                              @Param("rangeEnd") LocalDateTime rangeEnd, Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.paid = (:paid) " +
            "and ee.category.id in (:categories) " +
            "and ee.eventDate > (:rangeStart) " +
            "and ((ee.confirmedRequests < ee.participantLimit) or (ee.participantLimit = 0)) " +
            "and (lower(ee.annotation) like lower(concat('%', :text, '%')) " +
            "or lower(ee.description) like lower(concat('%', :text, '%')))")
    List<EventsEntity> searchAvailable(@Param("text") String text, @Param("categories") List<Integer> categories,
                                       @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                                       Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.eventDate > (:rangeStart) " +
            "and ((ee.confirmedRequests < ee.participantLimit) or (ee.participantLimit = 0)) " +
            "and (lower(ee.annotation) like lower(concat('%', :text, '%')) " +
            "or lower(ee.description) like lower(concat('%', :text, '%')))")
    List<EventsEntity> searchAvailable(@Param("text") String text, @Param("rangeStart") LocalDateTime rangeStart,
                                       Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.category.id in (:categories) " +
            "and ee.eventDate > (:rangeStart) " +
            "and ((ee.confirmedRequests < ee.participantLimit) or (ee.participantLimit = 0)) ")
    List<EventsEntity> searchAvailable(@Param("categories") List<Integer> categories,
                                       @Param("rangeStart") LocalDateTime rangeStart,
                                       Pageable pageable);

    @Query("select ee from EventsEntity ee " +
            "where ee.paid = (:paid) " +
            "and ee.eventDate > (:rangeStart) " +
            "and ((ee.confirmedRequests < ee.participantLimit) or (ee.participantLimit = 0)) ")
    List<EventsEntity> searchAvailable(@Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                                       Pageable pageable);

}
