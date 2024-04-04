package com.develop.pairprogramming.repository;

import com.develop.pairprogramming.model.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    /**
     * 회원을 저장한다.
     * 
     * @param member 저장할 회원 객체
     */
    public void save(Member member) {
        em.persist(member);
    }

    /**
     * 회원 id에 해당하는 회원을 찾는다.
     *
     * @param memberId 검색할 회원의 id
     * @return 회원 id에 해당하는 회원 객체
     */
    public Member findMemberById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    /**
     * 이메일에 해당하는 회원을 찾는다.
     *
     * @param email 검색할 회원의 이메일
     * @return 이메일에 해당하는 회원 목록
     */
    public List<Member> findMemberByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    /**
     * 이메일과 비밀번호에 해당하는 회원을 찾는다.
     *
     * @param email 검색할 회원의 이메일
     * @param password 검색할 회원의 비밀번호
     * @return 이메일과 비밀번호에 해당하는 회원 목록
     */
    public List<Member> findMemberByEmailAndPassword(String email, String password) {
        return em.createQuery("select m from Member m where m.email = :email and m.password = :password", Member.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
    }

    /**
     * 모든 회원을 조회한다.
     * 
     * @return 모든 회원의 객체 리스트
     */
    public List<Member> findAllMembers() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}