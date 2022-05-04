package com.main.koko_main_api.assemblers.workbook;
import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.workbook.WorkbooksResponseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkbooksAssemblerTest {
    private final WorkbooksAssembler assembler = new WorkbooksAssembler();

    @Test
    void test() {
        // datas
        PlayType playType = PlayType.builder().title("playtype").build();
        Workbook w = Workbook.builder().id(1L).description("aaa").title("bbb").playType(playType).build();

        // when
        WorkbooksResponseDto res = assembler.toModel(w);

        // then
        assertThat(res.getLink("self").get().getHref()).isEqualTo("/workbooks/1");
    }
}
