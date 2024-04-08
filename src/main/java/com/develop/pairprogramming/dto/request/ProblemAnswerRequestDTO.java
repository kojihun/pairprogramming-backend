package com.develop.pairprogramming.dto.request;

import com.develop.pairprogramming.model.ProblemLanguage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProblemAnswerRequestDTO {
    private String code;
    @Enumerated(EnumType.STRING)
    private ProblemLanguage language;
    private Long problemId;
}