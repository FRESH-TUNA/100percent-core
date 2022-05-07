package com.main.koko_main_api.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Music extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @Column
    private Integer min_bpm, max_bpm;

//    @ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany
    @JoinTable(name = "composer_music",
            joinColumns = @JoinColumn(name = "composer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
    private List<Composer> composers = new ArrayList<>();

    // @RestResource(exported = false)
    @OneToMany(mappedBy = "music")
    private List<Pattern> patterns = new ArrayList<>();

    /*
     * methods
     */
    public void add_composer(Composer composer) {
        this.composers.add(composer);
    }
    public void add_pattern(Pattern pattern) {
        this.patterns.add(pattern);
    }

    public void update(Music new_music) {
        this.title = new_music.getTitle();
        this.album = new_music.getAlbum();
        this.min_bpm = new_music.getMin_bpm(); this.max_bpm = new_music.getMax_bpm();
        this.composers = new_music.getComposers();
    }

    @Builder
    public Music(String title, Album album, Long id, Integer min_bpm, Integer max_bpm, List<Composer> composers) {
        this.title = title; this.album = album; this.id = id;
        this.min_bpm = min_bpm; this.max_bpm = max_bpm;
        this.composers = composers;
    }
}
