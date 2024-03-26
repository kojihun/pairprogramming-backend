package com.develop.pairprogramming.model;

import com.develop.pairprogramming.dto.request.MemberRequestDTO;
import jakarta.persistence.*;
import jnr.ffi.Struct;
import lombok.Builder;
import lombok.Getter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public void encryptPassword() {
        try {
            String salt = this.email;
            String password = this.password;
            String concatPassword = salt + password;

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(concatPassword.getBytes());
            byte[] bytesPassword = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (byte bytePassword : bytesPassword) {
                stringBuilder.append(String.format("%02x", bytePassword));
            }
            this.password = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}