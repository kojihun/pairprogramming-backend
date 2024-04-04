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
    public ProblemAnswer(Long problemAnswerId, String code, ProblemAnswerLanguage language, ProblemAnswerStatus status, Member member, Problem problem) {
        this.problemAnswerId = problemAnswerId;
        this.code = code;
        this.language = language;
        this.status = status;
        this.member = member;
        this.problem = problem;
    }

    public static ProblemAnswer of(ProblemAnswerRequestDTO problemAnswerRequestDTO) {
        return ProblemAnswer.builder()
                .code(problemAnswerRequestDTO.getCode())
                .language(problemAnswerRequestDTO.getLanguage())
                .status(ProblemAnswerStatus.FAIL)
                .build();
    }
}