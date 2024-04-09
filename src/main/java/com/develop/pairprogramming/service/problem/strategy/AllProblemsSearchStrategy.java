package com.develop.pairprogramming.service.problem.strategy;

import com.develop.pairprogramming.model.Problem;
import com.develop.pairprogramming.service.ProblemService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AllProblemsSearchStrategy implements ProblemSearchStrategy {
    private final ProblemService problemService;
    @Override
    public List<Problem> findProblems(int pageNumber, int pageSize) {
        return problemService.findAllProblems(pageNumber, pageSize);
    }

    @Override
    public long countProblems() {
        return problemService.countAllProblems();
    }
}