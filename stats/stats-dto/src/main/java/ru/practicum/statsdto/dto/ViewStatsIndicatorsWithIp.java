package ru.practicum.statsdto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStatsIndicatorsWithIp {
    private String app;
    private String uri;
    private String ip;
}
