package com.develop.pairprogramming.dto.response;

import com.develop.pairprogramming.model.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ProblemListResponseDTO {
    private Long problemId;
    private String title;
    private String rank;

    public ProblemListResponseDTO() {
    }

    @Builder
    public ProblemListResponseDTO(Long problemId, String title, String rank) {
        this.problemId = problemId;
        this.title = title;
        this.rank = rank;
    }

    public static ProblemListResponseDTO of(Problem problem) {
        return ProblemListResponseDTO.builder()
                .problemId(problem.getProblemId())
                .title(problem.getTitle())
                .rank(problem.getRank().name())
                .build();
    }

    public static List<ProblemListResponseDTO> listOf(List<Problem> problems) {
        List<ProblemListResponseDTO> problemResponseDTOs = new ArrayList<>();
        for (Problem problem : problems) {
            problemResponseDTOs.add(ProblemListResponseDTO.of(problem));
        }
        return problemResponseDTOs;
    }
}