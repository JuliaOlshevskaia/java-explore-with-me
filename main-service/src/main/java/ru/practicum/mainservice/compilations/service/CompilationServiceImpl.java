package ru.practicum.mainservice.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainservice.compilations.dto.CompilationDto;
import ru.practicum.mainservice.compilations.dto.NewCompilationDto;
import ru.practicum.mainservice.compilations.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.compilations.entity.CompilationEntity;
import ru.practicum.mainservice.compilations.mapper.CompilationMapper;
import ru.practicum.mainservice.compilations.repository.CompilationRepository;
import ru.practicum.mainservice.errors.exceptions.DataNotFoundException;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.entity.EventsEntity;
import ru.practicum.mainservice.events.mapper.EventsMapper;
import ru.practicum.mainservice.events.repository.EventsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final CompilationMapper mapper;
    private final EventsMapper eventsMapper;
    private final EventsRepository eventsRepository;

    @Override
    public CompilationDto saveCompilation(NewCompilationDto request) {
        CompilationEntity entity = new CompilationEntity(null, request.getTitle(), request.getPinned(), null);
        CompilationEntity entitySaved = repository.save(entity);

        List<EventShortDto> eventShortDtos = new ArrayList<>();

        if (request.getEvents() != null && request.getEvents().size() > 0) {
            List<EventsEntity> events = eventsRepository.findAllByIdIn(request.getEvents());
            List<EventsEntity> eventsUpdated = new ArrayList<>();
            for (EventsEntity event : events) {
                event.setCompilation(entitySaved);
                eventsUpdated.add(event);
                EventShortDto eventShortDto = eventsMapper.toShortDto(event);
                eventShortDtos.add(eventShortDto);
            }
            if (eventsUpdated.size() > 0) {
                eventsRepository.saveAll(eventsUpdated);
            }
        }
        CompilationDto dto = mapper.toDto(entitySaved);
        dto.setEvents(eventShortDtos);
        return dto;
    }

    @Override
    public void deleteCompilation(Integer compId) {
        if (!repository.existsById(compId)) {
            throw new DataNotFoundException("Compilation with id=" + compId + " was not found");
        }
        repository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest request) {
        CompilationEntity entity = repository.findById(compId).orElseThrow(() ->
                new DataNotFoundException("Compilation with id=" + compId + " was not found"));

        if (request.getTitle() != null && !(entity.getTitle().equals(request.getTitle()))) {
            entity.setTitle(request.getTitle());
        }

        if (request.getPinned() != null && !(entity.getPinned().equals(request.getPinned()))) {
            entity.setPinned(request.getPinned());
        }

        CompilationEntity entitySaved = repository.save(entity);

        CompilationDto dto = mapper.toDto(entity);
        List<EventShortDto> eventShortDtos = new ArrayList<>();

        if (request.getEvents() != null && request.getEvents().size() > 0) {
            List<EventsEntity> events = eventsRepository.findAllByIdIn(request.getEvents());
            List<EventsEntity> eventsUpdated = new ArrayList<>();
            for (EventsEntity event : events) {
                if (event.getCompilation() == null || !(event.getCompilation().getId().equals(compId))) {
                    event.setCompilation(entitySaved);
                    eventsUpdated.add(event);
                    EventShortDto eventShortDto = eventsMapper.toShortDto(event);
                    eventShortDtos.add(eventShortDto);
                }
            }
            if (eventsUpdated.size() > 0) {
                eventsRepository.saveAll(eventsUpdated);
            }
            dto.setEvents(eventShortDtos);
        }
        return dto;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageParam = PageRequest.of(from > 0 ? from / size : 0, size);

        List<CompilationEntity> compilationEntities = new ArrayList<>();

        if (pinned == null) {
            compilationEntities = repository.findAll(pageParam).getContent();
        } else {
            compilationEntities = repository.findAllByPinnedIs(pinned, pageParam);
        }
        List<CompilationDto> dtos = new ArrayList<>();

        for (CompilationEntity entity : compilationEntities) {
            CompilationDto dto = mapper.toDto(entity);
            List<EventShortDto> eventShortDtos = new ArrayList<>();
            for (EventsEntity event : entity.getEvents()) {
                EventShortDto eventShortDto = eventsMapper.toShortDto(event);
                eventShortDtos.add(eventShortDto);
            }
            dto.setEvents(eventShortDtos);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public CompilationDto getCompilation(Integer compId) {
        CompilationEntity entity = repository.findById(compId).orElseThrow(() ->
                new DataNotFoundException("Compilation with id=" + compId + " was not found"));

        CompilationDto dto = mapper.toDto(entity);
        List<EventShortDto> eventShortDtos = new ArrayList<>();
        for (EventsEntity event : entity.getEvents()) {
            EventShortDto eventShortDto = eventsMapper.toShortDto(event);
            eventShortDtos.add(eventShortDto);
        }
        dto.setEvents(eventShortDtos);

        return dto;
    }
}
