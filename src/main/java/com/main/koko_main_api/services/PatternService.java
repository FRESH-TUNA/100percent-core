package com.main.koko_main_api.services;

import com.main.koko_main_api.domains.Pattern;
import com.main.koko_main_api.dtos.pattern.PatternDetailResponseEntityDto;
import com.main.koko_main_api.dtos.pattern.PatternSaveEntityDto;
import com.main.koko_main_api.dtos.pattern.PatternSavePayload;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.repositories.BpmRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;

import com.main.koko_main_api.repositories.pattern.PatternSearchRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PatternService extends URIToID {
    private final PatternRepository patternRepository;
    private final MusicRepository musicRepository;
    private final BpmRepository bpmRepository;
    private final PatternSearchRepository patternSearchRepository;

    @Transactional
    public PatternDetailResponseEntityDto save(PatternSavePayload dto) {
        Long music_id = this.convertURItoID(dto.getMusic());
        Music music = musicRepository.findById(music_id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + music_id));
        PatternSaveEntityDto saveDto = PatternSaveEntityDto
                .builder().music(music).level(dto.getLevel()).build();

        Pattern pattern = patternRepository.save(saveDto.toEntity());

        return new PatternDetailResponseEntityDto(pattern);
    }

    public PatternDetailResponseEntityDto findById(Long id) {
        Pattern p = patternRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + id));

        return new PatternDetailResponseEntityDto(p);
    }
}
