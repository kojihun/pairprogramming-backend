package com.develop.pairprogramming.dto.request;

import com.develop.pairprogramming.model.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDTO {
    private String email;
    private String password;
    private String name;
}
