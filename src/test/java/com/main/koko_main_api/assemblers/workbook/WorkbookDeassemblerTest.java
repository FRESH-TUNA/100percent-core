package com.main.koko_main_api.assemblers.workbook;

import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.workbook.WorkbookRequestDto;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
public class WorkbookDeassemblerTest {

    @Mock
    private PatternRepository patternRepository;

    @InjectMocks
    private WorkbookDeassembler deassembler;

    @Test
    void 테스트() {
        /*
         * given datas
         */
        List<URI> urls = new ArrayList<>();
        urls.add(URI.create("/patterns/1")); urls.add(URI.create("/patterns/2"));
        WorkbookRequestDto dto = new WorkbookRequestDto("title", "description", urls);

        Pattern p1 = Pattern.builder().id(1L).build();
        Pattern p2 = Pattern.builder().id(2L).build();

        /*
         * when
         */
        when(patternRepository.getById(1L)).thenReturn(p1);
        when(patternRepository.getById(2L)).thenReturn(p2);


        /*
         then
         */
        Workbook workbook = deassembler.toEntity(dto);
        assertThat(workbook.getPatterns().size()).isEqualTo(2);
        assertThat(workbook.getTitle()).isEqualTo("title");
        assertThat(workbook.getDescription()).isEqualTo("description");
    }
}
