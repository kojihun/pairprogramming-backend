package com.develop.pairprogramming.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDTO {
    private String email;
    private String password;
    private String name;
}