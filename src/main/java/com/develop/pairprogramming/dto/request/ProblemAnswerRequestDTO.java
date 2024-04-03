package com.develop.pairprogramming.dto.request;

import com.develop.pairprogramming.model.ProblemAnswerLanguage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemAnswerRequestDTO {
    private String code;
    @Enumerated(EnumType.STRING)
    private ProblemAnswerLanguage language;
    private Long problemId;
}