package com.main.koko_main_api.Services;

import com.main.koko_main_api.Dtos.PlayablesResponseDto;
import com.main.koko_main_api.Dtos.PlayablesSaveDto;
import com.main.koko_main_api.Dtos.PlayablesSaveRequestDto;
import com.main.koko_main_api.Models.Bpm;
import com.main.koko_main_api.Models.Music;
import com.main.koko_main_api.Models.Playable;
import com.main.koko_main_api.Repositories.BpmsRepository;
import com.main.koko_main_api.Repositories.MusicsRepository;
import com.main.koko_main_api.Repositories.PlayablesRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayablesService {
    private final PlayablesRepository playablesRepository;
    private final MusicsRepository musicsRepository;
    private final BpmsRepository bpmsRepository;
    private final UriToIDService uriToIDService;

    @Transactional
    public PlayablesResponseDto save(PlayablesSaveRequestDto dto) {
        Long music_id = uriToIDService.convert(dto.getMusic());
        Music music = musicsRepository.findById(music_id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + dto.getMusic()));

        PlayablesSaveDto saveDto = PlayablesSaveDto
                .builder().music(music).level(dto.getLevel()).build();

        Playable playable = playablesRepository.save(saveDto.toEntity());

        bpmsRepository.saveAll(dto.getBpms().parallelStream().map(
                bpm -> bpm.toEntity(playable)).collect(Collectors.toList()));

        return this.findById(playable.getId());
    }

    public PlayablesResponseDto findById(Long id) {
        Playable p = playablesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + id));

        return new PlayablesResponseDto(p);
    }
}
