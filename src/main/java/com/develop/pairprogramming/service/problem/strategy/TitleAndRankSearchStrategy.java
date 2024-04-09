package com.develop.pairprogramming.service.problem.strategy;

import com.develop.pairprogramming.model.Problem;
import com.develop.pairprogramming.model.ProblemRank;
import com.develop.pairprogramming.service.ProblemService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TitleAndRankSearchStrategy implements ProblemSearchStrategy {
    private final ProblemService problemService;
    private final String searchInput;
    private final ProblemRank searchSelect;

    @Override
    public List<Problem> findProblems(int pageNumber, int pageSize) {
        return problemService.findProblemsByTitleAndRank(searchInput, searchSelect, pageNumber, pageSize);
    }

    @Override
    public long countProblems() {
        return problemService.countProblemsByTitleAndRank(searchInput, searchSelect);
    }
}