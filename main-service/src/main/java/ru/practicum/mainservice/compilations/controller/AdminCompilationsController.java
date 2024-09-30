package ru.practicum.mainservice.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.categories.service.CategoriesService;
import ru.practicum.mainservice.compilations.dto.CompilationDto;
import ru.practicum.mainservice.compilations.dto.NewCompilationDto;
import ru.practicum.mainservice.compilations.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.compilations.service.CompilationService;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {
    private final CompilationService service;

    @PostMapping
    public CompilationDto saveCompilation(@RequestBody NewCompilationDto request) {
        return service.saveCompilation(request);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Integer compId) {
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Integer compId, @RequestBody UpdateCompilationRequest request) {
        return service.updateCompilation(compId, request);
    }


}
