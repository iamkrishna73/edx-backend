package io.iamkrishna73.edx.dtos;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String email;
    private Long phoneNumber;
}
