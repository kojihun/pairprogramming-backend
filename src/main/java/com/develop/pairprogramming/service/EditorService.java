package com.develop.pairprogramming.service;

import com.develop.pairprogramming.exception.FileDeleteException;
import com.develop.pairprogramming.exception.FolderDeleteException;
import com.develop.pairprogramming.exception.PythonSyntaxErrorException;
import com.develop.pairprogramming.model.Editor;
import com.develop.pairprogramming.util.JavaCompilerUtil;
import com.develop.pairprogramming.util.MethodExecutationUtil;
import com.develop.pairprogramming.util.PythonCompilerUtil;
import lombok.RequiredArgsConstructor;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EditorService {
    private final JavaCompilerUtil javaCompilerUtil;
    private final String path = "C:/Users/ADMIN/Desktop/";

    public Editor compileWithPython(Editor editor) throws IOException, PythonSyntaxErrorException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("run.py"));
        bufferedWriter.write(editor.getCode());
        bufferedWriter.close();

        System.setProperty("python.cachedir.skip", "true");
        PythonInterpreter pythonInterpreter = new PythonInterpreter();

        StringWriter out = new StringWriter();
        pythonInterpreter.setOut(out);
        pythonInterpreter.execfile("run.py");

        return Editor.builder()
                .code(out.toString())
                .build();
    }

    public Object compileWithJava(Editor editor) throws FileDeleteException, FolderDeleteException {
        Object object = javaCompilerUtil.compile(editor);
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
