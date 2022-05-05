package com.main.koko_main_api.services;

import com.main.koko_main_api.assemblers.PatternAssembler;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.pattern.PatternResponseDto;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatternService {
    private final PatternRepository patternRepository;
    private final PatternAssembler assembler;

    public PatternResponseDto findById(Long id) {
        Pattern pattern = patternRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        return assembler.toModel(pattern);
    }
}
