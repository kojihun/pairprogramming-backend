package com.develop.pairprogramming.service.problem.strategy;

import com.develop.pairprogramming.model.Problem;

import java.util.List;

public interface ProblemSearchStrategy {
    List<Problem> findProblems(int pageNumber, int pageSize);
    long countProblems();
}
