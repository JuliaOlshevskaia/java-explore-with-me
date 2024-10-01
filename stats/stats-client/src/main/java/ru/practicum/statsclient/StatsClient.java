package ru.practicum.statsclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statsdto.dto.EndpointHit;
import ru.practicum.statsdto.dto.ViewStats;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
//    private final String serverUrl;
//    private final RestTemplate restTemplate;
//
//    public StatsClient(@Value("${stat-server.url}") String serverUrl, RestTemplate restTemplate) {
//        this.serverUrl = serverUrl;
//        this.restTemplate = restTemplate;
//    }
//
//    public void createHit(EndpointHit hitDto) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(hitDto, headers);
//        restTemplate.exchange(serverUrl + "/hit", HttpMethod.POST, requestEntity, EndpointHit.class);
//    }
//
//    public List<ViewStats> getStat(String start, String end, List<String> uris, Boolean unique) {
//
//        Map<String, Object> parameters = new HashMap<>();
//
//        parameters.put("start", start);
//        parameters.put("end", end);
//        parameters.put("uris", uris);
//        parameters.put("unique", unique);
//
//        ResponseEntity<String> response = restTemplate.getForEntity(
//                serverUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
//                String.class, parameters);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            return Arrays.asList(objectMapper.readValue(response.getBody(), ViewStats[].class));
//        } catch (JsonProcessingException exception) {
//            throw new RuntimeException(String.format("Json processing error: %s", exception.getMessage()));
//        }
//    }


//    private static final String API_PREFIX = "/bookings";

//    public StatClient(@Value("${stat-server.url}") String serverUrl, RestTemplate restTemplate) {
//        this.serverUrl = serverUrl;
//        this.restTemplate = restTemplate;
//    }

    @Autowired
    public StatsClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
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
