package ru.practicum.mainservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserShortDto {
    private Integer id;
    private String name;
}
