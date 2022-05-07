package com.main.koko_main_api.services.music;

import com.main.koko_main_api.deassemblers.music.PatternDeassembler;

import com.main.koko_main_api.dtos.pattern.PatternRequestDto;
import com.main.koko_main_api.repositories.pattern.PatternRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MusicPatternService {
    private final PatternRepository patternRepository;
    private final PatternDeassembler deassembler;

    @Transactional
    public void create(Long music_id, PatternRequestDto dto) {
        patternRepository.save(deassembler.toEntity(music_id, dto));
    }
}
