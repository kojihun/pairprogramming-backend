package com.develop.pairprogramming.dto.response;

import com.develop.pairprogramming.model.ProblemAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class ProblemAnswerResponseDTO {
    private String code;
    private String language;
    private String uuid;

    public ProblemAnswerResponseDTO() {

    }

    @Builder
    public ProblemAnswerResponseDTO(String code, String language, String uuid) {
        this.code = code;
        this.language = language;
        this.uuid = uuid;
    }

    public static ProblemAnswerResponseDTO of(ProblemAnswer problemAnswer) {
        return ProblemAnswerResponseDTO.builder()
                .code(problemAnswer.getCode())
                .language(problemAnswer.getLanguage().name())
                .uuid(problemAnswer.getUuid().toString())
                .build();
    }

    public static List<ProblemAnswerResponseDTO> listOf(List<ProblemAnswer> problemAnswers) {
        List<ProblemAnswerResponseDTO> list = new ArrayList<>();
        for (ProblemAnswer problemAnswer : problemAnswers) {
            list.add(ProblemAnswerResponseDTO.of(problemAnswer));
        }

        return list;
    }
}