package com.develop.pairprogramming.service;

import com.develop.pairprogramming.exception.DuplicateEmailException;
import com.develop.pairprogramming.exception.NotFoundMemberException;
import com.develop.pairprogramming.model.Member;
import com.develop.pairprogramming.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    /**
     * 1. 생성자 주입 방식
     * 2. 변경 불가능한 안전한 객체 생성가능
     * 3. final 키워드를 추가하면 컴파일 시점에 memberRepository를 설정하지 않는 오류를 체크할 수 있다.
     */
    private final MemberRepository memberRepository;

    /**
     * 회원가입을 한다.
     *
     * @param member 저장을 할 Member 객체
     * @return memberId 회원가입을 한 회원의 id를 리턴한다.
     */
    @Transactional
    public Long signup(Member member) {
        memberRepository.save(member);
        return member.getMemberId();
    }

    /**
     * 유효한 이메일인지 검증한다.
     * 
     * @param email 중복 이메일인지 확인하기 위한 변수
     */
    public void validateDuplicateEmail(String email) {
        List<Member> findMembers = memberRepository.findMemberByEmail(email);
        if (!findMembers.isEmpty()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }
    }

    /**
     * 로그인을 한다.
     *
     * @param member 로그인을 할 Member 객체
     */
    public Member signin(Member member) {
        String email = member.getEmail();
        String password = member.getPassword();

        List<Member> findMembers = memberRepository.findMemberByEmailAndPassword(email, password);
        if (!findMembers.isEmpty()) {
            throw new NotFoundMemberException("아이디 또는 비밀번호 오류입니다.");
        }

        return findMembers.get(0);
    }

    /**
     * 전체 회원을 조회한다.
     *
     * @return List<Member> 전체 회원 목록을 반환한다.
     */
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * memberId로 회원을 조회한다.
     *
     * @param memberId 회원읠 조회하기 위한 id
     * @return Member 회원 객체를 반환한다.
     */
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}