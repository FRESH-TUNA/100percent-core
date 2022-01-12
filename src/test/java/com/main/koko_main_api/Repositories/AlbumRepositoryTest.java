package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Album;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest
public class AlbumRepositoryTest {
    @Autowired
    private AlbumsRepository vr;

    @Test
    public void test() {
        String title = "title";

        vr.save(Album.builder()
                .title(title)
                .build());
        List<Album> albumList = vr.findAll();

        Album album = albumList.get(0);
        assertThat(album.getTitle()).isEqualTo(title);
    }
}
