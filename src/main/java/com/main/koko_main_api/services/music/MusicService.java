package com.main.koko_main_api.services.music;

import com.main.koko_main_api.controllers.music.MusicRequestParams;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.dtos.music.*;
import com.main.koko_main_api.dtos.music.playables.MusicPlayablesDto;
import com.main.koko_main_api.repositories.music.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final MusicSaveDtoDeassembler deassembler;
    private final MusicDtoAssembler showAssembler;
    private final PagedResourcesAssembler<MusicEntityToServiceDto> pageAssembler;
    private final MusicFilterFindAllService musicFilterFindAllService;

    /*
     * playtype_id가 반드시 전달되어서 필터링된다.
     */
    public PagedModel<MusicDto> findAll(Pageable pageable, MusicRequestParams params) {
        String filter = params.getFilter();

        if(filter == null || filter.equals("db"))
            return pageAssembler.toModel(pagedfindAll(pageable, params), showAssembler);

        Page<MusicEntityToServiceDto> page = list_to_single_page(
                musicFilterFindAllService.call(params));

        return pageAssembler.toModel(page, showAssembler);
    }

    /*
     * playable/playType + album 에따른 필터링을 지원하는 findall 메소드
     * difficulty 기준 필터링
     */
    private Page<MusicEntityToServiceDto> pagedfindAll(Pageable pageable, MusicRequestParams params) {
        Page<Music> musics_page = musicRepository.findAll(pageable, params.getAlbum());
        Long play_type_id = params.getPlay_type();
        List<Music> musics = musics_page.getContent();
        List<MusicEntityToServiceDto> musicEntityToServiceDtos = new ArrayList<>();

        for(Music m : musics) {
            List<MusicPlayablesDto> filtered_playables = new ArrayList<>();
            List<Playable> playables = m.getPlayables();

            // play_type filtering
            for(Playable playable : playables)
                if(playable.getPlayType().getId().equals(play_type_id))
                    filtered_playables.add(new MusicPlayablesDto(playable));

            musicEntityToServiceDtos.add(new MusicEntityToServiceDto(m, filtered_playables));
        }

        return new PageImpl<>(musicEntityToServiceDtos, pageable, musics_page.getTotalElements());
    }

    /*
     * create
     */
    @Transactional
    public MusicDto save(MusicSaveDto musicSavePayloadDto) {
        Music music = deassembler.toEntity(musicSavePayloadDto);
        MusicEntityToServiceDto dto = new MusicEntityToServiceDto(
                musicRepository.save(music));
        return showAssembler.toModel(dto);
    }

    /*
     * helper
     */
    private Page<MusicEntityToServiceDto> list_to_single_page(
            List<MusicEntityToServiceDto> dtos) {
        int list_size = dtos.size();
        return new PageImpl<>(dtos, PageRequest.of(0, list_size), list_size);
    }
}
