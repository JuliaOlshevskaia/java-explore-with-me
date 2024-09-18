package ru.practicum.mainservice.requests.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;
import ru.practicum.mainservice.users.dto.NewUserRequest;
import ru.practicum.mainservice.users.dto.UserDto;

import java.util.List;

public interface RequestsService {
    List<ParticipationRequestDto> getUserRequests(Integer userId);
    ParticipationRequestDto addParticipationRequest(Integer userId, Integer eventId);
    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);
}
