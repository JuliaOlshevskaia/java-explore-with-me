package ru.practicum.mainservice.events.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.EventShortDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventsController {

    @GetMapping
    public List<EventShortDto> getEvents_1(@RequestParam("text") String text,
                                           @RequestParam("categories") List<Integer> categories,
                                           @RequestParam("paid") Boolean paid,
                                           @RequestParam("rangeStart") String rangeStart,
                                           @RequestParam("rangeEnd") String rangeEnd,
                                           @RequestParam("onlyAvailable") Boolean onlyAvailable,
                                           @RequestParam("sort") String sort,
                                           @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return null;
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent_1(@PathVariable Integer id) {
        return null;
    }


}
