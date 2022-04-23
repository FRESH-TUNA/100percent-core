package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.dtos.*;
import com.main.koko_main_api.dtos.album.AlbumsResponseDto;
import com.main.koko_main_api.dtos.album.AlbumRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;

//https://www.baeldung.com/spring-data-rest-relationships

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MusicControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void music_to_composer_test() throws Exception {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics";
        String ALBUM_ENDPOINT = ROOT_ENDPOINT + "/albums";
        String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers";

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

    @Test
    public void music_to_composer_once_test() throws Exception {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics";
        String ALBUM_ENDPOINT = ROOT_ENDPOINT + "/albums";
        String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers";

        //composer 생성
        ComposersSaveRequestDto composer1 = ComposersSaveRequestDto
                .builder().name("composer1").build();
        template.postForEntity(COMPOSER_ENDPOINT, composer1, ComposersResponseDto.class);
        ComposersSaveRequestDto composer2 = ComposersSaveRequestDto
                .builder().name("composer2").build();
        template.postForEntity(COMPOSER_ENDPOINT, composer2, ComposersResponseDto.class);
        System.out.println( template.getForObject(COMPOSER_ENDPOINT , String.class));
        /*
         * music 생성
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        // composers data 생성
        JSONArray composers = new JSONArray();
        composers.put(COMPOSER_ENDPOINT + "/1"); composers.put(COMPOSER_ENDPOINT + "/2");

        // request body 생성
        JSONObject request_body = new JSONObject();
        request_body.put("title", "music_title");
        request_body.put("composers", composers);

        //요청
        HttpEntity<String> entity = new HttpEntity<>(request_body.toString(), headers);
        System.out.println(template.exchange(MUSIC_ENDPOINT, HttpMethod.POST, entity, String.class));

        // 연관관계 생성 checking
        String jsonResponse = template.getForObject(MUSIC_ENDPOINT + "/1/composers", String.class);
        System.out.println(jsonResponse);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray("composers");
        assertThat(jsonArray.getJSONObject(0).getString("name")).isEqualTo("composer1");
        assertThat(jsonArray.getJSONObject(1).getString("name")).isEqualTo("composer2");
    }

    @Test
    public void music_to_album_test() throws Exception {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics";
        String ALBUM_ENDPOINT = ROOT_ENDPOINT + "/albums";
        String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers";

        /*
         * album 생성
         */
        String title = "TECHNIKA";
        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder().title(title).build();
        ResponseEntity<AlbumsResponseDto> responseEntity = restTemplate.postForEntity(
                ALBUM_ENDPOINT, albumRequestDto, AlbumsResponseDto.class);

        /*
         * music 생성
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        // request body 생성
        JSONObject request_body = new JSONObject();
        request_body.put("title", "music_title");
        request_body.put("album", ALBUM_ENDPOINT + "/1");

        //요청
        HttpEntity<String> entity = new HttpEntity<>(request_body.toString(), headers);
        System.out.println(template.exchange(MUSIC_ENDPOINT, HttpMethod.POST, entity, String.class));

        //검증
        // 연관관계 생성 checking
        String jsonResponse = template.getForObject(MUSIC_ENDPOINT + "/1/album", String.class);
        JSONObject jsonObj = new JSONObject(jsonResponse);
        assertThat(jsonObj.getString("title")).isEqualTo("TECHNIKA");
    }


}
