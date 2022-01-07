package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Albums;
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
public class AlbumsRepositoryTest {
    @Autowired
    private AlbumsRepository vr;

    @Test
    public void test() {
        String title = "title";

        vr.save(Albums.builder()
                .title(title)
                .build());
        List<Albums> albumsList = vr.findAll();

        Albums album = albumsList.get(0);
        assertThat(album.getTitle()).isEqualTo(title);
    }
}
