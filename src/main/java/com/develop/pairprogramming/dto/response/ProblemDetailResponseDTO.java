package com.develop.pairprogramming.dto.response;

import com.develop.pairprogramming.model.Problem;
import com.develop.pairprogramming.model.Rank;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProblemDetailResponseDTO {
    private Long problemId;
    private String title;
    @Lob
    private String description;
    @Lob
    private String restriction;
    @Lob
    private String inputOutputExample;
    private Rank rank;

    public ProblemDetailResponseDTO() {
    }

    @Builder
    public ProblemDetailResponseDTO(Long problemId, String title, String description, String restriction, String inputOutputExample, Rank rank) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.restriction = restriction;
        this.inputOutputExample = inputOutputExample;
        this.rank = rank;
    }

    public static ProblemDetailResponseDTO of(Problem problem) {
        return ProblemDetailResponseDTO.builder()
                .problemId(problem.getProblemId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .restriction(problem.getRestriction())
                .inputOutputExample(problem.getInputOutputExample())
                .rank(problem.getRank())
                .build();
    }
}