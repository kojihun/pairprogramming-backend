package com.develop.pairprogramming.service;

import com.develop.pairprogramming.exception.FileDeleteException;
import com.develop.pairprogramming.exception.FolderDeleteException;
import com.develop.pairprogramming.exception.JavaCompileErrorException;
import com.develop.pairprogramming.model.*;
import com.develop.pairprogramming.repository.MemberRepository;
import com.develop.pairprogramming.repository.ProblemRepository;
import com.develop.pairprogramming.util.JavaCompilerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProblemService {
    private final JavaCompilerUtil javaCompilerUtil;
    private final ProblemRepository problemRepository;
    private final MemberRepository memberRepository;

    /**
     * 전체 문제를 조회한다.
     *
     * @param pageNumber 페이지 번호
     * @param pageSize   페이지 크기
     * @return 전체 문제 목록을 반환한다.
     */
    public List<Problem> findAllProblems(int pageNumber, int pageSize) {
        return problemRepository.findAllProblems(pageNumber, pageSize);
    }

    /**
     * 제목으로 검색된 문제를 조회한다.
     *
     * @param searchInput 검색어
     * @param pageNumber  페이지 번호
     * @param pageSize    페이지 크기
     * @return 검색된 문제 목록을 반환한다.
     */
    public List<Problem> findProblemsByTitle(String searchInput, int pageNumber, int pageSize) {
        return problemRepository.findProblemsByTitle(searchInput, pageNumber, pageSize);
    }

    /**
     * 등급으로 검색된 문제를 조회한다.
     *
     * @param searchSelect 등급
     * @param pageNumber   페이지 번호
     * @param pageSize     페이지 크기
     * @return 검색된 문제 목록을 반환한다.
     */
    public List<Problem> findProblemsByRank(ProblemRank searchSelect, int pageNumber, int pageSize) {
        return problemRepository.findProblemsByRank(searchSelect, pageNumber, pageSize);
    }

    /**
     * 등급과 제목으로 검색된 문제를 조회한다.
     *
     * @param searchInput  검색어
     * @param searchSelect 등급
     * @param pageNumber   페이지 번호
     * @param pageSize     페이지 크기
     * @return 검색된 문제 목록을 반환한다.
     */
    public List<Problem> findProblemsByTitleAndRank(String searchInput, ProblemRank searchSelect, int pageNumber, int pageSize) {
        return problemRepository.findProblemsByTitleAndRank(searchInput, searchSelect, pageNumber, pageSize);
    }

    /**
     * 전체 문제 수를 반환한다.
     *
     * @return 전체 문제 수
     */
    public long countAllProblems() {
        return problemRepository.countAllProblems();
    }

    /**
     * 제목으로 검색된 문제 수를 반환한다.
     *
     * @param searchInput 검색어
     * @return 검색된 문제 수
     */
    public long countProblemsByTitle(String searchInput) {
        return problemRepository.countProblemsByTitle(searchInput);
    }

    /**
     * 등급으로 조회된 문제 수를 반환한다.
     *
     * @param searchSelect 등급
     * @return 조회된 문제 수
     */
    public long countProblemsByRank(ProblemRank searchSelect) {
        return problemRepository.countProblemsByRank(searchSelect);
    }

    /**
     * 등급과 제목으로 검색된 문제 수를 반환한다.
     *
     * @param searchInput  검색어
     * @param searchSelect 등급
     * @return 검색된 문제 수
     */
    public long countProblemsByTitleAndRank(String searchInput, ProblemRank searchSelect) {
        return problemRepository.countProblemsByTitleAndRank(searchInput, searchSelect);
    }

    /**
     * 주어진 문제 ID에 해당하는 문제를 찾아 반환하는 메서드
     *
     * @param problemId 문제 ID
     * @return 해당 문제 객체
     */
    public Problem findProblemById(Long problemId) {
        return problemRepository.findProblemById(problemId);
    }

    /**
     * 주어진 문제 표준 형식 ID와 언어 유형에 해당하는 문제 표준 형식을 반환하는 메서드
     *
     * @param problem 문제 객체
     * @param language 언어 유형
     * @return 주어진 문제 표준 형식 ID와 언어 유형에 해당하는 문제 표준 형식 객체
     */
    public ProblemFormat findProblemFormatByProblemAndLanguage(Problem problem, ProblemLanguage language) {
        return problemRepository.findProblemFormatByProblemAndLanguage(problem, language);
    }

    @Transactional
    public void initProblemAnswer(Problem problem, ProblemLanguage language, Member member) {
        ProblemFormat problemFormat = problemRepository.findProblemFormatByProblemAndLanguage(problem, language);
        ProblemAnswer initProblemAnswer = ProblemAnswer.builder()
                .language(language)
                .code(problemFormat.getCode())
                .uuid(member.getUuid())
                .member(member)
                .problem(problem)
                .build();

        problemRepository.saveProblemAnswer(initProblemAnswer);
    }

    @Transactional
    public void modifyProblemAnswer(Problem problem, Member member, ProblemAnswer problemAnswer) {
        List<ProblemAnswer> foundProblemAnswers = problemRepository.findProblemAnswerByProblemAndLanguageAndMember(problem, problemAnswer.getLanguage(), member);
        if (!foundProblemAnswers.isEmpty()) {
            ProblemAnswer foundProblemAnswer = foundProblemAnswers.get(0);
            foundProblemAnswer.modifyCode(problemAnswer.getCode());
        }
    }

    @Transactional
    public void modifyProblemAnswerByUuid(Problem problem, UUID uuid, ProblemAnswer problemAnswer) {
        List<ProblemAnswer> foundProblemAnswers = problemRepository.findProblemAnswerByProblemAndLanguageAndUuid(problem, problemAnswer.getLanguage(), uuid);
        if (!foundProblemAnswers.isEmpty()) {
            ProblemAnswer foundProblemAnswer = foundProblemAnswers.get(0);
            foundProblemAnswer.modifyCode(problemAnswer.getCode());
        }
    }

    public List<ProblemAnswer> findProblemAnswerByProblemAndLanguageAndMember(Problem problem, ProblemLanguage language, Member member) {
        return problemRepository.findProblemAnswerByProblemAndLanguageAndMember(problem, language, member);
    }

    public List<ProblemAnswer> findProblemAnswerByProblemAndLanguageAndUuid(Problem problem, ProblemLanguage language, UUID uuid) {
        return problemRepository.findProblemAnswerByProblemAndLanguageAndUuid(problem, language, uuid);
    }

    /**
     * 문제에 대한 모든 테스트 케이스를 조회한다.
     *
     * @param problem 문제 객체
     * @return 문제에 대한 모든 테스트 케이스 리스트
     */
    public List<ProblemTestCase> findAllProblemTestCasesByProblem(Problem problem) {
        return problemRepository.findAllProblemTestCasesByProblem(problem);
    }

    /**
     * 문제와 회원에 대한 제출 내역을 조회한다.
     * 
     * @param problem 문제 객체
     * @param member 회원 객체
     * @return 문제와 회원에 대한 모든 제출 내역 리스트
     */
    public List<ProblemAnswer> findAllProblemAnswersByProblemAndMember(Problem problem, Member member) {
        return problemRepository.findAllProblemAnswersByProblemAndMember(problem, member);
    }

    public List<ProblemAnswerSubmit> findAllProblemAnswerSubmitsByProblemIdAndUuid(Long problemId, UUID uuid) {
        return problemRepository.findAllProblemAnswerSubmitsByProblemAndMember(problemId, uuid);
    }

    public List<ProblemAnswerSubmit> findAllProblemAnswerSubmitsByProblemAndUuid(Problem problem, UUID uuid) {
        return problemRepository.findAllProblemAnswerSubmitsByProblemAndUuid(problem, uuid);
    }

    @Transactional
    public List<ProblemTestResult> compile(ProblemAnswer problemAnswer, Problem problem, Member member) throws FileDeleteException, FolderDeleteException {
        ProblemLanguage language = problemAnswer.getLanguage();
        switch (language) {
            case PYTHON:
                // Python 컴파일 로직
                return null;
            case JAVA:
                Object compileResult = javaCompilerUtil.compile(problemAnswer);
                if (compileResult instanceof String) {
                    throw new JavaCompileErrorException((String) compileResult);
                }

                List<ProblemTestResult> problemTestResults = runJavaTests(compileResult, problem);
                boolean isSuccess = true;
                for (ProblemTestResult testResult : problemTestResults) {
                    if (testResult.getStatus().equals("FAIL")) {
                        isSuccess = false;
                        break;
                    }
                }

                List<ProblemAnswer> foundProblemAnswers = problemRepository.findProblemAnswerByProblemAndLanguageAndMember(problem, problemAnswer.getLanguage(), member);
                if (!foundProblemAnswers.isEmpty()) {
                    ProblemAnswer foundProblemAnswer = foundProblemAnswers.get(0);
                    foundProblemAnswer.modifyCode(problemAnswer.getCode());

                    ProblemAnswerSubmit problemAnswerSubmit = ProblemAnswerSubmit.builder()
                            .code(foundProblemAnswer.getCode())
                            .language(foundProblemAnswer.getLanguage())
                            .uuid(foundProblemAnswer.getUuid())
                            .status(isSuccess ? ProblemStatus.SUCCESS : ProblemStatus.FAIL)
                            .submitDate(new Timestamp(System.currentTimeMillis()))
                            .problemId(problem.getProblemId())
                            .problemAnswer(foundProblemAnswer)
                            .build();

                    problemRepository.saveProblemAnswerSubmit(problemAnswerSubmit);
                }

                return problemTestResults;
            default:
                return null;
        }
    }

    /**
     * 주어진 컴파일 결과와 문제의 테스트 케이스들을 실행하여 모든 테스트 케이스가 성공했는지 여부를 반환한다.
     *
     * @param compileResult 컴파일 결과
     * @param problem 문제 객체
     * @return 모든 테스트 케이스가 성공했는지 여부를 나타내는 boolean 값
     */
    private List<ProblemTestResult> runJavaTests(Object compileResult, Problem problem) {
        List<ProblemTestResult> problemTestResults = new ArrayList<>();

        List<ProblemTestCase> problemTestCases = problemRepository.findAllProblemTestCasesByProblem(problem);
        for (int i = 0; i < problemTestCases.size(); i++) {
            Object[] params = {problemTestCases.get(i).getInput()};
            String expectedOutput = problemTestCases.get(i).getOutput();

            Map<String, Object> runResult = javaCompilerUtil.run(compileResult, params, expectedOutput);
            if (!runResult.get("return").equals(expectedOutput)) {
                problemTestResults.add(ProblemTestResult.builder()
                        .problemTestCaseNo(i + 1L)
                        .input(problemTestCases.get(i).getInput())
                        .expectedOutput(problemTestCases.get(i).getOutput())
                        .actualOutput((String) runResult.get("return"))
                        .message((String) runResult.get("message"))
                        .status("SUCCESS")
                        .build());
            } else {
                problemTestResults.add(ProblemTestResult.builder()
                        .problemTestCaseNo(i + 1L)
                        .input(problemTestCases.get(i).getInput())
                        .expectedOutput(problemTestCases.get(i).getOutput())
                        .actualOutput((String) runResult.get("return"))
                        .message((String) runResult.get("message"))
                        .status("FAIL")
                        .build());
            }
        }

        return problemTestResults;
    }

    private boolean runPythonTests() {
        return true;
    }
}