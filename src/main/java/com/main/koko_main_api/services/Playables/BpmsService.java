package com.main.koko_main_api.services.Playables;

import com.main.koko_main_api.repositories.PlayablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BpmsService {
    private final PlayablesRepository playablesRepository;

//    @Transactional
//    public void save(Long playable_id, BpmsSaveDto dto) {
//        Playable playable = playablesRepository.findById(playable_id).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + playable_id));
//        playable.saveBpm(dto.toEntity());
//    }
}
