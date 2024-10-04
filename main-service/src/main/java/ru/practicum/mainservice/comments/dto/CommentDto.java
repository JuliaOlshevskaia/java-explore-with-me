package ru.practicum.mainservice.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.users.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;

    private UserShortDto commentator;

    private EventShortDto event;

    private String comment;

    private LocalDateTime createdDate;
}
