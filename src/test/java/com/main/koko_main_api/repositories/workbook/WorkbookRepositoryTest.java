package com.main.koko_main_api.repositories.workbook;

import com.main.koko_main_api.configs.RepositoryConfig;

import com.main.koko_main_api.domains.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkbookRepositoryTest {
    @Autowired
    private WorkbookRepository workbookRepository;

    @Test
    void id로찾기_테스트() {
        Workbook workbook = workbookRepository.saveAndFlush(new Workbook());

        Workbook workbook2 = workbookRepository.findById(workbook.getId()).get();

        assertThat(workbook).isEqualTo(workbook2);
    }
}