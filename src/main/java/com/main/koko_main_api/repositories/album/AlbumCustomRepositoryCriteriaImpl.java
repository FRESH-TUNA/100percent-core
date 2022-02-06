package com.main.koko_main_api.repositories.album;

import com.main.koko_main_api.domains.Album;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class AlbumCustomRepositoryCriteriaImpl implements AlbumCustomRepository{
    @PersistenceContext
    private EntityManager em;

    /*
     * select m from Member m
     */
    @Override
    public List<Album> findAll() {
        // 쿼리 빌더 받아오기
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 쿼리 빌더로 해당하는 엔티티의 쿼리 생성
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);

        //FROM
        Root<Album> m = cq.from(Album.class);

        //SELECT
        cq.select(m);

        // querying
        em.createQuery(cq).getResultList();
    }

    @Override
    public Album find(Long id) {
        // 쿼리 빌더 받아오기
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 쿼리 빌더로 해당하는 엔티티의 쿼리 생성
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);

        //FROM
        Root<Album> m = cq.from(Album.class);

        //WHERE
        Predicate id_equal = cb.equal(m.<Long>get("id"), id);

        //SELECT and WHERE
        cq.select(m).where(id_equal);

        // querying
        em.createQuery(cq).getResultList().get(0);
    }
}
