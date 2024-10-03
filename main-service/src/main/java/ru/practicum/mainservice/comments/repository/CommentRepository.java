package ru.practicum.mainservice.comments.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.comments.entity.CommentEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Query("select ce from CommentEntity ce " +
            "where ((:commentatorId) is null or ce.commentator.id = (:commentatorId)) "
//            "and ((:eventId) is null or ce.event.id = (:eventId)) " +
//            "and ((:rangeStart) is null or ce.createdDate > (:rangeStart)) " +
//            "and ((:rangeEnd) is null or ce.createdDate < (:rangeEnd)) "
    )
    List<CommentEntity> findAllByCommentatorIdAndEventIdAndCreatedDateAfterAndCreatedDateBefore(@Param("commentatorId") Integer commentatorId,
//                           @Param("eventId") Integer eventId,
//                           @Param("rangeStart") LocalDateTime rangeStart,
//                           @Param("rangeEnd") LocalDateTime rangeEnd,
                           Pageable pageable);
}
