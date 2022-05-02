package com.main.koko_main_api.assemblers.workbook;

import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
public class WorkbookDeassemblerTest {
    private WorkbookDeassembler deassembler = new WorkbookDeassembler();

    @Test
    void 테스트() {
        /*
         * given datas
         */
        List<URI> urls = new ArrayList<>();
        urls.add(URI.create("/patterns/1")); urls.add(URI.create("/patterns/2"));
        WorkbookRequestDto dto = new WorkbookRequestDto("title", "description", urls);

        PlayType playType = PlayType.builder().title("title").build();
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.builder().id(1L).playType(playType).build());
        patterns.add(Pattern.builder().id(2L).playType(playType).build());


        /*
         then
         */
        Workbook workbook = deassembler.toEntity(dto, patterns);
        assertThat(workbook.getPlayType()).isEqualTo(playType);
        assertThat(workbook.getPatterns().size()).isEqualTo(2);
        assertThat(workbook.getTitle()).isEqualTo("title");
        assertThat(workbook.getDescription()).isEqualTo("description");
    }
}
