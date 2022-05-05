package com.main.koko_main_api.controllers;
import com.main.koko_main_api.dtos.pattern.PatternRequestDto;
import com.main.koko_main_api.dtos.pattern.PatternResponseDto;

import com.main.koko_main_api.services.PatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/patterns")
public class PatternController {
    private final PatternService patternService;

    @GetMapping("/{id}")
    public PatternResponseDto findById(@PathVariable Long id) {
        return patternService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, PatternRequestDto dto) { patternService.update(id, dto); }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) { patternService.delete(id); }
}
