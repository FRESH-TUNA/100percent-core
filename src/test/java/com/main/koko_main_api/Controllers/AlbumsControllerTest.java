package com.main.koko_main_api.Controllers;

import com.main.koko_main_api.Dtos.AlbumsSaveRequestDto;
import com.main.koko_main_api.Models.Albums;
import com.main.koko_main_api.Repositories.AlbumsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumsControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AlbumsRepository albumsRepository;

    @Test
    public void save_test() throws Exception {
        String title = "TECHNIKA";
        AlbumsSaveRequestDto albumsSaveRequestDto = AlbumsSaveRequestDto
                .builder().title(title).build();
        String url = "http://localhost:" + port + "/api/v1/albums";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, albumsSaveRequestDto, Long.class);

        //결과 확인
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Albums> all = albumsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
    }
}
