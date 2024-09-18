package ru.practicum.mainservice.compilations.dto;

import javax.validation.constraints.Size;
import java.util.List;

public class UpdateCompilationRequest {
    private List<Integer> events;

    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;

}
