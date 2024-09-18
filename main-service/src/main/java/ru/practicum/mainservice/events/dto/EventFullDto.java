package ru.practicum.mainservice.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.users.dto.UserShortDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String createdOn; //date
    private String description;
    private String eventDate;
    private Integer id;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn; //date
    private Boolean requestModeration; // default true
    private String state; //enum PENDING PUBLISHED CANCELED
    private String title;
    private Integer views;
}
