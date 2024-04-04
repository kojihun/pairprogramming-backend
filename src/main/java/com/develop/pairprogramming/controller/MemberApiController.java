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
     * 회원가입을 처리한다.
     *
     * @param memberRequestDTO 회원가입을 위한 정보가 담긴 DTO
     * @return 회원가입 처리 결과를 담은 응답
     */
    @PostMapping("/signup")
    public ApiResponse<MemberSignupResponseDTO> signup(@RequestBody MemberRequestDTO memberRequestDTO) {
        Member newMember = Member.of(memberRequestDTO);
        memberService.signup(newMember);

        return ApiResponse.createSuccess(MemberSignupResponseDTO.of(newMember));
    }

    /**
     * 이메일 중복 여부를 확인한다.
     *
     * @param memberRequestDTO 중복 여부를 확인할 이메일이 담긴 DTO
     * @return 중복 여부 확인 결과를 담은 응답
     */
    @PostMapping("/validation")
    public ApiResponse<?> validateEmailDuplicate(@RequestBody MemberRequestDTO memberRequestDTO) {
        memberService.validateEmailDuplicate(memberRequestDTO.getEmail());

        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 회원 로그인을 처리한다.
     *
     * @param memberRequestDTO 이메일과 비밀번호가 포함된 DTO
     * @return 회원 로그인 처리 결과를 담은 응답
     */
    @PostMapping("/signin")
    public ApiResponse<MemberSigninResponseDTO> signin(@RequestBody MemberRequestDTO memberRequestDTO) {
        Member loggedInMember = memberService.signin(Member.of(memberRequestDTO));
        String accessToken = jwtTokenProvider.generateAccessToken(loggedInMember);

        return ApiResponse.createSuccess(MemberSigninResponseDTO.of(loggedInMember, accessToken));
    }

    /**
     * 회원 로그아웃을 처리한다.
     *
     * @param request HTTP 요청 객체
     * @return 회원 로그아웃 처리 결과를 담은 응답
     */
    @PostMapping("/signout")
    public ApiResponse<?> signout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();

        return ApiResponse.createSuccessWithNoContent();
    }

    /**
     * 클라이언트 마운트 시 토큰의 유효성을 확인
     * 
     * @return 토큰 유효성 확인 결과를 담은 응답
     */
    @GetMapping("/token")
    public ApiResponse<?> checkTokenValidity() {
        return ApiResponse.createSuccessWithNoContent();
    }
}
