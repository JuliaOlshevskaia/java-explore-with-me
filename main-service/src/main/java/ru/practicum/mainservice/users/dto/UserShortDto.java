package ru.practicum.mainservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserShortDto {
    @NotNull
    private Integer id;
    @NotNull
    private String name;
}
