package ru.practicum.statsdto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStatsIndicators {
    private String app;
    private String uri;
}
