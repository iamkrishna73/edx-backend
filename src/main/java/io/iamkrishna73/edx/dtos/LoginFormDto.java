package io.iamkrishna73.edx.dtos;

import lombok.Data;

@Data
public class LoginFormDto {
    private String username;
    private String password;
    private String email;
}
