package ru.practicum.mainservice.requests.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.service.EventsService;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;
import ru.practicum.mainservice.requests.service.RequestsService;
import ru.practicum.mainservice.users.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestsController {
    private final RequestsService service;
    private final EventsService eventService;
    private final UserService userService;

    @GetMapping()
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Integer userId) {
        userService.isUserExists(userId);
        return service.getUserRequests(userId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addParticipationRequest(@PathVariable Integer userId,
                                                @RequestParam("eventId") Integer eventId) {
        return service.addParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Integer userId,
                                @PathVariable Integer requestId) {
        userService.isUserExists(userId);
        return service.cancelRequest(userId, requestId);
    }

}
