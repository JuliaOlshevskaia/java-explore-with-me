package ru.practicum.mainservice.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.*;
import ru.practicum.mainservice.events.service.EventsService;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;
import ru.practicum.mainservice.users.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class PrivateEventsController {
    private final EventsService service;
    private final UserService userService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEvents(@PathVariable Integer userId,
                                         @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        userService.isUserExists(userId);
        return service.getEvents(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addEvent(@PathVariable Integer userId, @RequestBody NewEventDto request) {
        userService.isUserExists(userId);
        return service.addEvent(userId, request);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable Integer userId,
                                         @PathVariable Integer eventId) {
        userService.isUserExists(userId);
        return service.getEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable Integer userId,
                                @PathVariable Integer eventId,
                                @RequestBody UpdateEventUserRequest request) {
        userService.isUserExists(userId);
        return service.updateEvent(userId, eventId, request);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventParticipants(@PathVariable Integer userId,
                                                              @PathVariable Integer eventId) {
        userService.isUserExists(userId);
        return service.getEventParticipants(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestStatus(@PathVariable Integer userId,
                                                              @PathVariable Integer eventId,
                                                              @RequestBody EventRequestStatusUpdateRequest request) {
        userService.isUserExists(userId);
        return service.changeRequestStatus(userId, eventId, request);
    }


}
