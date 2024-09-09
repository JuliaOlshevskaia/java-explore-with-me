package ru.practicum.statsserver.service;

import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

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
    public List<ViewStats> getStats(String startTime, String endTime, List<String> uris, Boolean unique) {
        LocalDateTime start = LocalDateTime.parse(startTime, DTF);
        LocalDateTime end = LocalDateTime.parse(endTime, DTF);
        List<EndpointViewEntity> listEntity;

        if (uris == null || uris.size() == 0) {
            listEntity = repository.findAllByTimestampAfterAndTimestampBefore(start, end);
        } else {
            listEntity = repository.findAllByTimestampAfterAndTimestampBeforeAndUriIn(start, end, uris);
        }

        List<ViewStats> listViews = new ArrayList<>();

        if (unique) { //TODO переписать без циклов
            Map<ViewStatsIndicatorsWithIp, Integer> mapIndicatorsWithIp = new HashMap<>();

            for (EndpointViewEntity view : listEntity) {
                ViewStatsIndicatorsWithIp viewStatsIndicatorsWithIp = new ViewStatsIndicatorsWithIp(view.getApp(),
                        view.getUri(), view.getIp());
                if (!(mapIndicatorsWithIp.containsKey(viewStatsIndicatorsWithIp))) {
                    mapIndicatorsWithIp.put(viewStatsIndicatorsWithIp, 1);
                }
            }

            Map<ViewStats, Integer> mapView = new HashMap<>();

            for (Map.Entry<ViewStatsIndicatorsWithIp, Integer> view : mapIndicatorsWithIp.entrySet()) {
                ViewStats viewStats = new ViewStats(view.getKey().getApp(), view.getKey().getUri(), null);
                if (mapView.containsKey(viewStats)) {
                    mapView.put(viewStats, (mapView.get(viewStats) + 1));
                } else {
                    mapView.put(viewStats, 1);
                }
            }

            for (Map.Entry<ViewStats, Integer> view : mapView.entrySet()) {
                listViews.add(new ViewStats(view.getKey().getApp(), view.getKey().getUri(), view.getValue()));
            }
        } else {
            Map<ViewStatsIndicators, Integer> mapIndicators = new HashMap<>();

            for (EndpointViewEntity view : listEntity) {
                ViewStatsIndicators viewIndicators = new ViewStatsIndicators(view.getApp(), view.getUri());
                if (mapIndicators.containsKey(viewIndicators)) {
                    mapIndicators.put(viewIndicators, (mapIndicators.get(viewIndicators) + 1));
                } else {
                    mapIndicators.put(viewIndicators, 1);
                }
            }

            for (Map.Entry<ViewStatsIndicators, Integer> view : mapIndicators.entrySet()) {
                listViews.add(new ViewStats(view.getKey().getApp(), view.getKey().getUri(), view.getValue()));
            }
        }

        Collections.sort(listViews);
        return listViews;
    }

}
