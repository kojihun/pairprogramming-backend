package com.develop.pairprogramming.dto.response;

import com.develop.pairprogramming.model.ProblemFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class ProblemFormatResponseDTO {
    private String code;
    private String language;

    public ProblemFormatResponseDTO() {

    }

    @Builder
    public ProblemFormatResponseDTO(String code, String language) {
        this.code = code;
        this.language = language;
    }

    public static ProblemFormatResponseDTO of(ProblemFormat problemFormat) {
        return ProblemFormatResponseDTO.builder()
                .code(problemFormat.getCode())
                .language(problemFormat.getLanguage().name())
                .build();
    }

    public static List<ProblemFormatResponseDTO> listOf(List<ProblemFormat> problemFormats) {
        List<ProblemFormatResponseDTO> list = new ArrayList<>();
        for (ProblemFormat problemFormat : problemFormats) {
            list.add(ProblemFormatResponseDTO.of(problemFormat));
        }

        return list;
    }
}