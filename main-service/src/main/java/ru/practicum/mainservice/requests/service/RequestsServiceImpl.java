package ru.practicum.mainservice.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.errors.exceptions.DataNotFoundException;
import ru.practicum.mainservice.errors.exceptions.EventValidationException;
import ru.practicum.mainservice.events.dto.StateEnum;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.events.repository.EventsRepository;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;
import ru.practicum.mainservice.requests.entity.RequestsEntity;
import ru.practicum.mainservice.requests.mapper.RequestsMapper;
import ru.practicum.mainservice.requests.repository.RequestsRepository;
import ru.practicum.mainservice.users.entity.UserEntity;
import ru.practicum.mainservice.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestsServiceImpl implements RequestsService {
    private final RequestsRepository repository;
    private final RequestsMapper mapper;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;

    @Override
    public List<ParticipationRequestDto> getUserRequests(Integer userId) {
        List<RequestsEntity> requestsEntities = repository.findAllByRequesterId(userId);
        return mapper.toListDto(requestsEntities);
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(Integer userId, Integer eventId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User with id=" + userId + " was not found"));
        EventsEntity eventEntity = eventsRepository.findById(eventId).orElseThrow(() -> new DataNotFoundException("Event with id=" + eventId + " was not found"));
        if (eventEntity.getInitiator().getId().equals(userId)) {
            throw new EventValidationException("Инициатор события не может добавить запрос на участие в своём событии");
        }
        if (!eventEntity.getState().equals(StateEnum.PUBLISHED)) {
            throw new EventValidationException("Нельзя участвовать в неопубликованном событии");
        }
        if ((eventEntity.getConfirmedRequests() >= eventEntity.getParticipantLimit()) && (eventEntity.getParticipantLimit() > 0)) {
            throw new EventValidationException("У события достигнут лимит запросов на участие");
        }
        RequestsEntity requestEntity;
        if (eventEntity.getRequestModeration() && eventEntity.getParticipantLimit() > 0) {
            requestEntity = new RequestsEntity(null, eventEntity, userEntity, LocalDateTime.now(), StateEnum.PENDING);
        } else {
            requestEntity = new RequestsEntity(null, eventEntity, userEntity, LocalDateTime.now(), StateEnum.CONFIRMED);
            eventEntity.setConfirmedRequests(eventEntity.getConfirmedRequests() + 1);
            eventsRepository.save(eventEntity);
        }

        RequestsEntity requestCreatedEntity = repository.save(requestEntity);
        return mapper.toDto(requestCreatedEntity);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        RequestsEntity entity = repository.findById(requestId).orElseThrow(() ->
                new DataNotFoundException("Request with id=" + requestId + " was not found"));
        entity.setStatus(StateEnum.CANCELED);
        RequestsEntity entityUpdated = repository.save(entity);
        return mapper.toDto(entityUpdated);
    }
}
