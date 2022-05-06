package com.main.koko_main_api.repositories.album;

import com.main.koko_main_api.domains.Album;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class AlbumRepositoryJPAImpl implements AlbumRepository{
    private final EntityManager em;

    public AlbumRepositoryJPAImpl(EntityManager em) { this.em = em; }

    @Override
    @Transactional
    public Album save(Album album) {
        em.persist(album);
        return album;
    }

    @Override
    @Transactional
    public Album saveAndFlush(Album album) {
        album = save(album);
        em.flush();
        return album;
    }

    @Override
    public void flush() { em.flush(); }

    @Override
    public Optional<Album> findById(Long id) {
        TypedQuery<Album> query = em.createQuery("SELECT A FROM Album A LEFT JOIN FETCH A.musics", Album.class);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Album getById(Long id) {
        return em.getReference(Album.class, id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        em.remove(getById(id));
    }

    @Override
    @Transactional
    public void deleteAll() {
        Query query = em.createQuery("DELETE FROM Album A");
        query.executeUpdate();
    }

    @Override
    public List<Album> findAll() {
        TypedQuery<Album> query = em.createQuery("SELECT A FROM Album A", Album.class);
        return query.getResultList();
    }
}
