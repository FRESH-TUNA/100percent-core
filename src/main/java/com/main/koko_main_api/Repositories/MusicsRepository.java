package com.main.koko_main_api.Repositories;

import com.main.koko_main_api.Models.Musics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "musics", path = "musics")
public interface MusicsRepository extends JpaRepository<Musics, Long>{

}