package ru.practicum.statsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statsdto.dto.EndpointHit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public StatsClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getStats(String startTime, String endTime, List<String> uris, Boolean unique) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        if (uris != null && unique != null) {
            parameters.put("uris", uris);
            parameters.put("unique", unique);
            return get("/stats?startTime={startTime}&endTime={endTime}&uris={uris}&unique={unique}", parameters);
        } else if (uris == null && unique != null) {
            parameters.put("unique", unique);
            return get("/stats?startTime={startTime}&endTime={endTime}&unique={unique}", parameters);
        } else if (uris != null && unique == null) {
            parameters.put("uris", uris);
            return get("/stats?startTime={startTime}&endTime={endTime}&uris={uris}", parameters);
        } else {
            return get("/stats?startTime={startTime}&endTime={endTime}", parameters);
        }
    }

    public ResponseEntity<Object> hit(EndpointHit request) {
        return post("/hit", null, request);
    }
}
