package com.develop.pairprogramming.repository;

import com.develop.pairprogramming.model.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProblemRepository {
    private final EntityManager em;

    public List<Problem> findAllProblems(int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p", Problem.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Problem> findSearchedTitleProblems(String searchInput, int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p where p.title like concat('%', :searchInput, '%')", Problem.class)
                .setParameter("searchInput", searchInput)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Problem> findSearchedRankProblems(Rank searchSelect, int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p where p.rank = :searchSelect", Problem.class)
                .setParameter("searchSelect", searchSelect)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Problem> findSearchedRankAndTitleProblems(String searchInput, Rank searchSelect, int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p where p.title like concat('%', :searchInput, '%') and p.rank = :searchSelect", Problem.class)
                .setParameter("searchInput", searchInput)
                .setParameter("searchSelect", searchSelect)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long getTotalProblemsCount() {
        return em.createQuery("select count(p) from Problem p", Long.class)
                .getSingleResult();
    }

    public long getTotalSearchedTitleProblemsCount(String searchInput) {
        return em.createQuery("select count(p) from Problem p where p.title like concat('%', :searchInput, '%')", Long.class)
                .setParameter("searchInput", searchInput)
                .getSingleResult();
    }

    public long getTotalSearchedRankProblemsCount(Rank searchSelect) {
        return em.createQuery("select count(p) from Problem p where p.rank = :searchSelect", Long.class)
                .setParameter("searchSelect", searchSelect)
                .getSingleResult();
    }

    public long getTotalSearchedRankAndTitleProblemsCount(String searchInput, Rank searchSelect) {
        return em.createQuery("select count(p) from Problem p where p.title like concat('%', :searchInput, '%') and p.rank = :searchSelect", Long.class)
                .setParameter("searchInput", searchInput)
                .setParameter("searchSelect", searchSelect)
                .getSingleResult();
    }

    public Problem findProblemById(Long problemId) {
        return em.find(Problem.class, problemId);
    }

    public List<ProblemTestCase> findAllProblemTestCasesByProblemId(Problem problem) {
        return em.createQuery("select p from ProblemTestCase p where p.problem = :problem", ProblemTestCase.class)
                .setParameter("problem", problem)
                .getResultList();
    }

    public ProblemStandardFormat getProblemStandardFormat(Problem problem, String languageType) {
        return em.createQuery("select p from ProblemStandardFormat p where p.problem = :problem and p.languageType = :languageType", ProblemStandardFormat.class)
                .setParameter("problem", problem)
                .setParameter("languageType", languageType)
                .getSingleResult();
    }

    public void save(ProblemAnswer member) {
        em.persist(member);
    }
}