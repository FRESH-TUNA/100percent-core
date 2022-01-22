package com.main.koko_main_api.Services;
import com.main.koko_main_api.Dtos.PlayablesResponseDto;
import com.main.koko_main_api.Dtos.PlayablesSaveDto;
import com.main.koko_main_api.Dtos.PlayablesSaveRequestDto;
import com.main.koko_main_api.Models.Music;
import com.main.koko_main_api.Repositories.MusicsRepository;
import com.main.koko_main_api.Repositories.PlayablesRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PlayablesService {
    private final PlayablesRepository playablesRepository;
    private final MusicsRepository musicsRepository;
    private final UriToIDService uriToIDService;

    @Transactional
    public PlayablesResponseDto save(PlayablesSaveRequestDto dto) {
        Long music_id = uriToIDService.convert(dto.getMusic());
        Music music = musicsRepository.findById(music_id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + dto.getMusic()));

        PlayablesSaveDto saveDto = PlayablesSaveDto
                .builder().music(music).bpms(dto.getBpms())
                .level(dto.getLevel()).build();

        return new PlayablesResponseDto(
                playablesRepository.save(saveDto.toEntity()));
    }
}
