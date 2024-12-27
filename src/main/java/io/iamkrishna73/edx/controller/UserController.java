package io.iamkrishna73.edx.controller;

import io.iamkrishna73.edx.constant.LoggingConstant;
import io.iamkrishna73.edx.dtos.LoginFormDto;
import io.iamkrishna73.edx.dtos.SignUpDto;
import io.iamkrishna73.edx.dtos.UnlockFormDto;
import io.iamkrishna73.edx.dtos.response.LoginResponse;
import io.iamkrishna73.edx.service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/edx/auth")
public class UserController {
    private final IUserService userService;
    private HttpSession session;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginFormDto loginFormDto) {
        var methodName = "loginUser:UserController";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, loginFormDto.getEmail());
        LoginResponse response = userService.login(loginFormDto);
        log.info(LoggingConstant.END_METHOD_LOG, methodName, response.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        var methodName = "registerUser:UserController";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, signUpDto.getEmail());
        userService.signUp(signUpDto);
        log.info(LoggingConstant.END_METHOD_LOG, methodName, signUpDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/unlock-account")
    public ResponseEntity<?> unlockAccount(@RequestBody UnlockFormDto unlockFormDto) {
        var methodName = "unlockAccount:UserController";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, unlockFormDto.getEmail());
        userService.unlockAccount(unlockFormDto);
        log.info(LoggingConstant.END_METHOD_LOG, methodName, unlockFormDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/forget-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        var methodName = "forgotPassword:UserController";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, email);
        userService.forgetPassword(email);
        log.info(LoggingConstant.END_METHOD_LOG, methodName, email);
        return new ResponseEntity<>("Please check your email to get your password", HttpStatus.OK);
    }
}
