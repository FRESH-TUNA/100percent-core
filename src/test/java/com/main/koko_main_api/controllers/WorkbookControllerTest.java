package com.main.koko_main_api.controllers;

import com.main.koko_main_api.domains.*;
import com.main.koko_main_api.dtos.pattern.PatternRequestDto;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.DifficultyTypeRepository;
import com.main.koko_main_api.repositories.PlayTypesRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.pattern.PatternRepository;
import com.main.koko_main_api.repositories.workbook.WorkbookRepository;
import org.json.JSONArray;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkbookControllerTest {

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
    @Autowired
    private WorkbookRepository workbookRepository;

    private static String ROOT_ENDPOINT = "http://localhost:";
    private static String API_ENDPOINT = "/main_api/v1";
    private static HttpHeaders HEADERS = new HttpHeaders();

    /*
     * aftereach 를 통해 후처리를 할수 있다.
     * resttemplate을 이용한 테스트의 경우 @Transactional을
     * 사용해도 별도의 쓰레드에서 테스트가 실행되어 사용할수 없다.
     */
    @AfterEach
    public void clear_db() {
        workbookRepository.deleteAll();
        patternRepository.deleteAll();
        difficultyTypeRepository.deleteAll();
        playTypesRepository.deleteAll();
        musicRepository.deleteAll();
        albumRepository.deleteAll();
        composerRepository.deleteAll();
    }

    @BeforeAll
    static void set_header() {
        HEADERS.add("Content-type", "application/json");
    }

    @Test
    void findAll() {
        PlayType playType = 게임타입_생성();
        기본_워크북_생성(playType); 기본_워크북_생성(playType);

        // when
        String url = ROOT_ENDPOINT + port + API_ENDPOINT + "/workbooks";
        assertThat(template.getForEntity(url, String.class).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void save() throws JSONException {
        Music m = 음악_생성(앨범_생성(), 작곡가_생성());
        DifficultyType dt = 난이도타입_생성();
        PlayType pt = 게임타입_생성();
        Pattern p1 = 패턴_생성(m, dt, pt), p2 = 패턴_생성(m, dt, pt);
        String url = ROOT_ENDPOINT + port + API_ENDPOINT + "/workbooks";

        // when
        JSONObject request = new JSONObject();
        JSONArray patterns_body = new JSONArray();
        patterns_body.put(ROOT_ENDPOINT + port + API_ENDPOINT + "/patterns/" + p1.getId());
        patterns_body.put(ROOT_ENDPOINT + port + API_ENDPOINT + "/patterns/" + p2.getId());
        request.put("title", "title"); request.put("description", "description");
        request.put("patterns", patterns_body);
        HttpEntity<String> entity = new HttpEntity(request.toString(), HEADERS);

        // then
        ResponseEntity<String> res = template.exchange(
                url, HttpMethod.POST, entity, String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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
    private Workbook 기본_워크북_생성(PlayType playType) {
        return workbookRepository.saveAndFlush(Workbook.builder().title("title")
                .description("des").playType(playType).build());
    }

    private Album 앨범_생성() {
        return albumRepository.saveAndFlush(Album.builder().title("album").build());
    }

    private Composer 작곡가_생성() {
        return composerRepository.saveAndFlush(Composer.builder().name("composer").build());
    }

    private Music 음악_생성(Album album, Composer composer) {
        List<Composer> composers = new ArrayList<>();
        composers.add(composer);
        Music music = Music.builder().title("music").album(album).composers(composers).build();
        return musicRepository.saveAndFlush(music);
    }

    private DifficultyType 난이도타입_생성() {
        DifficultyType d = DifficultyType.builder().name("dt").build();
        return difficultyTypeRepository.saveAndFlush(d);
    }

    private PlayType 게임타입_생성() {
        PlayType p = PlayType.builder().title("title").build();
        return playTypesRepository.saveAndFlush(p);
    }

    private Pattern 패턴_생성(Music music, DifficultyType difficultyType, PlayType playType) {
        return patternRepository.saveAndFlush(
                Pattern.builder().difficultyType(difficultyType)
                        .playType(playType).music(music).level(10).build());
    }
}