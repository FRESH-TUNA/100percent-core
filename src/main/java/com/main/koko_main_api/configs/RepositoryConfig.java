package com.main.koko_main_api.configs;

import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.album.AlbumRepositoryJPAImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class RepositoryConfig {
    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public AlbumRepository albumRepository() {
        return new AlbumRepositoryJPAImpl(em);
    }
}
