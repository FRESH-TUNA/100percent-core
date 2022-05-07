package com.main.koko_main_api.repositories;
import com.main.koko_main_api.configs.RepositoryConfig;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

// test O
@DataJpaTest
@Import({RepositoryConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class AlbumRepositoryTest {
    @Autowired
    private AlbumRepository repository;

    @Test
    void 전체앨범가져오기테스트() {
        repository.saveAndFlush(Album.builder().title("title1").build());
        repository.saveAndFlush(Album.builder().title("title1").build());
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    void 레퍼런스같고오기테스트() {
        Album album1 = repository.getById(1L);
        Album album2 = repository.getById(1L);
        assertThat(album1).isEqualTo(album2);
    }

    @Test
    void 저장후_삭제_테스트() {
        Album a = repository.saveAndFlush(Album.builder().title("title1").build());
        repository.deleteById(a.getId());

        assertThat(repository.findById(a.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    void 전체_삭제_테스트() {
        repository.saveAndFlush(Album.builder().title("title1").build());
        repository.saveAndFlush(Album.builder().title("title1").build());
        repository.deleteAll();
        assertThat(repository.findAll().isEmpty()).isEqualTo(true);
    }

    @Test
    void 저장후_검색_테스트() {
        Album album = Album.builder().title("title1").build();
        repository.saveAndFlush(album);
        assertThat(repository.findById(album.getId()).get()).isEqualTo(album);
    }
}
