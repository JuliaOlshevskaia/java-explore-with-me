package ru.practicum.mainservice.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.StateEnum;
import ru.practicum.mainservice.events.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.events.service.EventsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventsController {
    private final EventsService service;

    @GetMapping
    public List<EventFullDto> getEvents_2(@RequestParam(name = "users", required = false) List<Integer> users,
                                          @RequestParam(name = "states", required = false) List<StateEnum> states,
                                          @RequestParam(name = "categories", required = false) List<Integer> categories,
                                          @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                          @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                          @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getEvents_2(users, states, categories, rangeStart,
                rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent_1(@PathVariable Integer eventId, @Valid @RequestBody UpdateEventAdminRequest request) {
        return service.updateEvent_1(eventId, request);
    }


}
