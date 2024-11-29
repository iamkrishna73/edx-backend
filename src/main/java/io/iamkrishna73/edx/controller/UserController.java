package io.iamkrishna73.edx.controller;

import io.iamkrishna73.edx.dtos.LoginFormDto;
import io.iamkrishna73.edx.dtos.SignUpDto;
import io.iamkrishna73.edx.dtos.UnlockFormDto;
import io.iamkrishna73.edx.dtos.response.LoginResponse;
import io.iamkrishna73.edx.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edx/auth")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginFormDto loginFormDto) {
        LoginResponse response = userService.login(loginFormDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        userService.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/unlock-account")
    public ResponseEntity<?> unlockAccount(@RequestBody UnlockFormDto unlockFormDto) {
        userService.unlockAccount(unlockFormDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> forgotPassword(String email) {
        return null;
    }
}
