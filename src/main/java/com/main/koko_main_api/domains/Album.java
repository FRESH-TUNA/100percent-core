package com.main.koko_main_api.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Album extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;


    @OneToMany(mappedBy = "album")
    private List<Music> musics;

    public void update(Album album) {
        this.title = album.getTitle();
    }

    @Builder
    public Album(String title, Long id) {
        this.id = id;
        this.title = title;
    }

}
