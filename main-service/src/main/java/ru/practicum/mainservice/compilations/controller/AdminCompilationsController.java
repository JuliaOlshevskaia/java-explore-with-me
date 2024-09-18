package ru.practicum.mainservice.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilations.dto.CompilationDto;
import ru.practicum.mainservice.compilations.dto.NewCompilationDto;
import ru.practicum.mainservice.compilations.dto.UpdateCompilationRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {

    @PostMapping
    public CompilationDto saveCompilation(@RequestBody NewCompilationDto request) {
        return null;
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Integer compId) {
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Integer compId, @RequestBody UpdateCompilationRequest request) {
        return null;
    }


}
