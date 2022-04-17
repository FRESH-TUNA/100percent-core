package com.main.koko_main_api.services.music;

import com.main.koko_main_api.assemblers.music.MusicAssembler;
import com.main.koko_main_api.assemblers.music.MusicDeassembler;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;

import com.main.koko_main_api.dtos.music.*;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import com.main.koko_main_api.utils.MusicFindAllPageCreater;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final PatternRepository patternRepository;

    private final MusicFindAllPageCreater pageCreater;
    private final MusicDeassembler deassembler;
    private final MusicAssembler showAssembler;
    private final PagedResourcesAssembler<Music> pageAssembler;


    /*
     * 기본 findAll methods
     */
    public PagedModel<MusicResponseDto> findAll(Pageable pageable, Long play_type_id) {
        Page<Music> music_page = musicRepository.findAll(pageable);

        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_page.getContent(), play_type_id);

        return pageAssembler.toModel(pageCreater.call(music_page, patterns), showAssembler);
    }

    public PagedModel<MusicResponseDto> findAllByAlbum(Pageable pageable, Long play_type_id, Long album_id) {
        Page<Music> music_page = musicRepository.findAllByAlbum(pageable, album_id);

        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_page.getContent(), play_type_id);

        Page<Music> res = pageCreater.call(music_page, patterns);

        return pageAssembler.toModel(pageCreater.call(music_page, patterns), showAssembler);
    }

    /*
     * create
     * 만드는중
     */
    @Transactional
    public MusicResponseDto create_or_update(MusicRequestDto musicSavePayloadDto) {
        Music music = deassembler.toEntity(musicSavePayloadDto);
        return showAssembler.toModel(musicRepository.save(music));
    }

    /*
     * findbyid
     */
    public MusicResponseDto findById(Long id) {
        Music m = musicRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 음아깅 없습니다."));
        return showAssembler.toModel(m);
    }

    /*
     * delete
     */
    public void destroy(Long id) {
        musicRepository.deleteById(id);
    }
}
