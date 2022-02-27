package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.MusicDto;
import com.main.koko_main_api.dtos.music.MusicDtoAssembler;
import com.main.koko_main_api.dtos.music.MusicSaveDto;
import com.main.koko_main_api.dtos.music.MusicSaveDtoDeassembler;
import com.main.koko_main_api.repositories.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final MusicSaveDtoDeassembler deassembler;
    private final MusicDtoAssembler assembler;
    private final MusicFindAllService musicFindAllService;

    /*
     * playtype_id가 반드시 전달되어서 필터링된다.
     */
    public Page<MusicDto> findAll(Pageable pageable, MusicRequestParams params) {
        return musicFindAllService.call(pageable, params);
    }

    /*
     * create
     */
    @Transactional
    public MusicDto save(MusicSaveDto musicSavePayloadDto) {
        Music music = deassembler.toEntity(musicSavePayloadDto);

        MusicDto musicDto = new MusicDto(musicRepository.save(music));

        return musicDto;
    }
}
