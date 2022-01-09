package com.main.koko_main_api.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Albums extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Builder
    public Albums(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "album")
    private List<Musics> musics;

    public void update(String title) {
        this.title = title;
    }
}
