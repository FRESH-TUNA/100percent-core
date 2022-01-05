package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Videos;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VideosRepositoryTest {
    @Autowired
    private VideosRepository vr;

    @Test
    public void test() {
        String title = "title";
        String address = "address";

        vr.save(Videos.builder()
                .title(title)
                .address(address)
                .build());
        List<Videos> videosList = vr.findAll();

        Videos video = videosList.get(0);

        assertThat(video.getAddress()).isEqualTo(address);
        assertThat(video.getTitle()).isEqualTo(title);
    }
}
