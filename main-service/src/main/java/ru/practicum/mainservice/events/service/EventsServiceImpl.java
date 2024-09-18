package ru.practicum.mainservice.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.categories.entity.CategoriesEntity;
import ru.practicum.mainservice.categories.mapper.CategoriesMapper;
import ru.practicum.mainservice.categories.repository.CategoriesRepository;
import ru.practicum.mainservice.errors.exceptions.DataNotFoundException;
import ru.practicum.mainservice.errors.exceptions.EventUpdateException;
import ru.practicum.mainservice.errors.exceptions.EventValidationException;
import ru.practicum.mainservice.events.dto.*;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.events.entity.LocationsEntity;
import ru.practicum.mainservice.events.mapper.EventsMapper;
import ru.practicum.mainservice.events.mapper.LocationsMapper;
import ru.practicum.mainservice.events.repository.EventsRepository;
import ru.practicum.mainservice.events.repository.LocationsRepository;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;
import ru.practicum.mainservice.requests.entity.RequestsEntity;
import ru.practicum.mainservice.requests.mapper.RequestsMapper;
import ru.practicum.mainservice.requests.repository.RequestsRepository;
import ru.practicum.mainservice.users.dto.UserShortDto;
import ru.practicum.mainservice.users.entity.UserEntity;
import ru.practicum.mainservice.users.mapper.UserMapper;
import ru.practicum.mainservice.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {
    private final EventsRepository repository;
    private final CategoriesRepository categoriesRepository;
    private final UserRepository userRepository;
    private final LocationsRepository locationsRepository;
    private final RequestsRepository requestsRepository;
    private final EventsMapper mapper;
    private final LocationsMapper locationsMapper;
    private final UserMapper userMapper;
    private final CategoriesMapper categoriesMapper;
    private final RequestsMapper requestsMapper;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventShortDto> getEvents(Integer userId, Integer from, Integer size) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);

        List<EventsEntity> eventsEntities = repository.findAllByInitiatorId(userId, pageParam);
        return mapper.toListShortDto(eventsEntities);
    }

    @Override
    public EventFullDto addEvent(Integer userId, NewEventDto request) {
        LocalDateTime eventDate = LocalDateTime.parse(request.getEventDate(), DTF);
        checkEventDate(eventDate);
        CategoriesEntity categoriesEntity = categoriesRepository.findById(request.getCategory()).get();
        UserEntity userEntity = userRepository.findById(userId).get();
        EventsEntity entity = mapper.toEntity(request, categoriesEntity, eventDate);
        LocationsEntity locationsEntity = createLocation(request.getLocation());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setInitiator(userEntity);
        entity.setLocation(locationsEntity);
        entity.setState(StateEnum.PENDING);
//        entity.setCategory(categoriesEntity);
        EventsEntity entityCreated = repository.save(entity);
        EventFullDto result = mapper.toDto(entityCreated,
                categoriesMapper.toDto(entityCreated.getCategory()),
                userMapper.toShortDto(entityCreated.getInitiator()),
                locationsMapper.toDto(entityCreated.getLocation()),
                entityCreated.getCreatedDate().format(DTF),
                entityCreated.getEventDate().format(DTF)
                );
//        result.setEventDate(entityCreated.getEventDate().toString());
        return result;
    }

    public EventFullDto updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest request) {
        isEventExists(eventId);
        checkEventStatus(eventId);

        EventsEntity entity = repository.findById(eventId).get();

        if (request.getEventDate() != null) {
            LocalDateTime eventDate = LocalDateTime.parse(request.getEventDate(), DTF);
            checkEventDate(eventDate);
            entity.setEventDate(eventDate);
        }
        if (request.getAnnotation() != null) {
            entity.setAnnotation(request.getAnnotation());
        }
        if (request.getCategory() != null && entity.getCategory().getId().equals(request.getCategory())) {
            CategoriesEntity categoriesEntity = categoriesRepository.findById(request.getCategory()).get();
            entity.setCategory(categoriesEntity);
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getLocation() != null &&
                (!(entity.getLocation().getLat().equals(request.getLocation().getLat())) ||
                !(entity.getLocation().getLon().equals(request.getLocation().getLon())))) {
            entity.setLocation(createLocation(request.getLocation()));
        }
        if (request.getPaid() != null) {
            entity.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            entity.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getRequestModeration() != null) {
            entity.setRequestModeration(request.getRequestModeration());
        }
        if (request.getStateAction() != null) {
            entity.setState(request.getStateAction() == StateActionEnum.SEND_TO_REVIEW ? StateEnum.PENDING : StateEnum.CANCELED);
        }
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        EventsEntity eventsEntityUpdated = repository.save(entity);
        EventFullDto result = mapper.toDto(eventsEntityUpdated,
                categoriesMapper.toDto(eventsEntityUpdated.getCategory()),
                userMapper.toShortDto(eventsEntityUpdated.getInitiator()),
                locationsMapper.toDto(eventsEntityUpdated.getLocation()),
                eventsEntityUpdated.getCreatedDate().format(DTF),
                eventsEntityUpdated.getEventDate().format(DTF)
        );
        return result;
    }

    @Override
    public EventFullDto getEvent(Integer userId, Integer eventId) {
        EventsEntity entity = repository.findAllByIdAndInitiatorId(eventId, userId);
        if (entity == null) {
            throw new DataNotFoundException("Event with id=" + eventId + " was not found");
        }
        CategoryDto category = categoriesMapper.toDto(entity.getCategory());
        UserShortDto user = userMapper.toShortDto(entity.getInitiator());
        Location location = locationsMapper.toDto(entity.getLocation());

        return mapper.toDto(entity, category, user, location, entity.getCreatedDate().format(DTF), entity.getEventDate().format(DTF));
    }

    @Override
    public List<ParticipationRequestDto> getEventParticipants(Integer userId, Integer eventId) {
        isEventExists(eventId);
        List<EventsEntity> events = repository.findAllByInitiatorId(userId);
        List<RequestsEntity> requests = requestsRepository.findAllByEventIn(events);
        return requestsMapper.toListDto(requests);
    }

    @Override
     public EventRequestStatusUpdateResult changeRequestStatus(Integer userId, Integer eventId, EventRequestStatusUpdateRequest request) {
        isEventExists(eventId);
        EventsEntity eventsEntity = repository.findById(eventId).get();
        final Integer[] confirmedRequests = {eventsEntity.getConfirmedRequests()};
        Integer participantLimit = eventsEntity.getParticipantLimit();
        if (confirmedRequests[0] >= participantLimit) {
            throw new EventValidationException("The participant limit has been reached");
        }
        List<Integer> requestIds = request.getRequestIds();
        List<RequestsEntity> requestsEntities = requestsRepository.findAllByIdIn(requestIds);
        if (request.getStatus().equals(StatusUpdateRequestEnum.CONFIRMED.toString())) {
            requestsEntities.forEach(i -> {
                if (participantLimit.equals(0) || confirmedRequests[0] < participantLimit) {
                    i.setStatus(StateEnum.PUBLISHED);
                    requestsRepository.save(i);
                    ++confirmedRequests[0];
                } else {
                    i.setStatus(StateEnum.CANCELED);
                    requestsRepository.save(i);
                }
            });
        } else {
            requestsEntities.forEach(i -> {
                i.setStatus(StateEnum.CANCELED);
                requestsRepository.save(i);
            });
        }
        List<ParticipationRequestDto> requestsConfirmed = new ArrayList<>();
        List<ParticipationRequestDto> requestsRejected = new ArrayList<>();
        List<RequestsEntity> requestsEntitiesUpdated = requestsRepository.findAllByIdIn(requestIds);
        requestsEntitiesUpdated.stream().filter(f -> f.getStatus() == StateEnum.PUBLISHED).forEach(i ->
                requestsConfirmed.add(requestsMapper.toDto(i)));
        requestsEntitiesUpdated.stream().filter(f -> f.getStatus() == StateEnum.CANCELED).forEach(i ->
                requestsRejected.add(requestsMapper.toDto(i)));

        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        eventRequestStatusUpdateResult.setConfirmedRequests(requestsConfirmed);
        eventRequestStatusUpdateResult.setRejectedRequests(requestsRejected);

        return eventRequestStatusUpdateResult;
     }

     @Override
     public EventFullDto updateEvent_1(Integer eventId, UpdateEventAdminRequest request) {
         isEventExists(eventId);
         EventsEntity entity = repository.findById(eventId).get();

         if (entity.getState() != StateEnum.PENDING) {
             throw new EventUpdateException("Cannot publish the event because it's not in the right state: PUBLISHED");
         }

         if (request.getEventDate() != null) {
             LocalDateTime eventDate = LocalDateTime.parse(request.getEventDate(), DTF);
             checkEventDate(eventDate);
             entity.setEventDate(eventDate);
         }
         if (request.getAnnotation() != null) {
             entity.setAnnotation(request.getAnnotation());
         }
         if (request.getCategory() != null && entity.getCategory().getId().equals(request.getCategory())) {
             CategoriesEntity categoriesEntity = categoriesRepository.findById(request.getCategory()).get();
             entity.setCategory(categoriesEntity);
         }
         if (request.getDescription() != null) {
             entity.setDescription(request.getDescription());
         }
         if (request.getLocation() != null &&
                 (!(entity.getLocation().getLat().equals(request.getLocation().getLat())) ||
                         !(entity.getLocation().getLon().equals(request.getLocation().getLon())))) {
             entity.setLocation(createLocation(request.getLocation()));
         }
         if (request.getPaid() != null) {
             entity.setPaid(request.getPaid());
         }
         if (request.getParticipantLimit() != null) {
             entity.setParticipantLimit(request.getParticipantLimit());
         }
         if (request.getRequestModeration() != null) {
             entity.setRequestModeration(request.getRequestModeration());
         }
         if (request.getStateAction() != null) {
             entity.setState(request.getStateAction() == StateActionAdminEnum.PUBLISH_EVENT ? StateEnum.PUBLISHED : StateEnum.CANCELED);
         }
         if (request.getTitle() != null) {
             entity.setTitle(request.getTitle());
         }
         EventsEntity eventsEntityUpdated = repository.save(entity);
         EventFullDto result = mapper.toDto(eventsEntityUpdated,
                 categoriesMapper.toDto(eventsEntityUpdated.getCategory()),
                 userMapper.toShortDto(eventsEntityUpdated.getInitiator()),
                 locationsMapper.toDto(eventsEntityUpdated.getLocation()),
                 eventsEntityUpdated.getCreatedDate().format(DTF),
                 eventsEntityUpdated.getEventDate().format(DTF)
         );
        return result;
     }

    public List<EventFullDto> getEvents_2(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart,
                                   String rangeEnd, Integer from, Integer size) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);
        List<EventsEntity> events = repository.findAllByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                users, states, categories, LocalDateTime.parse(rangeStart, DTF), LocalDateTime.parse(rangeEnd, DTF), pageParam);

        return null;
    }

    @Override
    public void isEventExists(Integer eventId) {
        if (!(repository.existsById(eventId))) {
            throw new DataNotFoundException("Event with id=" + eventId + " was not found");
        }
    }

    private void checkEventStatus(Integer eventId) {
        EventsEntity entity = repository.findById(eventId).get();
        if (entity.getState().equals(StateEnum.PUBLISHED.toString())) {
            throw new EventValidationException("Only pending or canceled events can be changed");
        }
    }

    private void checkEventDate(LocalDateTime eventDate) {
        long diffInHours = ChronoUnit.HOURS.between(LocalDateTime.now(), eventDate);
        if (diffInHours<2) {
            throw new EventValidationException("Field: eventDate. Error: должно содержать дату, которая еще не наступила. " +
                    "Value: " + eventDate);
        }
    }

    private LocationsEntity createLocation(Location location) {
        LocationsEntity locationsEntity = locationsRepository.findAllByLatAndLon(location.getLat(), location.getLon());
        if (locationsEntity == null) {
            LocationsEntity locationsNewEntity = locationsRepository.save(locationsMapper.toEntity(location));
            return locationsNewEntity;
        } else {
            return locationsEntity;
        }
    }

}
