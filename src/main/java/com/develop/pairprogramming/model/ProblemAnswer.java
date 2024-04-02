package com.develop.pairprogramming.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ProblemAnswer {
    @Id
    @GeneratedValue
    private Long problemAnswerId;
    private String languageType;
    private String defaultFormat;
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public ProblemAnswer() {
    }

    @Builder
    public ProblemAnswer(String languageType, String defaultFormat, String answer, Member member, Problem problem) {
        this.languageType = languageType;
        this.defaultFormat = defaultFormat;
        this.answer = answer;
        this.member = member;
        this.problem = problem;
    }
}