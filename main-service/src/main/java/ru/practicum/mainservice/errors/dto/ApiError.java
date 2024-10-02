package ru.practicum.mainservice.errors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    List<String> errors;

    String message;

    String reason;

    String status;

    Timestamp timestamp;
}
