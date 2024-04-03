package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.dto.request.ProblemAnswerRequestDTO;
import com.develop.pairprogramming.dto.response.EditorResponseDTO;
import com.develop.pairprogramming.exception.FileDeleteException;
import com.develop.pairprogramming.exception.FolderDeleteException;
import com.develop.pairprogramming.exception.PythonSyntaxErrorException;
import com.develop.pairprogramming.model.Editor;
import com.develop.pairprogramming.model.ProblemAnswer;
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
    public ApiResponse<EditorResponseDTO> compileWithPython(@RequestBody ProblemAnswerRequestDTO problemAnswerRequestDTO) throws IOException, PythonSyntaxErrorException {
        Editor editor = editorService.compileWithPython(Editor.of(problemAnswerRequestDTO));

        return ApiResponse.createSuccess(EditorResponseDTO.of(editor));
    }

    @PostMapping("/compile/java")
    public Object compileWithJava(@RequestBody ProblemAnswerRequestDTO problemAnswerRequestDTO) throws FileDeleteException, FolderDeleteException {
        Object object = javaCompilerUtil.compile(ProblemAnswer.of(problemAnswerRequestDTO));
        if (object instanceof String) {
            return object;
        }

        long beforeTime = System.currentTimeMillis();

        int participant = 1;
        Object[] params = {participant};

        Map<String, Object> output = javaCompilerUtil.run(object, params, null);
        System.out.println("output = " + output);

        long afterTime = System.currentTimeMillis();

        Map<String, Object> returnMap = new HashMap<>(output);
        returnMap.put("performance", (afterTime - beforeTime));
        return returnMap;
    }
}
