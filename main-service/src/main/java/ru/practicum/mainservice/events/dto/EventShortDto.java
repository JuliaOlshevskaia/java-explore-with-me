package ru.practicum.mainservice.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.mainservice.categories.dto.CategoryDto;
import ru.practicum.mainservice.users.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class EventShortDto {
    @NotNull
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Integer confirmedRequests;
    @NotNull
    private String eventDate;
    private Integer id;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Boolean paid;
    @NotNull
    private String title;
    private Integer views;


    public int compareTo(Object obj1, TypeSortEvent typeSort) {
        EventShortDto that = (EventShortDto)obj1;

        if (typeSort == TypeSortEvent.EVENT_DATE) {
            LocalDateTime p1 = LocalDateTime.parse(this.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime p2 = LocalDateTime.parse(that.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            if (p1.isAfter(p2)) {
                return -1;
            } else if (p1.isBefore(p2)) {
                return 1;
            } else
                return 0;
        } else {
            Integer p1 = this.getViews();
            Integer p2 = that.getViews();

            if (p1 > p2) {
                return -1;
            } else if (p1 < p2) {
                return 1;
            } else
                return 0;
        }
    }
}
