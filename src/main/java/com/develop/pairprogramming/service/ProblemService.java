package com.develop.pairprogramming.service;

import com.develop.pairprogramming.model.Problem;
import com.develop.pairprogramming.model.Rank;
import com.develop.pairprogramming.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProblemService {
    private final ProblemRepository problemRepository;

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
    public List<Problem> findSearchedTitleProblems(String searchInput, int pageNumber, int pageSize) {
        return problemRepository.findSearchedTitleProblems(searchInput, pageNumber, pageSize);
    }

    /**
     * 등급으로 검색된 문제를 조회한다.
     *
     * @param searchSelect 등급
     * @param pageNumber   페이지 번호
     * @param pageSize     페이지 크기
     * @return 검색된 문제 목록을 반환한다.
     */
    public List<Problem> findSearchedRankProblems(Rank searchSelect, int pageNumber, int pageSize) {
        return problemRepository.findSearchedRankProblems(searchSelect, pageNumber, pageSize);
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
    public List<Problem> findSearchedRankAndTitleProblems(String searchInput, Rank searchSelect, int pageNumber, int pageSize) {
        return problemRepository.findSearchedRankAndTitleProblems(searchInput, searchSelect, pageNumber, pageSize);
    }

    /**
     * 전체 문제 수를 반환한다.
     *
     * @return 전체 문제 수
     */
    public long getTotalProblemsCount() {
        return problemRepository.getTotalProblemsCount();
    }

    /**
     * 제목으로 검색된 문제 수를 반환한다.
     *
     * @param searchInput 검색어
     * @return 검색된 문제 수
     */
    public long getTotalSearchedTitleProblemsCount(String searchInput) {
        return problemRepository.getTotalSearchedTitleProblemsCount(searchInput);
    }

    /**
     * 등급으로 조회된 문제 수를 반환한다.
     *
     * @param searchSelect 등급
     * @return 조회된 문제 수
     */
    public long getTotalSearchedRankProblemsCount(Rank searchSelect) {
        return problemRepository.getTotalSearchedRankProblemsCount(searchSelect);
    }

    /**
     * 등급과 제목으로 검색된 문제 수를 반환한다.
     *
     * @param searchInput  검색어
     * @param searchSelect 등급
     * @return 검색된 문제 수
     */
    public long getTotalSearchedRankAndTitleProblemsCount(String searchInput, Rank searchSelect) {
        return problemRepository.getTotalSearchedRankAndTitleProblemsCount(searchInput, searchSelect);
    }

    public Problem findProblemById(Long problemId) {
        return problemRepository.findProblemById(problemId);
    }
}