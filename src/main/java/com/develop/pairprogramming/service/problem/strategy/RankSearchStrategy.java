package com.develop.pairprogramming.service.problem.strategy;

import com.develop.pairprogramming.model.Problem;
import com.develop.pairprogramming.model.ProblemRank;
import com.develop.pairprogramming.service.ProblemService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RankSearchStrategy implements ProblemSearchStrategy {
    private final ProblemService problemService;
    private final ProblemRank searchSelect;

    @Override
    public List<Problem> findProblems(int pageNumber, int pageSize) {
        return problemService.findProblemsByRank(searchSelect, pageNumber, pageSize);
    }

    @Override
    public long countProblems() {
        return problemService.countProblemsByRank(searchSelect);
    }
}