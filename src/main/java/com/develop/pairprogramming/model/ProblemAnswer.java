package com.develop.pairprogramming.model;

import com.develop.pairprogramming.dto.request.ProblemAnswerRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ProblemAnswer {
    @Id
    @GeneratedValue
    private Long problemAnswerId;
    private String code;
    @Enumerated(EnumType.STRING)
    private ProblemAnswerLanguage language;
    @Enumerated(EnumType.STRING)
    private ProblemAnswerStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public ProblemAnswer() {
    }

    @Builder
    public ProblemAnswer(ProblemAnswerLanguage language, String code, ProblemAnswerStatus status, Member member, Problem problem) {
        this.language = language;
        this.code = code;
        this.status = status;
        this.member = member;
        this.problem = problem;
    }

    public void changeStatus(ProblemAnswerStatus status) {
        this.status = status;
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeProblem(Problem problem) {
        this.problem = problem;
    }

    public static ProblemAnswer of(ProblemAnswerRequestDTO problemAnswerRequestDTO) {
        return ProblemAnswer.builder()
                .language(problemAnswerRequestDTO.getLanguage())
                .code(problemAnswerRequestDTO.getCode())
                .status(ProblemAnswerStatus.FAIL)
                .build();
    }
}