package ru.practicum.statsserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsdto.dto.EndpointHit;
import ru.practicum.statsdto.dto.ViewStats;
import ru.practicum.statsserver.service.EndpointViewService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping
public class StatsController {
    private final EndpointViewService service;

    @PostMapping("/hit")
    public void hit(@RequestBody EndpointHit request) {
        service.hit(request);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam("start") String startTime,
                              @RequestParam("end") String endTime,
                              @RequestParam(name = "uris", required = false) List<String> uris,
                              @RequestParam(name = "unique", required = false, defaultValue = "false") Boolean unique) {
        return service.getStats(startTime, endTime, uris, unique);
    }


}
