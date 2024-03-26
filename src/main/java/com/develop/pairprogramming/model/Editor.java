package com.develop.pairprogramming.model;

import com.develop.pairprogramming.dto.request.EditorRequestDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Editor {
    private String code;

    public Editor() {

    }

    @Builder
    public Editor(String code) {
        this.code = code;
    }

    public static Editor of(EditorRequestDTO editorRequestDTO) {
        return Editor.builder()
                .code(editorRequestDTO.getCode())
                .build();
    }
}
