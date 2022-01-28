package com.main.koko_main_api.repositories;

import com.main.koko_main_api.domains.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "musics", path = "musics")
public interface MusicsRepository extends JpaRepository<Music, Long>{

}
