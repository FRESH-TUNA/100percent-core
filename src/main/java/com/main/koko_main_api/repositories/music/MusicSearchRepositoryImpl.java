package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.domains.*;

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
 * https://www.inflearn.com/questions/15876
 * https://stackoverflow.com/questions/60071473/querydsl-filter-on-left-join-with-subquery
 * https://data-make.tistory.com/671
 */

public class MusicSearchRepositoryImpl
        extends QuerydslRepositorySupport
        implements MusicSearchRepository<Music, Long> {

    private final JPAQueryFactory queryFactory;

    public MusicSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Music.class);
        this.queryFactory = queryFactory;
    }

    /*
     * filters
     */
    private BooleanExpression albumEq(Album a) {
        return a != null ? QMusic.music.album.eq(a) : null;
    }

    /*
     * helpers
     */
    private JPAQuery<Music> findAll_base_query() {
        QMusic music = QMusic.music;
        JPAQuery<Music> query = queryFactory
                .selectDistinct(music)
                .from(music)
                .leftJoin(music.album).fetchJoin();
        return query;
    }

    private JPAQuery<Long> counts_base_query() {
        QMusic music = QMusic.music;
        return queryFactory.select(music.count()).from(music);
    }

    /*
     * main methods
     */
    @Override
    public Page<Music> findAll(Pageable pageable, Album album) {
        JPAQuery<Music> musics_query = findAll_base_query().where(albumEq(album));
        JPAQuery<Long> counts_query = counts_base_query().where(albumEq(album));

        // count query all
        Long music_counts = counts_query.fetchOne();
        List<Music> musics = getQuerydsl().applyPagination(pageable, musics_query).fetch();
        return new PageImpl<>(musics, pageable, music_counts);
    }

    @Override
    public Optional<Music> findById(Long aLong) {
        return Optional.empty();
    }
}
