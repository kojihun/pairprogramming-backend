package com.develop.pairprogramming.service;

import com.develop.pairprogramming.exception.PythonSyntaxErrorException;
import com.develop.pairprogramming.model.Editor;
import com.develop.pairprogramming.util.MethodExecutationUtil;
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
    // 임시경로
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

    public void compileWithJava(Editor editor) {
        String uuid = UUID.randomUUID().toString();
        String uuidPath = path + uuid + "/";

        File newFolder = new File(uuidPath);
        File sourceFile = new File(uuidPath + "DynamicClass.java");
        File classFile = new File(uuidPath + "DynamicClass.class");

        try {
            boolean isMadeNewFolder = newFolder.mkdir();
            new FileWriter(sourceFile).append(editor.getCode()).close();

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compileResult = compiler.run(null, null, null, sourceFile.getPath());
            if (compileResult == 1) {
                System.out.println("Hello");
            }

            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {new File(uuidPath).toURI().toURL()});
            Class<?> dynamicClass = Class.forName("DynamicClass", true, urlClassLoader);

            Object object = dynamicClass.getDeclaredConstructor().newInstance();
            this.runObject(object);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sourceFile.exists()) {
                boolean isSourceFileDeleted = sourceFile.delete();
            }
            if (classFile.exists()) {
                boolean isClassFileDeleted = classFile.delete();
            }
            if (newFolder.exists()) {
                boolean isNewFolderDeleted = newFolder.delete();
            }
        }
    }

    public Map<String, Object> runObject(Object object) {
        String methodName = "runMethod";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStreamOut = System.out;

        try {
            System.setOut(new PrintStream(printStreamOut));

            Map<String, Object> result = new HashMap<>();
            Class[] arguments = new Class[0];

            Object[] params = new Object[0];
            result = MethodExecutationUtil.timeOutCall(object, methodName, params, arguments);

            System.out.println("result = " + result);

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
