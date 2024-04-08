package com.develop.pairprogramming.repository;

import com.develop.pairprogramming.model.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProblemRepository {
    private final EntityManager em;

    /**
     * 모든 문제를 페이징하여 조회한다.
     *
     * @param pageNumber 페이지 번호 (1부터 시작)
     * @param pageSize 페이지당 문제 수
     * @return 페이지에 해당하는 문제 객체 리스트
     */
    public List<Problem> findAllProblems(int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p", Problem.class)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * 제목을 기준으로 검색한 후 페이징하여 문제를 조회한다.
     *
     * @param searchInput 검색어
     * @param pageNumber 페이지 번호 (1부터 시작)
     * @param pageSize 페이지당 문제 수
     * @return 검색된 제목에 해당하는 문제 객체 리스트
     */
    public List<Problem> findProblemsByTitle(String searchInput, int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p where p.title like concat('%', :searchInput, '%')", Problem.class)
                .setParameter("searchInput", searchInput)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * 등급을 기준으로 검색한 후 페이징하여 문제를 조회한다.
     *
     * @param searchSelect 등급
     * @param pageNumber 페이지 번호(1부터 시작)
     * @param pageSize 페이지당 문제 수
     * @return 검색된 등급에 해당하는 문제 객체의 리스트
     */
    public List<Problem> findProblemsByRank(ProblemRank searchSelect, int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p where p.rank = :searchSelect", Problem.class)
                .setParameter("searchSelect", searchSelect)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * 제목과 등급을 기준으로 검색한 후 페이징하여 문제를 조회한다.
     * 
     * @param searchInput 검색어
     * @param searchSelect 등급
     * @param pageNumber 페이지 번호(1부터 시작)
     * @param pageSize 페이지당 문제 수
     * @return 검색된 제목과 등급에 해당하는 문제 리스트
     */
    public List<Problem> findProblemsByTitleAndRank(String searchInput, ProblemRank searchSelect, int pageNumber, int pageSize) {
        return em.createQuery("select p from Problem p where p.title like concat('%', :searchInput, '%') and p.rank = :searchSelect", Problem.class)
                .setParameter("searchInput", searchInput)
                .setParameter("searchSelect", searchSelect)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * 전체 문제의 개수를 조회한다.
     * 
     * @return 전체 문제의 개수
     */
    public long countAllProblems() {
        return em.createQuery("select count(p) from Problem p", Long.class)
                .getSingleResult();
    }

    /**
     * 제목으로 검색된 문제의 총 개수를 조회한다.
     * 
     * @param searchInput 검색어
     * @return 검색된 제목에 해당하는 문제의 총 개수
     */
    public long countProblemsByTitle(String searchInput) {
        return em.createQuery("select count(p) from Problem p where p.title like concat('%', :searchInput, '%')", Long.class)
                .setParameter("searchInput", searchInput)
                .getSingleResult();
    }

    /**
     * 등급을 기준으로 검색된 문제의 총 개수를 조회한다.
     *
     * @param searchSelect 등급
     * @return 검색된 등급에 해당하는 문제의 총 개수
     */
    public long countProblemsByRank(ProblemRank searchSelect) {
        return em.createQuery("select count(p) from Problem p where p.rank = :searchSelect", Long.class)
                .setParameter("searchSelect", searchSelect)
                .getSingleResult();
    }

    /**
     * 제목과 등급을 기준으로 검색된 문제의 총 개수를 조회한다.
     * 
     * @param searchInput 검색어
     * @param searchSelect 등급
     * @return 검색된 제목과 등급에 해당하는 문제의 총 개수
     */
    public long countProblemsByTitleAndRank(String searchInput, ProblemRank searchSelect) {
        return em.createQuery("select count(p) from Problem p where p.title like concat('%', :searchInput, '%') and p.rank = :searchSelect", Long.class)
                .setParameter("searchInput", searchInput)
                .setParameter("searchSelect", searchSelect)
                .getSingleResult();
    }

    /**
     * 문제 id에 해당하는 문제를 조회한다.
     *
     * @param problemId 문제 id
     * @return 문제 id에 해당하는 문제 객체
     */
    public Problem findProblemById(Long problemId) {
        return em.find(Problem.class, problemId);
    }

    /**
     * 주어진 문제에 대한 모든 테스트 케이스를 조회한다.
     *
     * @param problem 문제 객체
     * @return 주어진 문제에 대한 모든 테스트 케이스 리스트
     */
    public List<ProblemTestCase> findAllProblemTestCasesByProblem(Problem problem) {
        return em.createQuery("select p from ProblemTestCase p where p.problem = :problem", ProblemTestCase.class)
                .setParameter("problem", problem)
                .getResultList();
    }

    /**
     * 주어진 문제와 언어 유형에 해당하는 표준 형식을 조회한다.
     *
     * @param problem 문제 객체
     * @param language 언어유형
     * @return 주어진 문제와 언어 유형에 해당하는 표준 형식 객체
     */
    public ProblemFormat findProblemFormatByProblemAndLanguage(Problem problem, ProblemLanguage language) {
        return em.createQuery("select p from ProblemFormat p where p.problem = :problem and p.language = :language", ProblemFormat.class)
                .setParameter("problem", problem)
                .setParameter("language", language)
                .getSingleResult();
    }

    public List<ProblemAnswer> findProblemAnswerByProblemAndLanguageAndMember(Problem problem, ProblemLanguage language, Member member) {
        return em.createQuery("select p from ProblemAnswer p where p.problem = :problem and p.language = :language and p.member = :member", ProblemAnswer.class)
                .setParameter("problem", problem)
                .setParameter("language", language)
                .setParameter("member", member)
                .getResultList();
    }

    public List<ProblemAnswer> findProblemAnswerByProblemAndLanguageAndUuid(Problem problem, ProblemLanguage language, UUID uuid) {
        return em.createQuery("select p from ProblemAnswer p where p.problem = :problem and p.language = :language and p.uuid = :uuid", ProblemAnswer.class)
                .setParameter("problem", problem)
                .setParameter("language", language)
                .setParameter("uuid", uuid)
                .getResultList();
    }

    public List<ProblemAnswerSubmit> findAllProblemAnswerSubmitsByProblemAndMember(Problem problem, Member member) {
        return em.createQuery("select p from ProblemAnswerSubmit p where p.problem = :problem and p.member = :member", ProblemAnswerSubmit.class)
                .setParameter("problem", problem)
                .setParameter("member", member)
                .getResultList();
    }

    public List<ProblemAnswerSubmit> findAllProblemAnswerSubmitsByProblemAndUuid(Problem problem, UUID uuid) {
        return em.createQuery("select p from ProblemAnswerSubmit p where p.problem = :problem and p.uuid = :uuid", ProblemAnswerSubmit.class)
                .setParameter("problem", problem)
                .setParameter("uuid", uuid)
                .getResultList();
    }

    /**
     * 문제 답안을 저장한다.
     * 
     * @param problemAnswer 문제 답안 객체
     */
    public void saveProblemAnswer(ProblemAnswer problemAnswer) {
        em.persist(problemAnswer);
    }

    public void saveProblemAnswerSubmit(ProblemAnswerSubmit problemAnswerSubmit) {
        em.persist(problemAnswerSubmit);
    }

    /**
     * 주어진 문제와 회원에 대한 제출 목록을 조회한다.
     *
     * @param problem 문제 객체
     * @param member 회원 객체
     * @return 회원이 제출한 답안 리스트
     */
    public List<ProblemAnswer> findAllProblemAnswersByProblemAndMember(Problem problem, Member member) {
        return em.createQuery("select p from ProblemAnswer p where p.problem = :problem and p.member = :member", ProblemAnswer.class)
                .setParameter("problem", problem)
                .setParameter("member", member)
                .getResultList();
    }
}