package com.main.koko_main_api.configs;

import com.main.koko_main_api.repositories.album.AlbumCustomRepository;
import com.main.koko_main_api.repositories.album.AlbumCustomRepositoryJPQLImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public AlbumCustomRepository albumCustomRepository() {
        return new AlbumCustomRepositoryJPQLImpl();
    }
}
