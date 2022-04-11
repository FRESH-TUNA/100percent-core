package com.main.koko_main_api.services.music;

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
    private MusicDeassembler deassembler;

    @Test
    public void findAll_테스트() {
        /*
         * data mocking
         */
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        Album album = Album.builder().title("album").build();
        PlayType five_key = PlayType.builder().title("5K").id(1L).build();
        PlayType six_key = PlayType.builder().title("6K").id(2L).build();
        List<Pattern> five_patterns = new ArrayList<>();
        List<Pattern> six_patterns = new ArrayList<>();
        Long playable_id = 1L;

        for(int i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().album(album).id((long) i).build();
            musics.add(music);

            Pattern pattern_hard_type = Pattern.builder()
                    .id(playable_id++)
                    .music(music).playType(five_key).build();

            Pattern pattern_normal_type = Pattern.builder()
                    .id(playable_id++)
                    .music(music).playType(six_key).build();

            music.add_pattern(pattern_hard_type);
            five_patterns.add(pattern_hard_type);

            music.add_pattern(pattern_normal_type);
            six_patterns.add(pattern_hard_type);
        }

        Pageable page = PageRequest.of(0, 1);
        Page<Music> music_page = new PageImpl<>(musics, page, MUSIC_LENGTH);

        when(musicRepository.findAll(page)).thenReturn(music_page);
        when(patternRepository.findAllByPlayTypeAndMusics(musics, five_key.getId()))
                .thenReturn(five_patterns);
        when(patternRepository.findAllByPlayTypeAndMusics(musics, six_key.getId()))
                .thenReturn(six_patterns);

        /*
         * when
         */
        PagedModel<MusicResponseDto> five_key_result = musicService.findAll(page, five_key.getId());
        PagedModel<MusicResponseDto> six_key_result = musicService.findAll(page, six_key.getId());

        /*
         * then
         */
        assertThat(five_key_result.getContent().size()).isEqualTo(2);
        assertThat(six_key_result.getContent().size()).isEqualTo(2);
    }

    @Test
    public void findAll_by_앨범_테스트() {
        /*
         * data mocking
         */
        final int MUSIC_LENGTH = 2;
        List<Music> musics = new ArrayList<>();
        Album album = Album.builder().title("album").build();
        PlayType five_key = PlayType.builder().title("5K").id(1L).build();
        List<Pattern> five_patterns = new ArrayList<>();
        Long playable_id = 1L;

        for(int i = 0; i < MUSIC_LENGTH; ++i) {
            Music music = Music.builder().album(album).id((long) i).build();
            musics.add(music);

            Pattern pattern_hard_type = Pattern.builder()
                    .id(playable_id++)
                    .music(music).playType(five_key).build();

            music.add_pattern(pattern_hard_type);
            five_patterns.add(pattern_hard_type);
        }

        Pageable page = PageRequest.of(0, 1);
        Page<Music> music_page = new PageImpl<>(musics, page, MUSIC_LENGTH);

        when(musicRepository.findAllByAlbum(page, album.getId())).thenReturn(music_page);
        when(patternRepository.findAllByPlayTypeAndMusics(musics, five_key.getId()))
                .thenReturn(five_patterns);

        /*
         * when
         */
        PagedModel<MusicResponseDto> five_key_result = musicService.findAllByAlbum(
                page, five_key.getId(), album.getId());

        /*
         * then
         */
        assertThat(five_key_result.getClass()).isEqualTo(PagedModel.class);
    }

    @Test
    public void save() {
        MusicRequestDto requestDto = new MusicRequestDto(null, null, null, null, 100, 200);
        Music music = Music.builder().title("music").album(Album.builder().title("hoho").id(1L).build()).build();

        /*
         * when
         */
        when(deassembler.toEntity(requestDto)).thenReturn(music);
        when(musicRepository.save(music)).thenReturn(music);
        MusicResponseDto result = musicService.save(requestDto);

        /*
         * then
         */
        assertThat(result.getClass()).isEqualTo(MusicResponseDto.class);
    }
}
