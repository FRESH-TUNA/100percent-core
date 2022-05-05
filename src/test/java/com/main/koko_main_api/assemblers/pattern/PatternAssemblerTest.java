package com.main.koko_main_api.assemblers.pattern;


import com.main.koko_main_api.assemblers.PatternAssembler;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Pattern;

import com.main.koko_main_api.dtos.pattern.PatternResponseDto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


class PatternAssemblerTest {

    PatternAssembler assembler = new PatternAssembler();

    @Test
    public void 테스트() {
        Music music = Music.builder().id(1L).title("title").album(Album.builder().id(1L).build()).build();
        Pattern p = Pattern.builder().id(1L).music(music).build();

        /*
         * when
         */
        PatternResponseDto dto = assembler.toModel(p);

        /*
         * then
         */
        assertThat(dto.getLink("self").get().getHref()).isEqualTo("/patterns/1");
        assertThat(dto.getLink("music").get().getHref()).isEqualTo("/musics/1");
    }
}
