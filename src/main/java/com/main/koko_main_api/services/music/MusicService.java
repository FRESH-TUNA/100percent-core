package com.main.koko_main_api.services.music;

import com.main.koko_main_api.assemblers.music.MusicAssembler;
import com.main.koko_main_api.assemblers.music.MusicDeassembler;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;

import com.main.koko_main_api.dtos.music.*;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
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

    private final MusicFindAllHelper findAllHelper;
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

        return pageAssembler.toModel(findAllHelper.create(music_page, patterns), showAssembler);
    }

    public PagedModel<MusicResponseDto> findAllByAlbum(Pageable pageable, Long play_type_id, Long album_id) {
        Page<Music> music_page = musicRepository.findAllByAlbum(pageable, album_id);
        List<Pattern> patterns = patternRepository
                .findAllByPlayTypeAndMusics(music_page.getContent(), play_type_id);

        return pageAssembler.toModel(findAllHelper.create(music_page, patterns), showAssembler);
    }

    /*
     * playable/playtype + difficulty 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */
    public PagedModel<MusicResponseDto> findAllByDifficulty(Long play_type_id, Long difficulty_id) {
        // pattern을 가지고 온다.
        List<Pattern> patterns = patternRepository.findAllByPlayTypeAndDifficulty(play_type_id, difficulty_id);
        List<Music> musics = findAllHelper.getMusicsFromPatterns(patterns);
        musics = musicRepository.findAll(musics);
        Page<Music> music_page = findAllHelper.musicsToSinglePage(musics);

        return pageAssembler.toModel(findAllHelper.create(music_page, patterns), showAssembler);
    }

    /*
     * playable/playtype + level 기준 필터링
     * 1개의 page로 된 response를 반환한다.
     */
    public PagedModel<MusicResponseDto> findAllByLevel(Long play_type_id, Integer level) {
        // pattern을 가지고 온다.
        List<Pattern> patterns = patternRepository.findAllByPlayTypeAndLevel(play_type_id, level);
        List<Music> musics = findAllHelper.getMusicsFromPatterns(patterns);
        musics = musicRepository.findAll(musics);
        Page<Music> music_page = findAllHelper.musicsToSinglePage(musics);

        return pageAssembler.toModel(findAllHelper.create(music_page, patterns), showAssembler);
    }

    /*
     * create
     * 만드는중
     */
    @Transactional
    public MusicResponseDto create(MusicRequestDto musicSavePayloadDto) {
        Music music = deassembler.toEntity(musicSavePayloadDto);
        return showAssembler.toModel(musicRepository.save(music));
    }

    @Transactional
    public MusicResponseDto update(Long id, MusicRequestDto dto) {
        Music music = musicRepository.findById(id).get();

        Music new_music = deassembler.toEntity(dto);

        music.update(new_music);

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


    /*
     * helper
     */

}
