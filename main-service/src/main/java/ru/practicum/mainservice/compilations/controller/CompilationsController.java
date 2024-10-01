package ru.practicum.mainservice.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilations.dto.CompilationDto;
import ru.practicum.mainservice.compilations.service.CompilationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/compilations")
public class CompilationsController {
    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                         @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Integer compId) {
        return service.getCompilation(compId);
    }



}
