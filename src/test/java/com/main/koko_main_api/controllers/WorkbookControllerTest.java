package com.main.koko_main_api.controllers;

import com.main.koko_main_api.repositories.album.AlbumRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkbookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private AlbumRepository albumsRepository;

    /*
     * aftereach 를 통해 후처리를 할수 있다.
     * resttemplate을 이용한 테스트의 경우 @Transactional을
     * 사용해도 별도의 쓰레드에서 테스트가 실행되어 사용할수 없다.
     */
    @AfterEach
    void clear_db() {
        this.albumsRepository.deleteAll();
    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }


    /*
     * helpers
     */
//    private String 음악_생성(String) throws JSONException {
//        //endpoints
//        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
//        String MUSIC_ENDPOINT = ROOT_ENDPOINT + "/musics";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/json");
//
//        //composer 생성
//        String composer1 = 작곡가_생성("composer1");
//        String composer2 = 작곡가_생성("composer2");
//
//        // album 생성
//        String album = 앨범_생성("album");
//
//
//        // music 생성
//        JSONObject request = new JSONObject();
//        request.put("title", "music");
//        request.put("album", album);
//        request.put("min_bpm", 100); request.put("max_bpm", 200);
//
//        JSONArray composer_data = new JSONArray();
//        composer_data.put(composer1); composer_data.put(composer2);
//        request.put("composers", composer_data);
//
//        HttpStatus result = template.exchange(MUSIC_ENDPOINT, HttpMethod.POST,
//                new HttpEntity<>(request.toString(), headers), String.class).getStatusCode();
//        return
//    }

    private String 작곡가_생성(String name) throws JSONException {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        String COMPOSER_ENDPOINT = ROOT_ENDPOINT + "/composers";

        JSONObject request = new JSONObject();
        request.put("name", name);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject result = new JSONObject(template.exchange(COMPOSER_ENDPOINT, HttpMethod.POST,
                new HttpEntity<>(request.toString(), headers), String.class).getBody());

        return result.getJSONObject("_links").getJSONObject("self").getString("href");
    }

    private String 앨범_생성(String title) throws JSONException {
        //endpoints
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        String ALBUM_ENDPOINT = ROOT_ENDPOINT + "/albums";

        JSONObject request = new JSONObject();
        request.put("title", title);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject result = new JSONObject(template.exchange(ALBUM_ENDPOINT, HttpMethod.POST,
                new HttpEntity<>(request.toString(), headers), String.class).getBody());

        return result.getJSONObject("_links").getJSONObject("self").getString("href");
    }
}