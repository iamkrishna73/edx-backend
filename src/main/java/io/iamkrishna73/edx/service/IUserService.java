package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.dtos.LoginFormDto;
import io.iamkrishna73.edx.dtos.SignUpDto;
import io.iamkrishna73.edx.dtos.UnlockFormDto;
import io.iamkrishna73.edx.dtos.response.LoginResponse;

public interface IUserService {
    LoginResponse login(LoginFormDto loginFormDto);
    void signUp(SignUpDto signUpFromDto);
    void unlockAccount(UnlockFormDto formDto);
    String forgetPassword(String email);
}
