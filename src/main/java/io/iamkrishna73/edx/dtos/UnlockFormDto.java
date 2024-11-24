package io.iamkrishna73.edx.dtos;

import lombok.Data;

@Data
public class UnlockFormDto {
    private String email;
    private String tempPassword;
    private String password;
    private String confirmPassword;
}
