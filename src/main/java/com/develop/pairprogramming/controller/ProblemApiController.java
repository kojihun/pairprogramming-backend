package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.dto.response.ProblemDetailResponseDTO;
import com.develop.pairprogramming.dto.response.ProblemListResponseDTO;
import com.develop.pairprogramming.dto.response.ProblemResponseDTO;
import com.develop.pairprogramming.model.Problem;
import com.develop.pairprogramming.model.Rank;
import com.develop.pairprogramming.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/problems")
@RequiredArgsConstructor
@RestController
public class ProblemApiController {
    private final ProblemService problemService;

    /**
     * 모든 문제를 조회하는 엔드포인트
     *
     * @param pageNumber   페이지 번호 (기본값: 1)
     * @param pageSize     페이지 크기 (기본값: 10)
     * @param searchInput  검색어 (선택 사항)
     * @param searchSelect 검색 옵션 (선택 사항)
     * @return ApiResponse<ProblemResponseDTO> 문제 조회 결과를 ApiResponse로 반환
     */
    @GetMapping("/all")
    public ApiResponse<ProblemResponseDTO> getAllProblems(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "searchInput", required = false) String searchInput,
            @RequestParam(name = "searchSelect", required = false) Rank searchSelect) {
        List<Problem> problems;
        long totalPages;

        if (isValidSearchInput(searchInput) && isValidSearchSelect(searchSelect)) {
            problems = problemService.findSearchedRankAndTitleProblems(searchInput, searchSelect, pageNumber, pageSize);
            totalPages = problemService.getTotalSearchedRankAndTitleProblemsCount(searchInput, searchSelect);
        } else if (isValidSearchInput(searchInput)) {
            problems = problemService.findSearchedTitleProblems(searchInput, pageNumber, pageSize);
            totalPages = problemService.getTotalSearchedTitleProblemsCount(searchInput);
        } else if (isValidSearchSelect(searchSelect)) {
            problems = problemService.findSearchedRankProblems(searchSelect, pageNumber, pageSize);
            totalPages = problemService.getTotalSearchedRankProblemsCount(searchSelect);
        } else {
            problems = problemService.findAllProblems(pageNumber, pageSize);
            totalPages = problemService.getTotalProblemsCount();
        }

        return ApiResponse.createSuccess(ProblemResponseDTO.of(ProblemListResponseDTO.listOf(problems), totalPages));
    }

    /**
     * 검색어의 유효성을 확인하는 메서드
     *
     * @param searchInput 검색어
     * @return boolean 검색어가 유효한지 여부를 반환
     */
    private boolean isValidSearchInput(String searchInput) {
        return searchInput != null && !searchInput.isEmpty();
    }

    /**
     * 등급의 유효성을 확인하는 메서드
     *
     * @param searchSelect 등급
     * @return boolean 등급이 유효한지 여부를 반환
     */
    private boolean isValidSearchSelect(Rank searchSelect) {
        return searchSelect != null;
    }

    /**
     * 문제 세부 정보를 가져오는 메서드
     *
     * @param problemId 문제 식별자
     * @return 문제 세부 정보를 포함한 ApiResponse 객체
     */
    @GetMapping("/detail")
    public ApiResponse<?> getProblemDetail(@RequestParam(name = "problemId") Long problemId) {
        Problem findProblem = problemService.findProblemById(problemId);

        return ApiResponse.createSuccess(ProblemDetailResponseDTO.of(findProblem));
    }
}