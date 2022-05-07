package com.main.koko_main_api.repositories.album;

import com.main.koko_main_api.domains.Album;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
/*
 * @Transactional
 * 메소드 시작시 트랜잭션 시작, 메소드 종료시 트랜잭션 커밋
 * 문제가 발생하면 롤백
 */
@Transactional
public class AlbumCustomRepositoryJPQLImpl implements AlbumCustomRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Album> findAll() {
        /*
         * Album은 클래스명이 아니라 엔티티 명이다.
         * 엔티티명은 @Entity(name="XXX") 에 정의 된것을 찾고
         * 정의가 안되있으면 그냥 클래스명을 기본값으로 사용한다.
         */
        String jpql = "select m from Album m";
        TypedQuery<Album> query = em.createQuery(jpql, Album.class);
        return query.getResultList();
    }

    @Override
    public Optional<Album> findById(Long id) {
        String jpql = "select m from Album m where m.id = :id";
        TypedQuery<Album> query = em.createQuery(jpql, Album.class);
        query.setParameter("id", id);
        List<Album> album = query.getResultList();

        if(album.isEmpty()) return Optional.empty();
        return Optional.of(album.get(0));
    }

    @Override
    public Album save(Album album) {
        em.persist(album);
        return album;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Album> album = findById(id);
        album.ifPresent((v) -> em.remove(v));
    }

    @Override
    public void deleteAll() {
        //bulk execution, 영속성 컨텍스트 무시
        String jpql = "delete from Album";
        em.createQuery(jpql).executeUpdate();
    }

    @Override
    public void flush() {
        em.flush();
    }
}
