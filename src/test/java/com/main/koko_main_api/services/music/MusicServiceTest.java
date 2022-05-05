package com.main.koko_main_api.services.music;

import com.main.koko_main_api.assemblers.MusicAssembler;
import com.main.koko_main_api.domains.*;

import com.main.koko_main_api.deassemblers.MusicDeassembler;
import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.repositories.music.MusicRepository;

import com.main.koko_main_api.repositories.pattern.PatternRepository;
import com.main.koko_main_api.services.MusicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/*
 * 참고자료
 * https://tecoble.techcourse.co.kr/post/2021-08-15-pageable/
 * test complete
 */
@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)

public class MusicServiceTest {
    @InjectMocks
    private MusicService musicService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private PatternRepository patternRepository;

    @Mock
    private MusicFindAllHelper pageCreater;

    @Mock
    private MusicDeassembler deassembler;

    @Mock
    private MusicAssembler showAssembler;

    @Mock
    private PagedResourcesAssembler<Music> pageAssembler;

    @Test
    public void findAll_페이징_테스트() {
        List<Music> musics = new ArrayList<>();
        List<Pattern> patterns = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 1);
        Page<Music> music_page = new PageImpl<Music>(musics, pageable, musics.size());
        Page<MusicResponseDto> response_page = new PageImpl<>(new ArrayList<>(), pageable, musics.size());
        Long play_type_id = 1L;

        /*
         * when
         */
        when(musicRepository.findAll(pageable)).thenReturn(music_page);
        when(patternRepository.findAllByPlayTypeAndMusics(music_page.getContent(), play_type_id)).thenReturn(patterns);
        when(pageCreater.create(music_page, patterns)).thenReturn(music_page);
        when(pageAssembler.toModel(music_page, showAssembler)).thenReturn(
                PagedModel.of(response_page.getContent(),
                        new PagedModel.PageMetadata(pageable.getPageSize(),
                                music_page.getNumber(), music_page.getTotalElements())));

        /*
         * then
         */
        PagedModel<MusicResponseDto> result = musicService.findAll(pageable, play_type_id);
        assertThat(result.getClass()).isEqualTo(PagedModel.class);
    }
}
