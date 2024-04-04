package com.develop.pairprogramming.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProblemResponseDTO {
    private List<ProblemListResponseDTO> problems;
    private long totalPages;

    public ProblemResponseDTO() {

    }

    @Builder
    public ProblemResponseDTO(List<ProblemListResponseDTO> problems, long totalPages) {
        this.problems = problems;
        this.totalPages = totalPages;
    }

    public static ProblemResponseDTO of(List<ProblemListResponseDTO> problems, long totalPages) {
        return ProblemResponseDTO.builder()
                .problems(problems)
                .totalPages(totalPages)
                .build();
    }
}