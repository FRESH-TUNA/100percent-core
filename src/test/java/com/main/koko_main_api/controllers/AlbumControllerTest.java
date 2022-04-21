package com.main.koko_main_api.controllers;

import com.main.koko_main_api.dtos.album.AlbumResponseDto;
import com.main.koko_main_api.dtos.album.AlbumRequestDto;
import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.repositories.album.AlbumCustomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/*
 * 그런데 delete 는 쿼리가 실행되지 않는다. @DataJpaTest 를
 * 따라가보면 @Transactional 이 붙어있고, 이는 테스트가 끝난 후 DB 를
 * 원래 상태로 되돌려 놓기 때문에, 굳이 불필요한 delete 를 실행하지 않는다.
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AlbumCustomRepository albumsRepository;

    /*
     * aftereach 를 통해 후처리를 할수 있다.
     * resttemplate을 이용한 테스트의 경우 @Transactional을
     * 사용해도 별도의 쓰레드에서 테스트가 실행되어 사용할수 없다.
     */
    @AfterEach
    public void clear_db() {
        this.albumsRepository.deleteAll();
    }

    @Test
    public void save_test() throws Exception {
        String title = "TECHNIKA";
        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder().title(title).build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";

        ResponseEntity<AlbumResponseDto> responseEntity = restTemplate.postForEntity(
                url, albumRequestDto, AlbumResponseDto.class);

        //결과 확인
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(title);
    }

    @Test
    public void findById_test() throws Exception {
        // save
        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder().title("thisistitle").build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";
        ResponseEntity<AlbumResponseDto> saveResponseEntity = restTemplate.postForEntity(
                url, albumRequestDto, AlbumResponseDto.class);

        //find test
        String find_url = "/main_api/v1/albums/{id}";
        ResponseEntity<AlbumResponseDto> findResponseEntity = restTemplate.getForEntity(
                find_url, AlbumResponseDto.class, saveResponseEntity.getBody().getId());
        assertThat(findResponseEntity.getBody().getId())
                .isEqualTo(saveResponseEntity.getBody().getId());
        assertThat(findResponseEntity.getBody().getTitle()).isEqualTo("thisistitle");
    }

    @Test
    public void update_test() throws Exception {
        // save
        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder().title("thisistitle").build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";
        ResponseEntity<AlbumResponseDto> saveResponseEntity = restTemplate.postForEntity(url, albumRequestDto, AlbumResponseDto.class);

        //update
        String update_url = "/main_api/v1/albums/{id}";
        AlbumsUpdateRequestDto updateDto = AlbumsUpdateRequestDto.builder()
                .title("changedTitle").build();
        HttpEntity<AlbumsUpdateRequestDto> updateRequestEntity = new HttpEntity<>(updateDto);
        ResponseEntity<AlbumResponseDto> updateResponseEntity = restTemplate.exchange(
                update_url, HttpMethod.PUT, updateRequestEntity, AlbumResponseDto.class, saveResponseEntity.getBody().getId());
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponseEntity.getBody().getTitle()).isEqualTo("changedTitle");
    }

    @Test
    public void delete_test() throws Exception {
        // save
        AlbumRequestDto albumRequestDto = AlbumRequestDto
                .builder().title("thisistitle").build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";
        ResponseEntity<AlbumResponseDto> saveResponseEntity = restTemplate.postForEntity(
                url, albumRequestDto, AlbumResponseDto.class);

        //delete (테스트의 경우 flush가 안되서 강제로 flush한다.
        String delete_url = "/main_api/v1/albums/{id}";
        restTemplate.delete(delete_url, saveResponseEntity.getBody().getId());
        albumsRepository.flush();

        //delete 검증
        List<Album> albums = albumsRepository.findAll();
        assertThat(albums.size()).isEqualTo(0);
    }

    @Test
    public void findAll_test() throws Exception {
        // save
        String url = "http://localhost:" + port + "/main_api/v1/albums";
        AlbumRequestDto albumRequestDto1 = AlbumRequestDto
                .builder().title("thisistitle1").build();
        AlbumRequestDto albumRequestDto2 = AlbumRequestDto
                .builder().title("thisistitle2").build();
        restTemplate.postForEntity(url, albumRequestDto1, AlbumResponseDto.class);
        restTemplate.postForEntity(url, albumRequestDto2, AlbumResponseDto.class);

        //find test
        ResponseEntity<List> findResponseEntity;
        findResponseEntity = restTemplate.getForEntity(url, List.class);
        assertThat(findResponseEntity.getBody().size()).isEqualTo(2);
    }
}
