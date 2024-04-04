package com.develop.pairprogramming.dto.request;

import com.develop.pairprogramming.model.ProblemAnswerLanguage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProblemAnswerRequestDTO {
    private Long problemId;
    private String code;
    @Enumerated(EnumType.STRING)
    private ProblemAnswerLanguage language;
}