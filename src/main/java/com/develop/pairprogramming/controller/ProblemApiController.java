package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.dto.request.ProblemAnswerRequestDTO;
import com.develop.pairprogramming.dto.response.ProblemAnswerResponseDTO;
import com.develop.pairprogramming.dto.response.ProblemDetailResponseDTO;
import com.develop.pairprogramming.dto.response.ProblemListResponseDTO;
import com.develop.pairprogramming.dto.response.ProblemResponseDTO;
import com.develop.pairprogramming.exception.FileDeleteException;
import com.develop.pairprogramming.exception.FolderDeleteException;
import com.develop.pairprogramming.model.*;
import com.develop.pairprogramming.service.MemberService;
import com.develop.pairprogramming.service.ProblemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(name = "searchSelect", required = false) Rank searchSelect) {
        List<Problem> problems;
        long totalPages;

        if (isSearchInputValid(searchInput) && isSearchSelectValid(searchSelect)) {
            problems = problemService.findProblemsByTitleAndRank(searchInput, searchSelect, pageNumber, pageSize);
            totalPages = problemService.countProblemsByTitleAndRank(searchInput, searchSelect);
        } else if (isSearchInputValid(searchInput)) {
            problems = problemService.findProblemsByTitle(searchInput, pageNumber, pageSize);
            totalPages = problemService.countProblemsByTitle(searchInput);
        } else if (isSearchSelectValid(searchSelect)) {
            problems = problemService.findProblemsByRank(searchSelect, pageNumber, pageSize);
            totalPages = problemService.countProblemsByRank(searchSelect);
        } else {
            problems = problemService.findAllProblems(pageNumber, pageSize);
            totalPages = problemService.countAllProblems();
        }

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
    private boolean isSearchSelectValid(Rank searchSelect) {
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
    public ApiResponse<?> getProblemStandardFormat(
            @RequestParam(name = "problemId") Long problemId,
            @RequestParam(name = "language") String language) {
        Problem foundProblem = problemService.findProblemById(problemId);
        ProblemStandardFormat problemStandardFormat = problemService.findProblemStandardFormatByProblemAndLanguage(foundProblem, language);

        return ApiResponse.createSuccess(problemStandardFormat);
    }

    /**
     * 문제를 컴파일한다.
     *
     * @param problemAnswerRequestDTO 문제 답변 정보가 담긴 DTO
     * @return 컴파일 결과를 담은 응답
     * @throws FileDeleteException 파일 삭제 예외
     * @throws FolderDeleteException 폴더 삭제 예외
     */
    @PostMapping("/compile")
    public ApiResponse<?> compileProblem(@RequestBody ProblemAnswerRequestDTO problemAnswerRequestDTO, HttpServletRequest request) throws FileDeleteException, FolderDeleteException {
        Problem foundProblem = problemService.findProblemById(problemAnswerRequestDTO.getProblemId());
        Member foundMember = memberService.findMemberById((Long) request.getAttribute("memberId"));

        ProblemAnswer problemAnswer = ProblemAnswer.of(problemAnswerRequestDTO);
        problemService.doProblemCompile(problemAnswer, foundProblem, foundMember);
        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 문제의 제출 목록을 가져온다.
     *
     * @param problemId 문제 식별자
     * @param request HTTP 요청 객체
     * @return 문제에 대한 제출 목록을 담은 응답
     */
    @GetMapping("/answer")
    public ApiResponse<?> getProblemAnswers(@RequestParam(name = "problemId") Long problemId, HttpServletRequest request) {
        Problem foundProblem = problemService.findProblemById(problemId);
        Member foundMember = memberService.findMemberById((Long) request.getAttribute("memberId"));

        List<ProblemAnswer> problemAnswers = problemService.findAllProblemAnswersByProblemAndMember(foundProblem, foundMember);
        return ApiResponse.createSuccess(ProblemAnswerResponseDTO.listOf(problemAnswers));
    }
}