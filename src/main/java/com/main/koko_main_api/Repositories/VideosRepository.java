package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

// DB레이어와의 통신을 담당하는 접근계층
public interface VideosRepository extends JpaRepository<Videos, Long> {
}
