package ru.practicum.mainservice.events.service;

import ru.practicum.mainservice.events.dto.*;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventsService {
    List<EventShortDto> getEvents(Integer userId, Integer from, Integer size);

    EventFullDto addEvent(Integer userId, NewEventDto request);

    EventFullDto updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest request);

    EventFullDto getEvent(Integer userId, Integer eventId);

    List<ParticipationRequestDto> getEventParticipants(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult changeRequestStatus(Integer userId, Integer eventId, EventRequestStatusUpdateRequest request);

    EventFullDto updateEvent_1(Integer eventId, UpdateEventAdminRequest request);

    List<EventFullDto> getEvents_2(List<Integer> users, List<StateEnum> states, List<Integer> categories, String rangeStart,
                                   String rangeEnd, Integer from, Integer size);

    List<EventShortDto> getEvents_1(String text, List<Integer> categories, Boolean paid, String rangeStart,
                                    String rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                    Integer size, HttpServletRequest request);

    EventFullDto getEvent_1(Integer id);

    EventShortDto toEventShortDto(EventsEntity entity);
}
