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
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "composer_music",
            joinColumns = @JoinColumn(name = "composer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
    private List<Composer> composers;

    // bpms에 한해서 @RestResource를 적용하지 않는다. (URI을 적용하지 않는다.)
    // @RestResource(exported = false)
    @OneToMany(mappedBy = "music")
    private List<Playable> playables = new ArrayList<>();

    public void add_playable(Playable playable) {
        this.playables.add(playable);
    }

    @Builder
    public Music(String title, Album album, Long id) {
        this.title = title;
        this.album = album;
        this.id = id;
    }
}
