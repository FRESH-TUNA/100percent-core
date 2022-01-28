package com.main.koko_main_api.repositories;

import com.main.koko_main_api.domains.Playable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


//@RepositoryRestResource(collectionResourceRel = "playables", path = "playables")
public interface PlayablesRepository extends JpaRepository<Playable, Long> {

//    @RestResource(exported = false)
//    Playable save(Playable playable);
}
