package com.develop.pairprogramming.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ProblemStandardFormat {
    @Id
    @GeneratedValue
    private Long problemStandardFormatId;
    private String language;
    private String format;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public ProblemStandardFormat() {

    }

    @Builder
    public ProblemStandardFormat(String language, String format) {
        this.language = language;
        this.format = format;
    }
}