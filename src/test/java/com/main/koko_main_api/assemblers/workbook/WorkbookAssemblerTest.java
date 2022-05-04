package com.main.koko_main_api.assemblers.workbook;
import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.workbook.WorkbookResponseDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkbookAssemblerTest {
    private final WorkbookAssembler assembler = new WorkbookAssembler();

    @Test
    void test() {
        // datas
        Music music = Music.builder().title("title").build();
        PlayType playType = PlayType.builder().title("playtype").build();
        DifficultyType difficultyType = DifficultyType.builder().name("difficultyType").build();
        Workbook w = Workbook.builder().id(1L).description("aaa").title("bbb").playType(playType).build();

        List<Pattern> patterns = new ArrayList();
        patterns.add(Pattern.builder().id(1L).music(music).playType(playType).difficultyType(difficultyType).build());
        patterns.add(Pattern.builder().id(2L).music(music).playType(playType).difficultyType(difficultyType).build());

        // when
        WorkbookResponseDto res = assembler.toModel(w, patterns);

        // then
        assertThat(res.getLink("self").get().getHref()).isEqualTo("/workbooks/1");
        assertThat(res.getPatterns().get(0).getLink("self").get().getHref())
                .isEqualTo("/patterns/1");
        assertThat(res.getPatterns().get(1).getLink("self").get().getHref())
                .isEqualTo("/patterns/2");
    }
}
