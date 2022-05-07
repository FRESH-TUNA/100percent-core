package com.main.koko_main_api.controllers.music;

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

public class MusicPatternControllerTest {
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
    public void 생성_테스트() throws JSONException {
        // given
        Composer c = 작곡가_생성();
        Album a = 앨범_생성();
        Music music = 음악_생성(a, c);
        DifficultyType dt = 난이도타입_생성();
        PlayType playType = 게임타입_생성();
        Pattern p = 패턴_생성(music, dt, playType);

        String dt_url = ROOT_ENDPOINT + port + API_ENDPOINT + "/difficulty_types/" + dt.getId();
        String play_type_url = ROOT_ENDPOINT + port + API_ENDPOINT + "/play_types/" + playType.getId();
        String pattern_url = ROOT_ENDPOINT + port + API_ENDPOINT + "/musics/" + music.getId() + "/" + "/patterns";

        // when
        JSONObject request = new JSONObject();
        request.put("difficultyType", dt_url);
        request.put("playType", play_type_url);
        request.put("level", 20);
        HttpEntity<String> body = new HttpEntity(request.toString(), HEADERS);
        ResponseEntity<String> updateResponseEntity = template.exchange(
                pattern_url, HttpMethod.POST, body, String.class);

        // then
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /*
     * helpers
     */
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
