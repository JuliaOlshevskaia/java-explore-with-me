package ru.practicum.mainservice.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilations.dto.CompilationDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/compilations")
public class CompilationsController {

    @GetMapping
    public List<CompilationDto> getStats(@RequestParam("pinned") Boolean pinned,
                                         @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getStats(@PathVariable Integer compId) {
        return null;
    }



}
