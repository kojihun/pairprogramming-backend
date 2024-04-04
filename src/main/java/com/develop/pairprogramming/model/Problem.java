package com.develop.pairprogramming.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Problem {
    @Id
    @GeneratedValue
    private Long problemId;
    private String title;
    @Lob
    private String description;
    @Lob
    private String restriction;
    @Lob
    private String inputOutputExample;
    @Enumerated(EnumType.STRING)
    private Rank rank;

    public Problem() {

    }

    @Builder
    public Problem(String title, String description, String restriction, String inputOutputExample, Rank rank) {
        this.title = title;
        this.description = description;
        this.restriction = restriction;
        this.inputOutputExample = inputOutputExample;
        this.rank = rank;
    }
}