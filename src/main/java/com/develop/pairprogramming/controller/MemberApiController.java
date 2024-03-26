package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.dto.request.MemberRequestDTO;
import com.develop.pairprogramming.dto.response.MemberSignupResponseDTO;
import com.develop.pairprogramming.model.Member;
import com.develop.pairprogramming.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원가입
     *
     * @param memberRequestDTO 회원가입을 위한 정보가 담긴 DTO
     */
    @PostMapping("/signup")
    public ApiResponse<MemberSignupResponseDTO> signup(@RequestBody MemberRequestDTO memberRequestDTO) {
        Member member = Member.of(memberRequestDTO);
        memberService.signup(member);

        return ApiResponse.createSuccess(MemberSignupResponseDTO.of(member));
    }

    /**
     * 이메일 유효성 검증
     *
     * @param email 유효성 검증을 위한 이메일
     */
    @PostMapping("/validation")
    public ApiResponse<?> validateDuplicateEmail(@RequestBody String email) {
        memberService.validateDuplicateEmail(email);

        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 로그인
     *
     * @param request HttpServletRequest
     * @param memberRequestDTO email과 password가 있는 DTO
     */
    @PostMapping("/signin")
    public ApiResponse<?> signin(HttpServletRequest request, @RequestBody MemberRequestDTO memberRequestDTO) {
        // TODO 세션 처리과정 추가 필요
        Member member = Member.of(memberRequestDTO);
        memberService.signin(member);

        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 로그아웃
     *
     */
    @PostMapping("/signout")
    public ApiResponse<?> signout() {
        // TODO 세션 삭제과정 추가 필요
        return ApiResponse.createSuccessWithNoContent();
    }
}
