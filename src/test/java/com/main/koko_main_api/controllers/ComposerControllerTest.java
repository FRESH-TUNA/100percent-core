package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.album.AlbumRequestDto;
import com.main.koko_main_api.dtos.album.AlbumResponseDto;
import com.main.koko_main_api.repositories.ComposerRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComposerControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ComposerRepository composerRepository;

    /*
     * aftereach 를 통해 후처리를 할수 있다.
     * resttemplate을 이용한 테스트의 경우 @Transactional을
     * 사용해도 별도의 쓰레드에서 테스트가 실행되어 사용할수 없다.
     */
    @AfterEach
    void clear_db() {
        this.composerRepository.deleteAll();
    }

    @Test
    public void save_test() throws Exception {
        String url = "http://localhost:" + port + "/main_api/v1/composers";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject body = new JSONObject();
        body.put("name", "title");


        ResponseEntity<String> res = template.exchange(
                url, HttpMethod.POST,
                new HttpEntity<>(body.toString(), headers), String.class);

        //결과 확인
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void findById_test() throws Exception {
        String url = "http://localhost:" + port + "/main_api/v1/composers";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject body = new JSONObject();
        body.put("name", "title");


        /*
         * when
         */
        url  = new JSONObject(template.exchange(
                url, HttpMethod.POST, new HttpEntity<>(body.toString(), headers), String.class)
                .getBody()).getJSONObject("_links").getJSONObject("self").getString("href");
        ResponseEntity<String> res = template.getForEntity(url, String.class);

        /*
         * then
         */
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_test() throws Exception {
        String url = "http://localhost:" + port + "/main_api/v1/composers";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject body = new JSONObject();
        body.put("name", "title");


        /*
         * when
         */
        url  = new JSONObject(template.exchange(
                        url, HttpMethod.POST, new HttpEntity<>(body.toString(), headers), String.class)
                .getBody()).getJSONObject("_links").getJSONObject("self").getString("href");
        JSONObject newbody = new JSONObject();
        newbody.put("name", "title2");
        ResponseEntity<String> res = template.exchange(
                        url, HttpMethod.PUT, new HttpEntity<>(newbody.toString(), headers), String.class);

        /*
         * then
         */
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void delete_test() throws Exception {
        String url = "http://localhost:" + port + "/main_api/v1/composers";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject body = new JSONObject();
        body.put("name", "title");


        /*
         * when
         */
        url  = new JSONObject(template.exchange(
                        url, HttpMethod.POST, new HttpEntity<>(body.toString(), headers), String.class)
                .getBody()).getJSONObject("_links").getJSONObject("self").getString("href");
        ResponseEntity<String> res = template.exchange(
                url, HttpMethod.DELETE, new HttpEntity<>(new String(), headers), String.class);

        /*
         * then
         */
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void findAll_test() throws Exception {
        String url = "http://localhost:" + port + "/main_api/v1/composers";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject body = new JSONObject();
        body.put("name", "title");

        /*
         * then
         */
        ResponseEntity<String> res = template.getForEntity(url, String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

