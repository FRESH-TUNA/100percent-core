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
    private BooleanExpression albumIdEq(Long a) {
        return a != null ? QMusic.music.album.id.eq(a) : null;
    }
    private BooleanExpression In(List<Music> musics) { return QMusic.music.in(musics); }

    /*
     * helpers
     */
    private JPAQuery<Music> findAll_base_query() {
        QMusic music = QMusic.music;
        QComposer composer = QComposer.composer;

        JPAQuery<Music> query = queryFactory
                .select(music)
                .from(music)
                .leftJoin(music.composers, composer).fetchJoin()
                .innerJoin(music.album).fetchJoin();

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
    public Page<Music> findAllByAlbum(Pageable pageable, Long album_id) {
        JPAQuery<Music> musics_query = findAll_base_query().where(albumIdEq(album_id));
        JPAQuery<Long> counts_query = counts_base_query().where(albumIdEq(album_id));

        // count query all
        Long music_counts = counts_query.fetchOne();
        List<Music> musics = getQuerydsl().applyPagination(pageable, musics_query).fetch();
        return new PageImpl<>(musics, pageable, music_counts);
    }

    @Override
    public List<Music> findAll(List<Music> musics) {
        return findAll_base_query().where(In(musics)).fetch();
    }

    @Override
    public Page<Music> findAll(Pageable pageable) {
        JPAQuery<Music> musics_query = findAll_base_query();
        JPAQuery<Long> counts_query = counts_base_query();

        // count query all
        Long music_counts = counts_query.fetchOne();
        List<Music> musics = getQuerydsl().applyPagination(pageable, musics_query).fetch();
        return new PageImpl<>(musics, pageable, music_counts);
    }
}
