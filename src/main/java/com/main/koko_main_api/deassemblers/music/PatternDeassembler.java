package com.main.koko_main_api.deassemblers.music;

import com.main.koko_main_api.deassemblers.BaseDeassembler;
import com.main.koko_main_api.domains.*;

import com.main.koko_main_api.dtos.pattern.PatternRequestDto;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatternDeassembler extends BaseDeassembler {
    private final MusicRepository musicRepository;
    private final PlayTypesRepository playTypesRepository;
    private final DifficultyTypeRepository difficultyTypeRepository;

    public Pattern toEntity(Long music_id, PatternRequestDto dto) {
        DifficultyType difficultyType = difficultyTypeRepository.getById(convertURItoID(dto.getDifficultyType()));
        PlayType playType = playTypesRepository.getById(convertURItoID(dto.getPlayType()));
        Music music = musicRepository.getById(music_id);

        return Pattern.builder().level(dto.getLevel())
                .playType(playType).difficultyType(difficultyType).music(music)
                .build();
    }

    public Pattern toEntity(PatternRequestDto dto) {
        DifficultyType difficultyType = difficultyTypeRepository.getById(convertURItoID(dto.getDifficultyType()));
        PlayType playType = playTypesRepository.getById(convertURItoID(dto.getPlayType()));
        Music music = musicRepository.getById(convertURItoID(dto.getMusic()));

        return Pattern.builder().level(dto.getLevel())
                .playType(playType).difficultyType(difficultyType).music(music)
                .build();
    }
}
