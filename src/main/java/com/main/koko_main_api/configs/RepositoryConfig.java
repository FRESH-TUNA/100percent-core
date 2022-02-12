package com.main.koko_main_api.configs;

import com.main.koko_main_api.repositories.album.AlbumCustomRepository;
import com.main.koko_main_api.repositories.album.AlbumCustomRepositoryJPQLImpl;
import com.main.koko_main_api.repositories.playable.PlayableSearchRepository;
import com.main.koko_main_api.repositories.playable.PlayableSearchRepositoryImpl;
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
    public AlbumCustomRepository albumCustomRepository() {
        return new AlbumCustomRepositoryJPQLImpl();
    }
//    @Bean
//    public PlayableSearchRepository playableSearchRepository(EntityManager em) {
//        return new PlayableSearchRepositoryImpl(this.em);
//    }
}
