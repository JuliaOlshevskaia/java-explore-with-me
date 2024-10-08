package ru.practicum.statsserver.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ru.practicum.statsdto.dto.EndpointHit;
import ru.practicum.statsdto.dto.ViewStats;
import ru.practicum.statsdto.dto.ViewStatsIndicators;
import ru.practicum.statsdto.dto.ViewStatsIndicatorsWithIp;
import ru.practicum.statsserver.entity.EndpointViewEntity;
import ru.practicum.statsserver.mapper.EndpointViewMapper;
import ru.practicum.statsserver.repository.EndpointViewRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EndpointViewServiceImpl implements EndpointViewService {
    private final EndpointViewRepository repository;
    private final EndpointViewMapper mapper;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void hit(EndpointHit endpointHit) {
        LocalDateTime date = LocalDateTime.parse(endpointHit.getTimestamp(), DTF);
        EndpointViewEntity entity = mapper.toEntity(endpointHit, date);
        repository.save(entity);
    }

    @Override
    public List<ViewStats> getStats(String startTime, String endTime, List<String> uris, Boolean unique) throws BadRequestException {
        LocalDateTime start = LocalDateTime.parse(startTime, DTF);
        LocalDateTime end = LocalDateTime.parse(endTime, DTF);
        List<EndpointViewEntity> listEntity;

        if (start.isAfter(end)) {
            throw new BadRequestException();
        }

        if (uris == null || uris.size() == 0) {
            listEntity = repository.findAllByTimestampAfterAndTimestampBefore(start, end);
        } else {
            listEntity = repository.findAllByTimestampAfterAndTimestampBeforeAndUriIn(start, end, uris);
        }

        List<ViewStats> listViews = new ArrayList<>();

        if (unique) {
            Map<ViewStatsIndicatorsWithIp, Integer> mapIndicatorsWithIp = new HashMap<>();

            listEntity.forEach(view -> {
                ViewStatsIndicatorsWithIp viewStatsIndicatorsWithIp = new ViewStatsIndicatorsWithIp(view.getApp(),
                        view.getUri(), view.getIp());

                if (!mapIndicatorsWithIp.containsKey(viewStatsIndicatorsWithIp)) {
                    mapIndicatorsWithIp.put(viewStatsIndicatorsWithIp, 1);
                }
            });

            Map<ViewStats, Integer> mapView = new HashMap<>();

            mapIndicatorsWithIp.forEach((view, count) -> {
                ViewStats viewStats = new ViewStats(view.getApp(), view.getUri(), null);

                if (mapView.containsKey(viewStats)) {
                    mapView.put(viewStats, (mapView.get(viewStats) + 1));
                } else {
                    mapView.put(viewStats, 1);
                }
            });

            mapView.forEach((viewStats, count) -> {
                listViews.add(new ViewStats(viewStats.getApp(), viewStats.getUri(), count));
            });
        } else {
            Map<ViewStatsIndicators, Integer> mapIndicators = new HashMap<>();

            listEntity.forEach(view -> {
                ViewStatsIndicators viewIndicators = new ViewStatsIndicators(view.getApp(), view.getUri());

                if (mapIndicators.containsKey(viewIndicators)) {
                    mapIndicators.put(viewIndicators, (mapIndicators.get(viewIndicators) + 1));
                } else {
                    mapIndicators.put(viewIndicators, 1);
                }
            });

            mapIndicators.forEach((viewStats, count) -> {
                listViews.add(new ViewStats(viewStats.getApp(), viewStats.getUri(), count));
            });
        }

        Collections.sort(listViews);
        return listViews;
    }

}
