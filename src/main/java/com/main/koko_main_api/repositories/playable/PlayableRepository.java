package com.main.koko_main_api.repositories.playable;

import com.main.koko_main_api.domains.Playable;
import org.springframework.data.jpa.repository.JpaRepository;


//@RepositoryRestResource(collectionResourceRel = "playables", path = "playables")
public interface PlayableRepository extends JpaRepository<Playable, Long> {

//    @RestResource(exported = false)
//    Playable save(Playable playable);
}
