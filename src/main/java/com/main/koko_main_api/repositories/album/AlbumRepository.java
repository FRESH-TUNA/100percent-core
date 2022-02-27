package com.main.koko_main_api.repositories.album;

import com.main.koko_main_api.domains.Album;
import org.springframework.data.jpa.repository.JpaRepository;

// DB레이어와의 통신을 담당하는 접근계층
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
