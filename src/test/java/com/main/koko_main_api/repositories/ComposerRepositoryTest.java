package com.main.koko_main_api.repositories;

import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.Composer;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ComposerRepositoryTest {
    @Autowired
    private ComposerRepository repository;

    @Test
    void findByNameContaining() {
        repository.save(Composer.builder().name("title1").build());
        repository.save(Composer.builder().name("title2").build());
        List<Composer> res = repository.findByNameContaining("title");

        assertThat(res.size()).isEqualTo(2);
    }
}