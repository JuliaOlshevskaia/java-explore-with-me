package ru.practicum.mainservice.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.service.EventsService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventsController {
    private final EventsService service;

    @GetMapping
    public List<EventShortDto> getEvents_1(@RequestParam(name = "text", required = false) String text,
                                           @RequestParam(name = "categories", required = false) List<Integer> categories,
                                           @RequestParam(name = "paid", required = false) Boolean paid,
                                           @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                           @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                           @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                                           @RequestParam(name = "sort", required = false) String sort,
                                           @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getEvents_1(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent_1(@PathVariable Integer id) {
        return service.getEvent_1(id);
    }


}
