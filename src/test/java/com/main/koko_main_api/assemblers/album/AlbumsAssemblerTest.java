package com.main.koko_main_api.assemblers.album;

import com.main.koko_main_api.assemblers.AlbumsAssembler;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.dtos.album.AlbumsResponseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlbumsAssemblerTest {
    private final AlbumsAssembler assembler = new AlbumsAssembler();

    @Test
    void toModel() {
        Album album = Album.builder().id(1L).title("title").build();

        /*
         * then
         */
        AlbumsResponseDto dto = assembler.toModel(album);
        System.out.println(dto.getLink("self").get().getHref());
        assertThat(dto.getLink("self").get().getHref()).isEqualTo("/albums/1");
    }
}