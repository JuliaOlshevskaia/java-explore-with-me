package ru.practicum.statsserver.service;

import org.apache.coyote.BadRequestException;
import ru.practicum.statsdto.dto.EndpointHit;
import ru.practicum.statsdto.dto.ViewStats;

import java.util.List;

public interface EndpointViewService {
    void hit(EndpointHit endpointHit);

    List<ViewStats> getStats(String startTime, String endTime, List<String> uris, Boolean unique) throws BadRequestException;
}
