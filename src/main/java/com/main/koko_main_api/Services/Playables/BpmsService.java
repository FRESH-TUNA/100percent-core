package com.main.koko_main_api.Services.Playables;

import com.main.koko_main_api.Dtos.BpmsSaveDto;
import com.main.koko_main_api.Models.Playable;
import com.main.koko_main_api.Repositories.PlayablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BpmsService {
    private final PlayablesRepository playablesRepository;

    @Transactional
    public void save(Long playable_id, BpmsSaveDto dto) {
        Playable playable = playablesRepository.findById(playable_id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + playable_id));
        playable.saveBpm(dto.toEntity());
    }
}
