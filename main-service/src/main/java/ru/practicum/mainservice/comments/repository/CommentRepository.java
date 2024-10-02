package ru.practicum.mainservice.comments.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.comments.entity.CommentEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByCommentatorIdAndEventIdAndCreatedDateAfterAndCreatedDateBefore(Integer commentatorId,
                                                                                                Integer eventId,
                                                                                                LocalDateTime rangeStart,
                                                                                                LocalDateTime rangeEnd,
                                                                                                Pageable pageable);

    List<CommentEntity> findAllByEventIdAndCreatedDateAfterAndCreatedDateBefore(Integer eventId,
                                                                                LocalDateTime rangeStart,
                                                                                LocalDateTime rangeEnd,
                                                                                Pageable pageable);

    List<CommentEntity> findAllByCommentatorIdAndCreatedDateAfterAndCreatedDateBefore(Integer commentatorId,
                                                                                      LocalDateTime rangeStart,
                                                                                      LocalDateTime rangeEnd,
                                                                                      Pageable pageable);

    List<CommentEntity> findAllByCommentatorIdAndEventId(Integer commentatorId,
                                                         Integer eventId,
                                                         Pageable pageable);

    List<CommentEntity> findAllByCommentatorId(Integer commentatorId, Pageable pageable);

    List<CommentEntity> findAllByEventId(Integer eventId, Pageable pageable);
}
