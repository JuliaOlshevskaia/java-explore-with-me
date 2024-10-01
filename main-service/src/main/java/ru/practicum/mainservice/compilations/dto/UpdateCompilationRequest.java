package ru.practicum.mainservice.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class UpdateCompilationRequest {
    private List<Integer> events;

    @Builder.Default
    private Boolean pinned = false;

    @Size(min = 1, max = 50)
    private String title;

}
