package com.main.koko_main_api.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Musics extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "albums_id")
    private Album album;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "composer_music",
            joinColumns = @JoinColumn(name = "composers_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "musics_id", referencedColumnName = "id"))
    private List<Composer> composers;

    @Builder
    public Musics(String title) {
        this.title = title;
    }
}
