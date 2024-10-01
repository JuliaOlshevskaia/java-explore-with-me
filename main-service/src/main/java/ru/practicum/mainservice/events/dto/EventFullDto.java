package ru.practicum.mainservice.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.users.dto.UserShortDto;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    @NotNull
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Integer confirmedRequests;
    private String createdOn;
    private String description;
    @NotNull
    private String eventDate;
    private Integer id;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Location location;
    @NotNull
    private Boolean paid;
    private Integer participantLimit; // default = 0
    private String publishedOn;
    private Boolean requestModeration; // default = true
    private String state;
    @NotNull
    private String title;
    private Integer views;
}
