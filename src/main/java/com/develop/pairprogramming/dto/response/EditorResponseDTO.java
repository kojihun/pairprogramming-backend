package com.develop.pairprogramming.dto.response;

import com.develop.pairprogramming.model.Editor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditorResponseDTO {
    private String code;

    public EditorResponseDTO() {
    }

    @Builder
    public EditorResponseDTO(String code) {
        this.code = code;
    }

    public static EditorResponseDTO of(Editor editor) {
        return EditorResponseDTO.builder()
                .code(editor.getCode())
                .build();
    }
}
