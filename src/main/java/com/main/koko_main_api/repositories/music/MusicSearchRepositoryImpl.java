package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.controllers.Playables.PlayableParams;
import com.main.koko_main_api.domains.Music;

import com.main.koko_main_api.domains.QMusic;

import com.main.koko_main_api.repositories.playable.PlayableSearchRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

/*
 * 참고자료
 * https://velog.io/@max9106/JPA-QueryDSL
 * https://joanne.tistory.com/270
 * https://jojoldu.tistory.com/372
 * https://jessyt.tistory.com/55
 */

//@Repository
public class MusicSearchRepositoryImpl
        extends QuerydslRepositorySupport
        implements PlayableSearchRepository<Music, Long> {

    private final JPAQueryFactory queryFactory;

    public MusicSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Music.class);
        this.queryFactory = queryFactory;
    }

    /*
     * helper methods
     */
    private JPAQuery<Music> findAll_base_query() {
        QMusic music = QMusic.music;
        return queryFactory
                .selectDistinct(music).from(music)
                .leftJoin(music.album).fetchJoin()
                .leftJoin(music.playables).fetchJoin();
    }

    /*
     * filters
     */
//    private BooleanExpression playTypeEq(Long id) {
//        return id != null ? QPlayable.playable.playType.id.eq(id) : null;
//    }
//    private BooleanExpression difficultyTypeEq(Long id) {
//        return id != null ? QPlayable.playable.difficultyType.id.eq(id) : null;
//    }

    @Override
    public List<Music> findAll() {
        return null;
    }

    @Override
    public List<Music> findAll(PlayableParams params) {
        return null;
    }

    @Override
    public Page<Music> findAll(Pageable pageable, PlayableParams params) {
        return null;
    }

    @Override
    public Optional<Music> findById(Long aLong) {
        return Optional.empty();
    }
}
