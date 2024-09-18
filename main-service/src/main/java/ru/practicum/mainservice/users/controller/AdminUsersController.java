package ru.practicum.mainservice.users.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.categories.service.CategoriesService;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;
import ru.practicum.mainservice.users.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
public class AdminUsersController {
    private final UserService service;

    @GetMapping()
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                  @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto registerUser(@RequestBody NewUserRequest request) {
        return service.registerUser(request);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId) {
        service.delete(userId);
    }
}
