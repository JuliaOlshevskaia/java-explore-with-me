package ru.practicum.mainservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
    @Size(min = 2, max = 250)
    private String name;
    @Email
    @Size(min = 6, max = 254)
    private String email;
}
