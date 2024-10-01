package ru.practicum.mainservice.requests.service;

import ru.practicum.mainservice.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestsService {
    List<ParticipationRequestDto> getUserRequests(Integer userId);

    ParticipationRequestDto addParticipationRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);
}
