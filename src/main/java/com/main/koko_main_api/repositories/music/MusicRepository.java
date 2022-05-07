package com.main.koko_main_api.repositories.music;

import com.main.koko_main_api.domains.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "musics", path = "musics")
public interface MusicRepository extends JpaRepository<Music, Long>, MusicSearchRepository<Music, Long> {

}
