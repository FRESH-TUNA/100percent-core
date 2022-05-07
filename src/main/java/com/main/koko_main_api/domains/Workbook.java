package com.main.koko_main_api.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
public class Workbook extends BaseTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "play_type_id")
    private PlayType playType;

    @OneToMany(mappedBy = "workbook", cascade = CascadeType.ALL)
    private List<WorkbookPattern> patterns = new ArrayList<>();

    /*
     * methods
     */
    public void add_patterns(List<Pattern> patterns) {
        this.patterns = patterns.stream()
                .map(p -> WorkbookPattern.builder().pattern(p).workbook(this).build())
                .collect(Collectors.toList());
    }

    public void update(Workbook new_workbook) {
        this.title = new_workbook.getTitle();
        this.description = new_workbook.getDescription();
        this.patterns = new_workbook.getPatterns();
        this.playType = new_workbook.getPlayType();
    }

    @Builder
    public Workbook(Long id, String title, String description,
                    List<WorkbookPattern> patterns,
                    PlayType playType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.patterns = patterns;
        this.playType = playType;
    }
}
