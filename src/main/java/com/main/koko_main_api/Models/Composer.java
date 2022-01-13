package com.main.koko_main_api.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "composer_music",
//            joinColumns = @JoinColumn(name = "musics_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "composers_id", referencedColumnName = "id"))
    @ManyToMany(mappedBy = "composers")
    private List<Music> musics;

    @Builder
    public Composer(String name) {
        this.name = name;
    }
}
