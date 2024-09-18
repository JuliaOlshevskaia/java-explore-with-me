package ru.practicum.mainservice.compilations.dto;

import ru.practicum.mainservice.events.dto.EventShortDto;

import java.util.List;

public class CompilationDto {
    private List<EventShortDto> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}
