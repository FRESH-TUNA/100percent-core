package com.main.koko_main_api.deassemblers.music;

import com.main.koko_main_api.deassemblers.BaseDeassembler;
import com.main.koko_main_api.domains.*;

import com.main.koko_main_api.dtos.pattern.PatternRequestDto;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatternDeassembler extends BaseDeassembler {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PlayTypesRepository playTypesRepository;

    @Autowired
    private DifficultyTypeRepository difficultyTypeRepository;

    public Pattern toEntity(Long music_id, PatternRequestDto dto) {
        DifficultyType difficultyType = difficultyTypeRepository.getById(convertURItoID(dto.getDifficultyType()));
        PlayType playType = playTypesRepository.getById(convertURItoID(dto.getPlayType()));
        Music music = musicRepository.getById(music_id);

        return Pattern.builder().level(dto.getLevel())
                .playType(playType).difficultyType(difficultyType).music(music)
                .build();
    }
}
