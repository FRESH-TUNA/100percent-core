package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.dtos.music.MusicListDto;
import com.main.koko_main_api.repositories.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;

    private final MusicFindAllService musicFindAllService;

    /*
     * playtype_id가 반드시 전달되어서 필터링된다.
     */
    public Page<MusicListDto> findAll(Pageable pageable, MusicRequestParams params) {
        return musicFindAllService.call(pageable, params);
    }
}
