package com.main.koko_main_api.Models;

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

    @Builder
    public Album(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "album")
    private List<Music> musics;

    public void update(String title) {
        this.title = title;
    }
}
