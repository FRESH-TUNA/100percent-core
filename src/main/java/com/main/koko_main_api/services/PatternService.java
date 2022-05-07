package com.main.koko_main_api.services;

import com.main.koko_main_api.assemblers.PatternAssembler;
import com.main.koko_main_api.deassemblers.music.PatternDeassembler;
import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.pattern.PatternRequestDto;
import com.main.koko_main_api.dtos.pattern.PatternResponseDto;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatternService {
    private final PatternRepository patternRepository;
    private final PatternAssembler assembler;
    private final PatternDeassembler deassembler;

    public PatternResponseDto findById(Long id) {
        Pattern pattern = patternRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        return assembler.toModel(pattern);
    }

    @Transactional
    public void update(Long id, PatternRequestDto dto) {
        Pattern pattern = patternRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        Pattern new_pattern = deassembler.toEntity(dto);
        pattern.update(new_pattern);
    }

    @Transactional
    public void delete(Long id) {
        patternRepository.deleteById(id);
    }
}
