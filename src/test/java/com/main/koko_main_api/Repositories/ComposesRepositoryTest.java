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
    public void composer_to_music_test() throws Exception {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port;
        String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers";
        String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics";

         //composer 생성
        ComposersSaveRequestDto composer1 = ComposersSaveRequestDto
                .builder().name("composer1").build();
        template.postForEntity(COMPOSER_ENDPOINT, composer1, ComposersResponseDto.class);

        // music 생성
        MusicsSaveRequestDto music1 = MusicsSaveRequestDto
                .builder().title("music1").build();
        MusicsSaveRequestDto music2 = MusicsSaveRequestDto
                .builder().title("music2").build();
        template.postForEntity(MUSIC_ENDPOINT, music1, MusicsResponseDto.class);
        template.postForEntity(MUSIC_ENDPOINT, music2, MusicsResponseDto.class);

        // 연관관계 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "text/uri-list");
        HttpEntity<String> entity = new HttpEntity<>(
                MUSIC_ENDPOINT + "/1\n" + MUSIC_ENDPOINT + "/2", headers);
        template.exchange(COMPOSER_ENDPOINT + "/1/musics", HttpMethod.PUT, entity, String.class);

        // 연관관계 생성 checking
        String jsonResponse = template.getForObject(COMPOSER_ENDPOINT + "/1/musics", String.class);
        System.out.println(jsonResponse);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray("musics");
        assertThat(jsonArray.getJSONObject(0).getString("title")).isEqualTo("music1");
    }

    @Test
    public void music_to_composer_test() throws Exception {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port;
        String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers";
        String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics";

        //composer 생성
        ComposersSaveRequestDto composer1 = ComposersSaveRequestDto
                .builder().name("composer1").build();
        template.postForEntity(COMPOSER_ENDPOINT, composer1, ComposersResponseDto.class);
        ComposersSaveRequestDto composer2 = ComposersSaveRequestDto
                .builder().name("composer2").build();
        template.postForEntity(COMPOSER_ENDPOINT, composer2, ComposersResponseDto.class);

        // music 생성
        MusicsSaveRequestDto music1 = MusicsSaveRequestDto
                .builder().title("music1").build();
        MusicsSaveRequestDto music2 = MusicsSaveRequestDto
                .builder().title("music2").build();
        template.postForEntity(MUSIC_ENDPOINT, music1, MusicsResponseDto.class);
        template.postForEntity(MUSIC_ENDPOINT, music2, MusicsResponseDto.class);

        // 연관관계 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "text/uri-list");
        HttpEntity<String> entity = new HttpEntity<>(
                COMPOSER_ENDPOINT + "/1\n" + COMPOSER_ENDPOINT + "/2", headers);
        template.exchange(MUSIC_ENDPOINT + "/1/composers", HttpMethod.PUT, entity, String.class);

        // 연관관계 생성 checking
        String jsonResponse = template.getForObject(MUSIC_ENDPOINT + "/1/composers", String.class);
        System.out.println(jsonResponse);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray("composers");
        assertThat(jsonArray.getJSONObject(0).getString("name")).isEqualTo("composer1");
    }
}
