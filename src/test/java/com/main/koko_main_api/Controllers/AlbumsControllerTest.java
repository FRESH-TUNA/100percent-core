package com.main.koko_main_api.Controllers;

import com.main.koko_main_api.Dtos.AlbumsResponseDto;
import com.main.koko_main_api.Dtos.AlbumsSaveRequestDto;
import com.main.koko_main_api.Dtos.AlbumsUpdateRequestDto;
import com.main.koko_main_api.Models.Albums;
import com.main.koko_main_api.Repositories.AlbumsRepository;
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

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumsControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AlbumsRepository albumsRepository;

    @Test
    public void save_test() throws Exception {
        String title = "TECHNIKA";
        AlbumsSaveRequestDto albumsSaveRequestDto = AlbumsSaveRequestDto
                .builder().title(title).build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";

        ResponseEntity<AlbumsResponseDto> responseEntity = restTemplate.postForEntity(url, albumsSaveRequestDto, AlbumsResponseDto.class);

        //결과 확인
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(title);
    }

    @Test
    public void findById_test() throws Exception {
        // save
        AlbumsSaveRequestDto albumsSaveRequestDto = AlbumsSaveRequestDto
                .builder().title("thisistitle").build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";

        ResponseEntity<Long> saveResponseEntity = restTemplate.postForEntity(url, albumsSaveRequestDto, Long.class);

        //find test
        String find_url = "/main_api/v1/albums/{id}";
        ResponseEntity<AlbumsResponseDto> findResponseEntity = restTemplate.getForEntity(find_url, AlbumsResponseDto.class, saveResponseEntity.getBody());
        assertThat(findResponseEntity.getBody().getId()).isEqualTo(saveResponseEntity.getBody());
        assertThat(findResponseEntity.getBody().getTitle()).isEqualTo("thisistitle");
    }

    @Test
    @Transactional
    public void update_test() throws Exception {
        // save
        AlbumsSaveRequestDto albumsSaveRequestDto = AlbumsSaveRequestDto
                .builder().title("thisistitle").build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";
        ResponseEntity<AlbumsResponseDto> saveResponseEntity = restTemplate.postForEntity(url, albumsSaveRequestDto, AlbumsResponseDto.class);

        //update
        String update_url = "/main_api/v1/albums/{id}";
        AlbumsUpdateRequestDto updateDto = AlbumsUpdateRequestDto.builder()
                .title("changedTitle").build();
        HttpEntity<AlbumsUpdateRequestDto> updateRequestEntity = new HttpEntity<>(updateDto);
        ResponseEntity<AlbumsResponseDto> updateResponseEntity = restTemplate.exchange(
                update_url, HttpMethod.PUT, updateRequestEntity, AlbumsResponseDto.class, saveResponseEntity.getBody().getId());
        assertThat(updateResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponseEntity.getBody().getTitle()).isEqualTo("changedTitle");
    }

    @Test
    @Transactional
    public void delete_test() throws Exception {
        // save
        AlbumsSaveRequestDto albumsSaveRequestDto = AlbumsSaveRequestDto
                .builder().title("thisistitle").build();
        String url = "http://localhost:" + port + "/main_api/v1/albums";
        ResponseEntity<Long> saveResponseEntity = restTemplate.postForEntity(url, albumsSaveRequestDto, Long.class);

        //delete
        String delete_url = "/main_api/v1/albums/{id}";
        restTemplate.delete(delete_url, saveResponseEntity.getBody());

        //delete 검증
        List<Albums> albums = albumsRepository.findAll();
        assertThat(albums.size()).isEqualTo(0);
    }

    @Test
    public void findAll_test() throws Exception {
        // save
        String url = "http://localhost:" + port + "/main_api/v1/albums";
        AlbumsSaveRequestDto albumsSaveRequestDto1 = AlbumsSaveRequestDto
                .builder().title("thisistitle1").build();
        AlbumsSaveRequestDto albumsSaveRequestDto2 = AlbumsSaveRequestDto
                .builder().title("thisistitle2").build();
        restTemplate.postForEntity(url, albumsSaveRequestDto1, Long.class);
        restTemplate.postForEntity(url, albumsSaveRequestDto2, Long.class);

        //find test
        ResponseEntity<List> findResponseEntity;
        findResponseEntity = restTemplate.getForEntity(url, List.class);
//        AlbumsResponseDto d1 = (AlbumsResponseDto) findResponseEntity.getBody().get(0);
//        AlbumsResponseDto d2 = (AlbumsResponseDto) findResponseEntity.getBody().get(1);
        assertThat(findResponseEntity.getBody().size()).isEqualTo(2);
//        assertThat(d1.getTitle()).isEqualTo("thisistitle1");
//        assertThat(d2.getTitle()).isEqualTo("thisistitle2");
    }
}
