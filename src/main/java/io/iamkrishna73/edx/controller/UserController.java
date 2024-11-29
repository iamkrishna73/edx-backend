package io.iamkrishna73.edx.controller;

import io.iamkrishna73.edx.constant.AppConstant;
import io.iamkrishna73.edx.dtos.LoginFormDto;
import io.iamkrishna73.edx.dtos.SignUpDto;
import io.iamkrishna73.edx.dtos.UnlockFormDto;
import io.iamkrishna73.edx.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/edx/auth")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginFormDto loginFormDto) {
        String response = userService.login(loginFormDto);
       // var data = response.contains(AppConstant.ACCOUNT_SUCCESS_STATUS);
        // System.out.println(data);
        if(!Objects.equals(response, AppConstant.ACCOUNT_SUCCESS_STATUS)) {
            System.out.println("hello jjs");
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
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
