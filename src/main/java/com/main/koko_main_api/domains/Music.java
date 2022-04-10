package com.main.koko_main_api.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "composer_music",
            joinColumns = @JoinColumn(name = "composer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
    private List<Composer> composers = new ArrayList<>();

    // bpms에 한해서 @RestResource를 적용하지 않는다. (URI을 적용하지 않는다.)
    // @RestResource(exported = false)
    @OneToMany(mappedBy = "music")
    private List<Pattern> patterns = new ArrayList<>();

    // bpms에 한해서 @RestResource를 적용하지 않는다. (URI을 적용하지 않는다.)
    // @RestResource(exported = false)
    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bpm> bpms = new ArrayList<>();



    /*
     * methods
     */
    /*
     * for new music
     * 실제 쿼리가 들어가지는 않지만 객체의 양방향 연결을 위해 사용한다.
     * 또한 객체 address를 바꾸지 않아서 필요없는 update쿼리를 막는다.
     */
    public void add_bpms(List<Bpm> bpms) {
        Iterator<Bpm> it = bpms.iterator();
        while(it.hasNext()) this.bpms.add(it.next());
    }
    public void add_bpm(Bpm bpm) {
        this.bpms.add(bpm);
    }
    public void add_composer(Composer composer) {
        this.composers.add(composer);
    }
    public void add_pattern(Pattern pattern) {
        this.patterns.add(pattern);
    }

    @Builder
    public Music(String title, Album album, Long id) {
        this.title = title;
        this.album = album;
        this.id = id;
    }
}
