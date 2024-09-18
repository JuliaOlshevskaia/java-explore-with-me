package ru.practicum.mainservice.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.users.dto.UserShortDto;

@Data
@AllArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private Integer id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;
}
