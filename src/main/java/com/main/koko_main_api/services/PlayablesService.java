package com.main.koko_main_api.services;

import com.main.koko_main_api.dtos.playable.PlayablesResponseDto;
import com.main.koko_main_api.dtos.playable.PlayablesSaveDto;
import com.main.koko_main_api.dtos.playable.PlayableSavePayload;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.repositories.BpmsRepository;
import com.main.koko_main_api.repositories.MusicsRepository;
import com.main.koko_main_api.repositories.PlayablesRepository;
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
    public PlayablesResponseDto save(PlayableSavePayload dto) {
        Long music_id = uriToIDService.convert(dto.getMusic());

        Music music = musicsRepository.findById(music_id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + dto.getMusic()));

        PlayablesSaveDto saveDto = PlayablesSaveDto
                .builder().music(music).level(dto.getLevel()).build();

        Playable playable = playablesRepository.save(saveDto.toEntity());

        bpmsRepository.saveAll(dto.getBpms().stream().map(
                bpm -> bpm.toEntity(playable)).collect(Collectors.toList()));

        return new PlayablesResponseDto(playable);
    }

    public PlayablesResponseDto findById(Long id) {
        Playable p = playablesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + id));

        return new PlayablesResponseDto(p);
    }
}
