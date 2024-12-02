package io.iamkrishna73.edx.dtos.response;

import lombok.Data;

@Data
public class LoginResponse {
   private Integer userId;
   private String username;
   private String email;

}
