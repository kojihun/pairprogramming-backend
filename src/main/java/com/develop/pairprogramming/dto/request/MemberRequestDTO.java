package com.develop.pairprogramming.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberRequestDTO {
    private String email;
    private String password;
    private String name;
}