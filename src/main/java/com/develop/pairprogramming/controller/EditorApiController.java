package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.dto.request.EditorRequestDTO;
import com.develop.pairprogramming.dto.response.EditorResponseDTO;
import com.develop.pairprogramming.exception.FileDeleteException;
import com.develop.pairprogramming.exception.FolderDeleteException;
import com.develop.pairprogramming.exception.PythonSyntaxErrorException;
import com.develop.pairprogramming.model.Editor;
import com.develop.pairprogramming.service.EditorService;
import com.develop.pairprogramming.util.JavaCompilerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/editor")
@RequiredArgsConstructor
@RestController
public class EditorApiController {
    private final EditorService editorService;
    private final JavaCompilerUtil javaCompilerUtil;

    @PostMapping("/compile/python")
    public ApiResponse<EditorResponseDTO> compileWithPython(@RequestBody EditorRequestDTO editorRequestDTO) throws IOException, PythonSyntaxErrorException {
        Editor editor = editorService.compileWithPython(Editor.of(editorRequestDTO));

        return ApiResponse.createSuccess(EditorResponseDTO.of(editor));
    }

    @PostMapping("/compile/java")
    public Object compileWithJava(@RequestBody EditorRequestDTO editorRequestDTO) throws FileDeleteException, FolderDeleteException {

        Object object = javaCompilerUtil.compile(Editor.of(editorRequestDTO));
        if (object instanceof String) {
            return object;
        }

        long beforeTime = System.currentTimeMillis();

        int participant = 1;
        Object[] params = {participant};

        Map<String, Object> output = javaCompilerUtil.runWithJava(object, params);
        System.out.println("output = " + output);

        long afterTime = System.currentTimeMillis();

        Map<String, Object> returnMap = new HashMap<>(output);
        returnMap.put("performance", (afterTime - beforeTime));
        return returnMap;
    }
}
