package ru.practicum.mainservice.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
//    @NotNull
//    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

//    @NotNull
    private Integer category;

//    @NotNull
//    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

//    @NotNull
    private String eventDate;

//    @NotNull
    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    private StateActionEnum stateAction; //enum SEND_TO_REVIEW CANCEL_REVIEW

//    @NotNull
    @Size(min = 3, max = 120)
    private String title;
}
