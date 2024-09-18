package ru.practicum.mainservice.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.categories.service.CategoriesService;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.events.service.EventsService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventsController {
    private final EventsService service;

    @GetMapping
    public List<EventFullDto> getEvents_2(@RequestParam("users") List<Integer> users,
                                          @RequestParam("states") List<String> states,
                                          @RequestParam("categories") List<Integer> categories,
                                          @RequestParam("rangeStart") String rangeStart,
                                          @RequestParam("rangeEnd") String rangeEnd,
                                          @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent_1(@PathVariable Integer eventId, @RequestBody UpdateEventAdminRequest request) {
        return service.updateEvent_1(eventId, request);
    }


}
