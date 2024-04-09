package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.dto.request.ProblemAnswerRequestDTO;
import com.develop.pairprogramming.dto.response.*;
import com.develop.pairprogramming.exception.FileDeleteException;
import com.develop.pairprogramming.exception.FolderDeleteException;
import com.develop.pairprogramming.model.*;
import com.develop.pairprogramming.service.MemberService;
import com.develop.pairprogramming.service.ProblemService;
import com.develop.pairprogramming.service.problem.strategy.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/problems")
@RequiredArgsConstructor
@RestController
public class ProblemApiController {
    private final ProblemService problemService;
    private final MemberService memberService;

    /**
     * 모든 문제를 조회한다.
     *
     * @param pageNumber   페이지 번호 (기본값: 1)
     * @param pageSize     페이지 크기 (기본값: 10)
     * @param searchInput  검색어 (선택 사항)
     * @param searchSelect 검색 옵션 (선택 사항)
     * @return 문제 조회 결과를 담은 응답
     */
    @GetMapping("/all")
    public ApiResponse<ProblemResponseDTO> getAllProblems(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "searchInput", required = false) String searchInput,
            @RequestParam(name = "searchSelect", required = false) ProblemRank searchSelect) {
        ProblemSearchStrategy problemSearchStrategy;

        if (isSearchInputValid(searchInput) && isSearchSelectValid(searchSelect)) {
            problemSearchStrategy = new TitleAndRankSearchStrategy(problemService, searchInput, searchSelect);
        } else if (isSearchInputValid(searchInput)) {
            problemSearchStrategy = new TitleSearchStrategy(problemService, searchInput);
        } else if (isSearchSelectValid(searchSelect)) {
            problemSearchStrategy = new RankSearchStrategy(problemService, searchSelect);
        } else {
            problemSearchStrategy = new AllProblemsSearchStrategy(problemService);
        }

        List<Problem> problems = problemSearchStrategy.findProblems(pageNumber, pageSize);
        long totalPages = problemSearchStrategy.countProblems();

        return ApiResponse.createSuccess(ProblemResponseDTO.of(ProblemListResponseDTO.listOf(problems), totalPages));
    }

    /**
     * 검색어의 유효성을 확인한다.
     *
     * @param searchInput 검색어
     * @return 검색어가 유효한지 여부를 나타내는 boolean 값
     */
    private boolean isSearchInputValid(String searchInput) {
        return searchInput != null && !searchInput.isEmpty();
    }

    /**
     * 등급의 유효성을 확인한다.
     *
     * @param searchSelect 등급
     * @return 등급이 유효한지 여부를 나타내는 boolean 값
     */
    private boolean isSearchSelectValid(ProblemRank searchSelect) {
        return searchSelect != null;
    }

    /**
     * 문제의 세부 정보를 조회한다.
     *
     * @param problemId 문제의 식별자
     * @return 문제 세부 정보를 포함한 응답
     */
    @GetMapping("/detail")
    public ApiResponse<ProblemDetailResponseDTO> getProblemDetail(@RequestParam(name = "problemId") Long problemId) {
        Problem foundProblem = problemService.findProblemById(problemId);

        return ApiResponse.createSuccess(ProblemDetailResponseDTO.of(foundProblem));
    }

    /**
     * 문제의 표준 형식을 조회한다.
     *
     * @param problemId 문제 식별자
     * @param language 언어유형
     * @return 문제의 표준 형식을 포함한 응답
     */
    @GetMapping("/format")
    public ApiResponse<ProblemAnswerResponseDTO> getProblemFormat(
            @RequestParam(name = "problemId") Long problemId,
            @RequestParam(name = "language") ProblemLanguage language,
            HttpServletRequest request) {
        Problem foundProblem = problemService.findProblemById(problemId);
        Member foundMember = memberService.findMemberById((Long) request.getAttribute("memberId"));

        List<ProblemAnswer> foundProblemAnswers = problemService.findProblemAnswerByProblemAndLanguageAndMember(foundProblem, language, foundMember);
        if (foundProblemAnswers.isEmpty()) {
            problemService.initProblemAnswer(foundProblem, language, foundMember);
        }

        List<ProblemAnswer> problemAnswers = problemService.findProblemAnswerByProblemAndLanguageAndMember(foundProblem, language, foundMember);
        return ApiResponse.createSuccess(ProblemAnswerResponseDTO.of(problemAnswers.get(0)));
    }

    @GetMapping("/format/{uuid}")
    public ApiResponse<?> getProblemFormatByUuid(
            @RequestParam(name = "problemId") Long problemId,
            @RequestParam(name = "language") ProblemLanguage language,
            @PathVariable(name = "uuid") String uuid) {
        Problem foundProblem = problemService.findProblemById(problemId);

        List<ProblemAnswer> foundProblemAnswers = problemService.findProblemAnswerByProblemAndLanguageAndUuid(foundProblem, language, UUID.fromString(uuid));
        if (foundProblemAnswers.isEmpty()) {
            ProblemFormat foundProblemFormat = problemService.findProblemFormatByProblemAndLanguage(foundProblem, language);
            return ApiResponse.createSuccess(foundProblemFormat);
        } else {
            return ApiResponse.createSuccess(ProblemAnswerResponseDTO.of(foundProblemAnswers.get(0)));
        }
    }

    @GetMapping("/format/clear")
    public ApiResponse<ProblemFormatResponseDTO> clearProblemFormat(
            @RequestParam(name = "problemId") Long problemId,
            @RequestParam(name = "language") ProblemLanguage language) {
        Problem foundProblem = problemService.findProblemById(problemId);
        ProblemFormat foundProblemFormat = problemService.findProblemFormatByProblemAndLanguage(foundProblem, language);

        return ApiResponse.createSuccess(ProblemFormatResponseDTO.of(foundProblemFormat));
    }

    @PostMapping("/answer")
    public ApiResponse<?> postProblemAnswer(
            @RequestBody ProblemAnswerRequestDTO requestDTO,
            HttpServletRequest request) {
        Problem foundProblem = problemService.findProblemById(requestDTO.getProblemId());
        Member foundMember = memberService.findMemberById((Long) request.getAttribute("memberId"));

        problemService.modifyProblemAnswer(foundProblem, foundMember, ProblemAnswer.of(requestDTO));
        return ApiResponse.createSuccessWithNoContent();
    }

    @PostMapping("/answer/{uuid}")
    public ApiResponse<?> postProblemAnswerByUuid(
            @RequestBody ProblemAnswerRequestDTO requestDTO,
            @PathVariable(name = "uuid") String uuid) {
        Problem foundProblem = problemService.findProblemById(requestDTO.getProblemId());

        problemService.modifyProblemAnswerByUuid(foundProblem, UUID.fromString(uuid), ProblemAnswer.of(requestDTO));
        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 문제의 제출 목록을 가져온다.
     *
     * @param problemId 문제 식별자
     * @param request HTTP 요청 객체
     * @return 문제에 대한 제출 목록을 담은 응답
     */
    @GetMapping("/answer/submit")
    public ApiResponse<?> getProblemAnswerSubmits(
            @RequestParam(name = "problemId") Long problemId,
            @RequestParam(name = "uuid") String uuid) {

        List<ProblemAnswerSubmit> problemAnswerSubmits = problemService.findAllProblemAnswerSubmitsByProblemIdAndUuid(problemId, UUID.fromString(uuid));
        return ApiResponse.createSuccess(problemAnswerSubmits);
    }

    @GetMapping("/answer/submit/{uuid}")
    public ApiResponse<?> getProblemAnswerSubmitsByUuid(
            @RequestParam(name = "problemId") Long problemId,
            @PathVariable(name = "uuid") String uuid) {
        Problem foundProblem = problemService.findProblemById(problemId);

        List<ProblemAnswerSubmit> problemAnswerSubmits = problemService.findAllProblemAnswerSubmitsByProblemAndUuid(foundProblem, UUID.fromString(uuid));
        return ApiResponse.createSuccess(problemAnswerSubmits);
    }

    /**
     * 문제를 컴파일한다.
     *
     * @param requestDTO 문제 답변 정보가 담긴 DTO
     * @return 컴파일 결과를 담은 응답
     * @throws FileDeleteException 파일 삭제 예외
     * @throws FolderDeleteException 폴더 삭제 예외
     */
    @PostMapping("/compile")
    public ApiResponse<?> compileProblem(@RequestBody ProblemAnswerRequestDTO requestDTO, HttpServletRequest request) throws FileDeleteException, FolderDeleteException {
        Problem foundProblem = problemService.findProblemById(requestDTO.getProblemId());
        Member foundMember = memberService.findMemberById((Long) request.getAttribute("memberId"));

        List<ProblemTestResult> problemTestResults = problemService.compile(ProblemAnswer.of(requestDTO), foundProblem, foundMember);
        return ApiResponse.createSuccess(problemTestResults);
    }
}