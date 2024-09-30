package ru.practicum.mainservice.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.events.dto.EventShortDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private List<EventShortDto> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}
