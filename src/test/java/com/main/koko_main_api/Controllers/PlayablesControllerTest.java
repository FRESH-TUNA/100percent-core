package com.main.koko_main_api.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

//https://www.baeldung.com/spring-data-rest-relationships

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayablesControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    private static String MUSICS_ENDPOINT="/musics";
    private static String PLAYABLES_ENDPOINT="/playables";

    @Test
    public void playable_to_music() throws Exception {
        //endpoints and headers
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        /*
         * music 생성
         */
        JSONObject music_body = new JSONObject();
        music_body.put("title", "music_title");
        HttpEntity<String> entity = new HttpEntity<>(music_body.toString(), headers);
        System.out.println(template.exchange(ROOT_ENDPOINT + MUSICS_ENDPOINT, HttpMethod.POST, entity, String.class));

        /*
         * playable 생성
         */
        JSONObject playable_body = new JSONObject();
        playable_body.put("level", 1);
        playable_body.put("music", ROOT_ENDPOINT + MUSICS_ENDPOINT + "/1");
        System.out.println(template.exchange(ROOT_ENDPOINT + PLAYABLES_ENDPOINT, HttpMethod.POST,
                new HttpEntity<>(playable_body.toString(), headers), String.class));

        /*
         * 검증
         */
        JSONObject jsonObj = new JSONObject(template.getForObject(
                ROOT_ENDPOINT + PLAYABLES_ENDPOINT + "/1/music", String.class));
        assertThat(jsonObj.getString("title")).isEqualTo("music_title");
    }

    @Test
    public void playable_to_bpm_test() throws Exception {
        //endpoints and headers
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        /*
         * playable 생성
         */
        JSONObject playable_body = new JSONObject();
        playable_body.put("level", 1);
        System.out.println(template.exchange(ROOT_ENDPOINT + PLAYABLES_ENDPOINT, HttpMethod.POST,
                new HttpEntity<>(playable_body.toString(), headers), String.class));

        /*
         * bpm 생성
         */
        JSONObject bpm_body = new JSONObject();
        bpm_body.put("value", 100);

        JSONArray new_bpm_bodies = new JSONArray();
        new_bpm_bodies.put(bpm_body);
        new_bpm_bodies.put(bpm_body);

        playable_body = new JSONObject();
        playable_body.put("bpms", new_bpm_bodies);
        System.out.println(template.exchange(ROOT_ENDPOINT + PLAYABLES_ENDPOINT + "/1/bpms",
                HttpMethod.POST, new HttpEntity<>(bpm_body.toString(), headers), String.class));
        System.out.println(template.optionsForAllow(ROOT_ENDPOINT + PLAYABLES_ENDPOINT + "/1/bpms"));
    }
}


