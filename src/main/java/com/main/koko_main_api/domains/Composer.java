package com.main.koko_main_api.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Composer extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "composers")
    private List<Music> musics = new ArrayList<>();

    @Builder
    public Composer(String name, Long id) {
        this.id = id;
        this.name = name;
    }

    /*
     * helper
     */
    public void add_music(Music music) {
        this.musics.add(music);
    }
}
