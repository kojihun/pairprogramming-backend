package com.develop.pairprogramming.dto.response;

import com.develop.pairprogramming.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignupResponseDTO {
    private String email;
    private String name;

    public MemberSignupResponseDTO() {
    }

    @Builder
    public MemberSignupResponseDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static MemberSignupResponseDTO of(Member member) {
        return MemberSignupResponseDTO.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}