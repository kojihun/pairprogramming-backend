package com.develop.pairprogramming.dto.response;

import com.develop.pairprogramming.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSigninResponseDTO {
    private String email;
    private String name;
    private String accessToken;

    public MemberSigninResponseDTO() {

    }

    @Builder
    public MemberSigninResponseDTO(String email, String name, String accessToken) {
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
    }

    public static MemberSigninResponseDTO of(Member member, String accessToken) {
        return MemberSigninResponseDTO.builder()
                .email(member.getEmail())
                .name(member.getName())
                .accessToken(accessToken)
                .build();
    }
}