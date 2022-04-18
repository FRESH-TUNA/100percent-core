package com.main.koko_main_api.assemblers.music;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.DifficultyType;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;

import com.main.koko_main_api.dtos.music.MusicResponseDto;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
class MusicAssemblerTest {
    @InjectMocks
    private MusicAssembler deassembler;

    @Mock
    private RepositoryEntityLinks linkHelper;

    @Test
    public void 테스트() {
        Music music = Music.builder().id(1L).album(Album.builder().id(1L).build()).build();
        DifficultyType dt = DifficultyType.builder().name("name").build();
        music.add_pattern(Pattern.builder().id(1L).difficultyType(dt).build());
        music.add_pattern(Pattern.builder().id(2L).difficultyType(dt).build());

        /*
         * when
         */
        when(linkHelper.linkToItemResource(MusicRepository.class, music.getId()))
                .thenReturn(Link.of("music_link"));
        when(linkHelper.linkToItemResource(AlbumRepository.class, music.getAlbum().getId()))
                .thenReturn(Link.of("album_link"));
        when(linkHelper.linkToItemResource(PatternRepository.class, 1L))
                .thenReturn(Link.of("pattern_link"));
        when(linkHelper.linkToItemResource(PatternRepository.class, 2L))
                .thenReturn(Link.of("pattern_link"));

        /*
         * then
         */
        MusicResponseDto dto = deassembler.toModel(music);
        assertThat(dto.getLink("self").get().getHref()).isEqualTo("music_link");
        assertThat(dto.getLink("album").get().getHref()).isEqualTo("album_link");

        assertThat(dto.getPatterns().get(0).getLink("self").get().getHref())
                .isEqualTo("pattern_link");
    }
}
