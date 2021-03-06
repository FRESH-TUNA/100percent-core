package com.main.koko_main_api.controllers;

import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatternControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private PatternRepository patternRepository;
    @Autowired
    private ComposerRepository composerRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private DifficultyTypeRepository difficultyTypeRepository;
    @Autowired
    private PlayTypesRepository playTypesRepository;

    private static String ROOT_ENDPOINT = "http://localhost:";
    private static String API_ENDPOINT = "/main_api/v1";
    private static HttpHeaders HEADERS = new HttpHeaders();

    @AfterEach
    public void clear_db() {
        patternRepository.deleteAll();
        difficultyTypeRepository.deleteAll();
        playTypesRepository.deleteAll();
        musicRepository.deleteAll();
        albumRepository.deleteAll();
        composerRepository.deleteAll();
    }

    @BeforeAll
    static void set_ROOT_ENDPOINT() {
        HEADERS.add("Content-type", "application/json");
    }

    @Test
    public void findById_test() throws Exception {
         Composer c = ?????????_??????();
         Album a = ??????_??????();

         Music music = ??????_??????(a, c);
         DifficultyType dt = ???????????????_??????();
         PlayType playType = ????????????_??????();

         Pattern p = ??????_??????(music, dt, playType);

         // when
         String url = ROOT_ENDPOINT + port + API_ENDPOINT + "/patterns/" + p.getId();
         assertThat(template.getForEntity(url, String.class).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void ????????????_?????????() throws JSONException {
        // given
        Composer c = ?????????_??????();
        Album a = ??????_??????();
        Music music = ??????_??????(a, c), music2 = ??????_??????(a, c);
        DifficultyType dt = ???????????????_??????(), dt2 = ???????????????_??????();
        PlayType playType = ????????????_??????(), playType2 = ????????????_??????();
        Pattern p = ??????_??????(music, dt, playType);

        String music_url = ROOT_ENDPOINT + port + API_ENDPOINT + "/musics/" + music2.getId();
        String dt_url = ROOT_ENDPOINT + port + API_ENDPOINT + "/difficulty_types/" + dt2.getId();
        String play_type_url = ROOT_ENDPOINT + port + API_ENDPOINT + "/play_types/" + playType2.getId();
        String pattern_url = ROOT_ENDPOINT + port + API_ENDPOINT + "/patterns/" + p.getId();

        // when
        JSONObject request = new JSONObject();
        request.put("music", music_url); request.put("difficultyType", dt_url);
        request.put("playType", play_type_url); request.put("level", 20);
        HttpEntity<String> updateRequestEntity = new HttpEntity(request.toString(), HEADERS);
        ResponseEntity<String> updateResponseEntity = template.exchange(
                pattern_url, HttpMethod.PUT, updateRequestEntity, String.class);

        // then
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void ??????_?????????() {
        Composer c = ?????????_??????();
        Album a = ??????_??????();

        Music music = ??????_??????(a, c);
        DifficultyType dt = ???????????????_??????();
        PlayType playType = ????????????_??????();

        Pattern p = ??????_??????(music, dt, playType);

        // when
        String url = ROOT_ENDPOINT + port + API_ENDPOINT + "/patterns/" + p.getId();
        assertThat(template.exchange(url, HttpMethod.DELETE, new HttpEntity(HEADERS), String.class)
                .getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /*
     * helpers
     */
    private Album ??????_??????() {
        return albumRepository.saveAndFlush(Album.builder().title("album").build());
    }

    private Composer ?????????_??????() {
        return composerRepository.saveAndFlush(Composer.builder().name("composer").build());
    }

    private Music ??????_??????(Album album, Composer composer) {
        List<Composer> composers = new ArrayList<>();
        composers.add(composer);
        Music music = Music.builder().title("music").album(album).composers(composers).build();
        return musicRepository.saveAndFlush(music);
    }

    private DifficultyType ???????????????_??????() {
        DifficultyType d = DifficultyType.builder().name("dt").build();
        return difficultyTypeRepository.saveAndFlush(d);
    }

    private PlayType ????????????_??????() {
        PlayType p = PlayType.builder().title("title").build();
        return playTypesRepository.saveAndFlush(p);
    }

    private Pattern ??????_??????(Music music, DifficultyType difficultyType, PlayType playType) {
        return patternRepository.saveAndFlush(
                Pattern.builder().difficultyType(difficultyType)
                        .playType(playType).music(music).level(10).build());
    }
}
