package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Dtos.*;
import com.main.koko_main_api.Models.Composers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//https://www.baeldung.com/spring-data-rest-relationships

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComposesRepositoryTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    @Transactional
    public void composer_to_music_test() throws Exception {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1/musics";
        String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers";
        String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics";
        System.out.println(template.getForObject(ROOT_ENDPOINT, String.class));

        // composer 생성
//        ComposersSaveRequestDto composer1 = ComposersSaveRequestDto
//                .builder().name("composer1").build();
//        ComposersSaveRequestDto composer2 = ComposersSaveRequestDto
//                .builder().name("composer2").build();
//        template.postForEntity(COMPOSER_ENDPOINT, composer1, ComposersResponseDto.class);
//        template.postForEntity(COMPOSER_ENDPOINT, composer2, ComposersResponseDto.class);
//
//        // music 생성
//        MusicsSaveRequestDto music1 = MusicsSaveRequestDto
//                .builder().title("music1").build();
//        template.postForEntity(MUSIC_ENDPOINT, music1, MusicsResponseDto.class);
//
//        // 연관관계 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "text/uri-list");
//        HttpEntity<String> entity = new HttpEntity<>(
//                COMPOSER_ENDPOINT + "/1\n" + COMPOSER_ENDPOINT + "/2\n", headers);
//        template.exchange(MUSIC_ENDPOINT + "/1/composers", HttpMethod.PUT, entity, String.class);
//
//
//        // 연관관계 생성 checking
//        String jsonResponse = template.getForObject(ROOT_ENDPOINT, String.class);
//        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
//        JSONArray jsonArray = jsonObj.getJSONArray("musics");
//        assertThat(jsonArray.getJSONObject(0).getString("title")).isEqualTo("music1");
    }
}
