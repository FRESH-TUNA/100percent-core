package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Composer;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.music.bpm.MusicBpmsRequestDto;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
class MusicRequestDtoDeassemblerTest {
    @InjectMocks
    private MusicRequestDtoDeassembler deassembler;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ComposerRepository composerRepository;

    @Test
    public void 엔티티변환테스트() {
        /*
         * given datas
         */
        List<Composer> composers = getComposers();
        Album album = getAlbum();
        MusicRequestDto dto = getMusicRequestDto(
                getAlbumURI(album), getComposerURLs(composers), getMusicBpmsRequestDto());

        /*
         * when
         */
        when(albumRepository.getById(any())).thenReturn(album);
        when(composerRepository.getById(1L)).thenReturn(composers.get(0));
        when(composerRepository.getById(2L)).thenReturn(composers.get(1));


        /*
         then
         */
        Music music = deassembler.toEntity(dto);
        assertThat(music.getAlbum()).isEqualTo(album);
        assertThat(music.getComposers()).isEqualTo(composers);
        assertThat(music.getTitle()).isEqualTo("songsong");
    }

    /*
     * helper
     */
    private Album getAlbum() {
        Album album = Album.builder().title("album_title").id(1L).build();
        return album;
    }

    private List<MusicBpmsRequestDto> getMusicBpmsRequestDto() {
        List<MusicBpmsRequestDto> dtos = new ArrayList<>();
        dtos.add(new MusicBpmsRequestDto(100));
        dtos.add(new MusicBpmsRequestDto(200));
        return dtos;
    }

    private URI getAlbumURI(Album album) {
        return URI.create("https://localhost:8000/main-api/albums/" + album.getId());
    }

    private MusicRequestDto getMusicRequestDto(
            URI album_uri, List<URI> composers, List<MusicBpmsRequestDto> bpms) {
        return new MusicRequestDto("songsong", album_uri, composers, bpms);
    }

    private List<Composer> getComposers () {
        List<Composer> composers = new ArrayList<>();
        composers.add(Composer.builder().name("kiriri").id(1L).build());
        composers.add(Composer.builder().name("kiriri").id(2L).build());
        return composers;
    }

    private List<URI> getComposerURLs(List<Composer> composers) {
        return composers.stream().map(
                x -> URI.create("https://localhost:8000/main-api/albums/" + x.getId()))
                .collect(Collectors.toList());
    }
}