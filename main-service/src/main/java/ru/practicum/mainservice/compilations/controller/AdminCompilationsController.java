package ru.practicum.mainservice.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilations.dto.CompilationDto;
import ru.practicum.mainservice.compilations.dto.NewCompilationDto;
import ru.practicum.mainservice.compilations.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.compilations.service.CompilationService;

import javax.validation.Valid;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {
    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto saveCompilation(@Valid @RequestBody NewCompilationDto request) {
        return service.saveCompilation(request);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId) {
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Integer compId, @Valid @RequestBody UpdateCompilationRequest request) {
        return service.updateCompilation(compId, request);
    }
}
