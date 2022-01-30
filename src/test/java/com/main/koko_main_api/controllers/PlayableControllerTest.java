package com.main.koko_main_api.controllers;

import com.main.koko_main_api.domains.Bpm;
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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//https://www.baeldung.com/spring-data-rest-relationships

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Transactional
public class PlayableControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    private static String MUSICS_ENDPOINT="/musics";
    private static String PLAYABLES_ENDPOINT="/playables";

    @Test
    public void save_and_findById_test() throws Exception {
        //endpoints and headers
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        /*
         * music 생성
         */
        JSONObject music_body = new JSONObject();
        music_body.put("title", "music example");
        String music_uri = new JSONObject(template.exchange(
                        ROOT_ENDPOINT + MUSICS_ENDPOINT, HttpMethod.POST,
                        new HttpEntity<>(music_body.toString(), headers), String.class)
                .getBody()).getJSONObject("_links")
                .getJSONObject("self").getString("href");
        /*
         * bpm body생성
         */
        JSONObject bpm_body_1 = new JSONObject(), bpm_body_2 = new JSONObject();
        bpm_body_1.put("value", 100); bpm_body_2.put("value", 101);
        JSONArray new_bpm_bodies = new JSONArray();
        new_bpm_bodies.put(bpm_body_1); new_bpm_bodies.put(bpm_body_2);

        /*
         * playable 생성
         */
        JSONObject playable_body = new JSONObject();
        playable_body.put("level", 1);
        playable_body.put("music", music_uri);
        playable_body.put("bpms", new_bpm_bodies);

        JSONObject new_playable = new JSONObject(template.exchange(
                ROOT_ENDPOINT + PLAYABLES_ENDPOINT, HttpMethod.POST,
                new HttpEntity<>(playable_body.toString(), headers),
                String.class).getBody());

        /*
         * 생성된 데이터 검증
         */
        //System.out.println(new_playable);
        JSONArray new_playable_bpms = new_playable.getJSONArray("bpms");
        assertThat(new_playable_bpms.getJSONObject(0).getInt("value")).isEqualTo(100);
        assertThat(new_playable_bpms.getJSONObject(1).getInt("value")).isEqualTo(101);


        /*
         * findbyId 검증
         */
        //System.out.println(new_playable);
        String new_playable_link = new_playable.getJSONObject("_links")
                .getJSONObject("self").getString("href");
        new_playable = new JSONObject(template.getForObject(new_playable_link, String.class));
        new_playable_bpms = new_playable.getJSONArray("bpms");
        assertThat(new_playable_bpms.getJSONObject(0).getInt("value")).isEqualTo(100);
        assertThat(new_playable_bpms.getJSONObject(1).getInt("value")).isEqualTo(101);
    }

    @Test
    public void findAll() throws Exception {
        //endpoints and headers
        String ROOT_ENDPOINT = "http://localhost:" + port + "/main_api/v1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        JSONObject playables = new JSONObject(
                template.getForObject(ROOT_ENDPOINT + "/playables", String.class));
        System.out.println(playables.toString());
    }
}


