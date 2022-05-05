package com.main.koko_main_api.assemblers.album;

import com.main.koko_main_api.assemblers.AlbumAssembler;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.album.AlbumResponseDto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlbumAssemblerTest {
    private final AlbumAssembler assembler = new AlbumAssembler();

    @Test
    void toModel() {
        Album album = Album.builder().id(1L).title("title").build();
        album.add_music(Music.builder().id(1L).title("title2").build());
        album.add_music(Music.builder().id(2L).title("title3").build());

        /*
         * then
         */
        AlbumResponseDto dto = assembler.toModel(album);
        assertThat(dto.getMusics().get(0).getLink("self").get().getHref()).isEqualTo("/musics/1");
        assertThat(dto.getMusics().get(1).getLink("self").get().getHref()).isEqualTo("/musics/2");
        assertThat(dto.getLink("self").get().getHref()).isEqualTo("/albums/1");
    }
}