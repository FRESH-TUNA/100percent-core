package com.main.koko_main_api.services.music;

import com.main.koko_main_api.assemblers.music.MusicAssembler;
import com.main.koko_main_api.domains.*;

import com.main.koko_main_api.dtos.music.MusicRequestDto;
import com.main.koko_main_api.assemblers.music.MusicDeassembler;
import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
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
import java.util.Optional;

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
    private MusicDeassembler deassembler;

    @Mock
    private MusicAssembler showAssembler;


    @Test
    public void create_or_update_테스트() {
        MusicRequestDto requestDto = new MusicRequestDto();
        Music music = Music.builder().title("music").album(Album.builder().title("hoho").id(1L).build()).build();

        /*
         * when
         */
        when(deassembler.toEntity(requestDto)).thenReturn(music);
        when(musicRepository.save(music)).thenReturn(music);
        when(showAssembler.toModel(music)).thenReturn(new MusicResponseDto(music));
        MusicResponseDto result = musicService.create_or_update(requestDto);

        /*
         * then
         */
        assertThat(result.getClass()).isEqualTo(MusicResponseDto.class);
    }

    @Test
    public void id로검색텍스트() {
        Music music = Music.builder().title("music").album(Album.builder().title("hoho").id(1L).build()).build();

        /*
         * when
         */
        when(musicRepository.findById(music.getId())).thenReturn(Optional.of(music));
        when(showAssembler.toModel(music)).thenReturn(new MusicResponseDto(music));
        MusicResponseDto result = musicService.findById(music.getId());

        /*
         * then
         */
        assertThat(result.getClass()).isEqualTo(MusicResponseDto.class);
    }
}
