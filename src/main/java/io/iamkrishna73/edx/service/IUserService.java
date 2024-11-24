package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.dtos.LoginFormDto;
import io.iamkrishna73.edx.dtos.SignUpDto;
import io.iamkrishna73.edx.dtos.UnlockFormDto;

public interface IUserService {
    String login(LoginFormDto loginFormDto);
    void signUp(SignUpDto signUpFromDto);
    void unlockAccount(UnlockFormDto formDto);
    String forgetPassword(String email);
}
