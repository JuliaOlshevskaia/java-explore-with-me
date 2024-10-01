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
import ru.practicum.mainservice.errors.exceptions.ValidationException;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor
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
        Pageable pageParam = PageRequest.of(from, size);

//        List<EventsEntity> eventsEntities = repository.getByUser(userId, pageParam);
        List<EventsEntity> eventsEntities = repository.findAll(pageParam).getContent();
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
        if (entity.getConfirmedRequests() == null) {
            entity.setConfirmedRequests(0);
        }
//        entity.setCategory(categoriesEntity);
        EventsEntity entityCreated = repository.save(entity);
        EventFullDto result = mapper.toDto(entityCreated,
                categoriesMapper.toDto(entityCreated.getCategory()),
                userMapper.toShortDto(entityCreated.getInitiator()),
                locationsMapper.toDto(entityCreated.getLocation()),
                entityCreated.getCreatedDate().format(DTF),
                entityCreated.getEventDate().format(DTF)
                );
        return result;
    }

    public EventFullDto updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest request) {
        isEventExists(eventId);
        checkEventStatus(eventId);

        EventsEntity entity = repository.findById(eventId).get();

        if (entity.getState() == StateEnum.PUBLISHED) {
            throw new EventUpdateException("Only pending or canceled events can be changed");
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
                    i.setStatus(StateEnum.CONFIRMED);
                    requestsRepository.save(i);
                    ++confirmedRequests[0];
                    eventsEntity.setConfirmedRequests(eventsEntity.getConfirmedRequests() + 1);
                    repository.save(eventsEntity);
                } else {
                    i.setStatus(StateEnum.REJECTED);
                    requestsRepository.save(i);
                }
            });
        } else {
            requestsEntities.forEach(i -> {
                i.setStatus(StateEnum.REJECTED);
                requestsRepository.save(i);
            });
        }
        List<ParticipationRequestDto> requestsConfirmed = new ArrayList<>();
        List<ParticipationRequestDto> requestsRejected = new ArrayList<>();
        List<RequestsEntity> requestsEntitiesUpdated = requestsRepository.findAllByIdIn(requestIds);
        requestsEntitiesUpdated.stream().filter(f -> f.getStatus() == StateEnum.CONFIRMED).forEach(i ->
                requestsConfirmed.add(requestsMapper.toDto(i)));
        requestsEntitiesUpdated.stream().filter(f -> f.getStatus() == StateEnum.REJECTED).forEach(i ->
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

         if (ChronoUnit.HOURS.between(LocalDateTime.now(), entity.getEventDate()) < 1) {
             throw new EventUpdateException("Cannot publish the event because it's not in the right event date");
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

     @Override
     public List<EventShortDto> getEvents_1(String text, List<Integer> categories, Boolean paid, String rangeStart,
                                    String rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                    Integer size, HttpServletRequest request) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);
        Pageable pageParam2 = PageRequest.of(from, size);
         List<EventsEntity> entities;

         if (onlyAvailable) {
             if (rangeStart == null && rangeEnd == null) {

                 if (text != null && categories == null && paid == null) {
                     entities = repository.searchAvailable(text, LocalDateTime.now(),
                             pageParam);
                 } else if (text == null && categories != null && paid == null) {
                     entities = repository.searchAvailable(categories, LocalDateTime.now(), pageParam);
                 } else if (text == null && categories == null && paid != null) {
                     entities = repository.searchAvailable(paid, LocalDateTime.now(),
                             pageParam);
                 } else {
                     entities = repository.searchAvailable(text, categories, paid, LocalDateTime.now(),
                             pageParam);
                 }
             } else {
                 entities = repository.searchAvailable(text, categories, paid, LocalDateTime.parse(rangeStart, DTF),
                         LocalDateTime.parse(rangeEnd, DTF), pageParam);
             }
         } else {
             if (rangeStart == null && rangeEnd == null) {
                 if (text != null && categories == null && paid == null) {
                     entities = repository.search(text, LocalDateTime.now(),
                             pageParam);
                 } else if (text == null && categories != null && paid == null) {
                     entities = repository.findAllByCategoryIdInAndEventDateAfter(categories, LocalDateTime.now(), pageParam2);
                 } else if (text == null && categories == null && paid != null) {
                     entities = repository.search(paid, LocalDateTime.now(),
                             pageParam);
                 } else {
                     entities = repository.search(text, categories, paid, LocalDateTime.now(),
                             pageParam);
                 }
             } else {
                 entities = repository.search(text, categories, paid,  LocalDateTime.parse(rangeStart, DTF),
                         LocalDateTime.parse(rangeEnd, DTF), pageParam);
             }
         }

         if (entities.size() == 0) {
             throw new ValidationException("Event must be published");
         }

//         for (EventsEntity event : entities) {
//             if (event.getState() != StateEnum.PUBLISHED) {
//                 throw new ValidationException("Event must be published");
//             }
//         }

         List<EventShortDto> eventShortDtos = new ArrayList<>();

//        List<EventShortDto> eventShortDtos = mapper.toListShortDto(entities);

         for (EventsEntity entity : entities) {
             EventShortDto eventShortDto = new EventShortDto(entity.getAnnotation(),
                     new CategoryDto(entity.getCategory().getId(), entity.getCategory().getName()),
                     entity.getConfirmedRequests(), entity.getEventDate().format(DTF), entity.getId(),
                     new UserShortDto(entity.getInitiator().getId(), entity.getInitiator().getName()),
                     entity.getPaid(), entity.getTitle(), entity.getViews());
             eventShortDtos.add(eventShortDto);
         }
        return eventShortDtos;
    }

    public List<EventFullDto> getEvents_2(List<Integer> users, List<StateEnum> states, List<Integer> categories, String rangeStart,
                                   String rangeEnd, Integer from, Integer size) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);
        List<EventsEntity> events = new ArrayList<>();

        if (users == null && states == null && categories == null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAll(pageParam).getContent();
        } else if (users != null && states == null && categories == null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAllByInitiatorIdIn(users, pageParam);
        } else if (users == null && states != null && categories == null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAllByStateIn(states, pageParam);
        } else if (users == null && states == null && categories != null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAllByCategoryIdIn(categories, pageParam);
        } else if (users != null && states != null && categories == null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAllByInitiatorIdInAndStateIn(users, states, pageParam);
        } else if (users != null && states == null && categories != null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAllByInitiatorIdInAndCategoryIdIn(users, categories, pageParam);
        } else if (users == null && states != null && categories != null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAllByStateInAndCategoryIdIn(states, categories, pageParam);
        } else if (users != null && states != null && categories != null &&
                rangeEnd == null && rangeStart == null) {
            events = repository.findAllByInitiatorIdInAndStateInAndCategoryIdIn(users, states, categories, pageParam);
        } else {
            events = repository.getAllEvents(
                    users, pageParam);
//            events = repository.findAll(pageParam).getContent();
        }

        List<EventFullDto> eventFullDtos = new ArrayList<>();

        if (events.size() > 0) {
            for (EventsEntity entities : events) {
                EventFullDto result = mapper.toDto(entities,
                        categoriesMapper.toDto(entities.getCategory()),
                        userMapper.toShortDto(entities.getInitiator()),
                        locationsMapper.toDto(entities.getLocation()),
                        entities.getCreatedDate().format(DTF),
                        entities.getEventDate().format(DTF)
                );
                eventFullDtos.add(result);
            }
        }
        return eventFullDtos;
    }

    @Override
    public void isEventExists(Integer eventId) {
        if (!(repository.existsById(eventId))) {
            throw new DataNotFoundException("Event with id=" + eventId + " was not found");
        }
    }

    private void checkEventStatus(Integer eventId) {
        EventsEntity entity = repository.findById(eventId).get();
        if (entity.getState() == StateEnum.PUBLISHED) {
            throw new EventValidationException("Only pending or canceled events can be changed");
        }
    }

    private void checkEventDate(LocalDateTime eventDate) {
        long diffInHours = ChronoUnit.HOURS.between(LocalDateTime.now(), eventDate);
        if (diffInHours < 2) {
            throw new ValidationException("Field: eventDate. Error: должно содержать дату, которая еще не наступила. " +
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

    @Override
    public EventFullDto getEvent_1(Integer id) {
        EventsEntity entity = repository.findById(id).get();
        if (entity.getState() != StateEnum.PUBLISHED) {
            throw new DataNotFoundException("Event with id=" + id + " was not found");
        }
        entity.setViews(entity.getViews() == null ? 1 : (entity.getViews() + 1));
        EventFullDto result = mapper.toDto(entity,
                categoriesMapper.toDto(entity.getCategory()),
                userMapper.toShortDto(entity.getInitiator()),
                locationsMapper.toDto(entity.getLocation()),
                entity.getCreatedDate().format(DTF),
                entity.getEventDate().format(DTF)
        );
        return result;
    }

}
