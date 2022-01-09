package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Dtos.AlbumsResponseDto;
import com.main.koko_main_api.Dtos.AlbumsSaveRequestDto;
import com.main.koko_main_api.Dtos.ComposersSaveRequestDto;
import com.main.koko_main_api.Models.Composers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//https://www.baeldung.com/spring-data-rest-relationships

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComposesRepositoryTest {

    @Autowired
    private TestRestTemplate template;
    private static String ROOT_ENDPOINT = "http://localhost:8080/main_api/v1"
    private static String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers/";
    private static String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics/";
    private static String ALBUM_ENDPOINT = ROOT_ENDPOINT + "/albums";

    @Test
    public void composer_to_music_test() throws Exception {
        // composer 생성
        ComposersSaveRequestDto composer1 = ComposersSaveRequestDto
                .builder().name("composer1").build();
        // 더 좋은 방법을 찾아보자
        template.postForEntity(COMPOSER_ENDPOINT, composer1, Composers.class);

        //album 생성





        template
    }
}
