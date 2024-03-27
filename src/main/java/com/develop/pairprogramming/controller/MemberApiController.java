package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.config.JwtTokenProvider;
import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.dto.request.MemberRequestDTO;
import com.develop.pairprogramming.dto.response.MemberSigninResponseDTO;
import com.develop.pairprogramming.dto.response.MemberSignupResponseDTO;
import com.develop.pairprogramming.model.Member;
import com.develop.pairprogramming.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

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
     * @param memberRequestDTO 유효성 검증을 위한 이메일
     */
    @PostMapping("/validation")
    public ApiResponse<?> validateDuplicateEmail(@RequestBody MemberRequestDTO memberRequestDTO) {
        memberService.validateDuplicateEmail(memberRequestDTO.getEmail());

        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 로그인
     *
     * @param memberRequestDTO email과 password가 있는 DTO
     */
    @PostMapping("/signin")
    public ApiResponse<MemberSigninResponseDTO> signin(@RequestBody MemberRequestDTO memberRequestDTO) {
        Member member = Member.of(memberRequestDTO);
        Member loginedMember = memberService.signin(member);
        String accessToken = jwtTokenProvider.generateToken(loginedMember);

        return ApiResponse.createSuccess(MemberSigninResponseDTO.of(loginedMember, accessToken));
    }

    /**
     * 로그아웃
     *
     */
    @PostMapping("/signout")
    public ApiResponse<?> signout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();

        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 화면 마운트시 토큰 유효여부 확인
     */
    @GetMapping("/token")
    public ApiResponse<?> validateToken() {
        return ApiResponse.createSuccessWithNoContent();
    }
}
