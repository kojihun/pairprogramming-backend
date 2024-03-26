package com.develop.pairprogramming.model;

import com.develop.pairprogramming.dto.request.MemberRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;
    private String email;
    private String password;
    private String name;

    public Member() {
    }

    @Builder
    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Member of(MemberRequestDTO memberRequestDTO) {
        return Member.builder()
                .email(memberRequestDTO.getEmail())
                .password(memberRequestDTO.getPassword())
                .name(memberRequestDTO.getName())
                .build();
    }
}