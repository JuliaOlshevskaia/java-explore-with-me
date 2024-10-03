package ru.practicum.mainservice.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.comments.dto.CommentDto;
import ru.practicum.mainservice.comments.dto.NewCommentDto;
import ru.practicum.mainservice.comments.entity.CommentEntity;
import ru.practicum.mainservice.comments.repository.CommentRepository;
import ru.practicum.mainservice.errors.exceptions.DataNotFoundException;
import ru.practicum.mainservice.errors.exceptions.EventValidationException;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.events.repository.EventsRepository;
import ru.practicum.mainservice.events.service.EventsService;
import ru.practicum.mainservice.requests.entity.RequestsEntity;
import ru.practicum.mainservice.requests.repository.RequestsRepository;
import ru.practicum.mainservice.users.entity.UserEntity;
import ru.practicum.mainservice.users.mapper.UserMapper;
import ru.practicum.mainservice.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final UserMapper userMapper;
    private final EventsService eventsService;
    private final RequestsRepository requestsRepository;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public CommentDto addComment(Integer userId, Integer eventId, NewCommentDto request) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new DataNotFoundException("User with id=" + userId + " was not found"));
        EventsEntity event = eventsRepository.findById(eventId).orElseThrow(() ->
                new DataNotFoundException("Event with id=" + eventId + " was not found"));
        RequestsEntity requestByUser = requestsRepository.findAllByEventIdAndRequesterId(eventId, userId);
        if (event.getInitiator().getId().equals(userId)) {
            throw new EventValidationException("Нельзя оставлять комментарий к своему событию");
        }
        if (requestByUser == null) {
            throw new EventValidationException("Нельзя оставлять комментарии к событию, в котором пользователь не участвовал");
        }

        CommentEntity entity = new CommentEntity(null, user, event, request.getComment(), LocalDateTime.now());
        CommentEntity entityCreated = repository.save(entity);
        CommentDto dto = new CommentDto(
                entityCreated.getId(),
                userMapper.toShortDto(entityCreated.getCommentator()),
                eventsService.toEventShortDto(entityCreated.getEvent()),
                entityCreated.getComment(),
                entityCreated.getCreatedDate());
        return dto;
    }

    @Override
    public void deleteComment(Integer commentId, Integer userId) {
        if (!repository.existsById(commentId)) {
            throw new DataNotFoundException("Comment with id=" + commentId + " was not found");
        }
        repository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getComments(Integer eventId, Integer commentatorId, String rangeStart, String rangeEnd,
                                        Integer from, Integer size) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);
        List<CommentEntity> entities = repository.findAllByCommentatorIdAndEventIdAndCreatedDateAfterAndCreatedDateBefore(
                commentatorId,
                eventId,
//                rangeStart == null ? null : LocalDateTime.parse(rangeStart, DTF),
//                rangeEnd == null ? null : LocalDateTime.parse(rangeEnd, DTF),
                pageParam);

        List<CommentDto> result = new ArrayList<>();

        entities.forEach(e -> result.add(new CommentDto(
                e.getId(),
                userMapper.toShortDto(e.getCommentator()),
                eventsService.toEventShortDto(e.getEvent()),
                e.getComment(),
                e.getCreatedDate())));

        return result;
    }

    @Override
    public CommentDto getComment(Integer id) {
        CommentEntity entity = repository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Comment with id=" + id + " was not found"));
        return new CommentDto(entity.getId(), userMapper.toShortDto(entity.getCommentator()),
                eventsService.toEventShortDto(entity.getEvent()), entity.getComment(), entity.getCreatedDate());
    }
}
