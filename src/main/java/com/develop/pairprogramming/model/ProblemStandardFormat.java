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
    private String languageType;
    private String standardFormat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public ProblemStandardFormat() {
    }

    @Builder
    public ProblemStandardFormat(String languageType, String standardFormat) {
        this.languageType = languageType;
        this.standardFormat = standardFormat;
    }
}