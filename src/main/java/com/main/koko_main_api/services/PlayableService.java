package com.main.koko_main_api.services;

import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.entityDtos.playable.PlayableDetailResponseEntityDto;
import com.main.koko_main_api.entityDtos.playable.PlayableListResponseEntityDto;
import com.main.koko_main_api.entityDtos.playable.PlayableSaveEntityDto;
import com.main.koko_main_api.payloads.playable.PlayableSavePayload;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.repositories.BpmsRepository;
import com.main.koko_main_api.repositories.MusicsRepository;
import com.main.koko_main_api.repositories.PlayablesRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayableService extends URIToID {
    private final PlayablesRepository playablesRepository;
    private final MusicsRepository musicsRepository;
    private final BpmsRepository bpmsRepository;

    @Transactional
    public PlayableDetailResponseEntityDto save(PlayableSavePayload dto) {
        Long music_id = this.convertURItoID(dto.getMusic());
        Music music = musicsRepository.findById(music_id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + music_id));
        PlayableSaveEntityDto saveDto = PlayableSaveEntityDto
                .builder().music(music).level(dto.getLevel()).build();

        Playable playable = playablesRepository.save(saveDto.toEntity());
        List<Bpm> bpms = bpmsRepository.saveAll(dto.getBpms().stream().map(
                bpm -> bpm.toEntity(playable)).collect(Collectors.toList()));
        /*
         * update 쿼리가 발생한다.
         */
        playable.add_bpms_for_save_request(bpms);

        return new PlayableDetailResponseEntityDto(playable);
    }

    public PlayableDetailResponseEntityDto findById(Long id) {
        Playable p = playablesRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + id));

        return new PlayableDetailResponseEntityDto(p);
    }

    public Page<PlayableListResponseEntityDto> findAll(Pageable pageable) {
        return playablesRepository.findAll(pageable).map(
                p -> new PlayableListResponseEntityDto(p));
    }
}