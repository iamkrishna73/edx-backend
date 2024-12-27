package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.constant.AppConstant;
import io.iamkrishna73.edx.constant.LoggingConstant;
import io.iamkrishna73.edx.dtos.LoginFormDto;
import io.iamkrishna73.edx.dtos.SignUpDto;
import io.iamkrishna73.edx.dtos.UnlockFormDto;
import io.iamkrishna73.edx.dtos.response.LoginResponse;
import io.iamkrishna73.edx.entities.UserDetailsEntity;
import io.iamkrishna73.edx.exception.ResourceNotFoundException;
import io.iamkrishna73.edx.repos.UserDetailsRepository;
import io.iamkrishna73.edx.utils.EmailUtils;
import io.iamkrishna73.edx.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements IUserService {
    private final UserDetailsRepository userDetailsRepository;
    private final EmailUtils emailUtils;

    @Value("${frontend.url.unlockAccount}")
    private String unlockAccountUrl;


    public UserService(UserDetailsRepository userDetailsRepository, EmailUtils emailUtils) {
        this.userDetailsRepository = userDetailsRepository;
        this.emailUtils = emailUtils;
    }

    @Override
    public LoginResponse login(LoginFormDto loginFormDto) {
        var methodName = "login:UserService";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, loginFormDto.getEmail());
        UserDetailsEntity userDetails = userDetailsRepository.findByEmail(loginFormDto.getEmail()).orElseThrow(() -> {
                    log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "User not found");
                    throw new ResourceNotFoundException("User not found");
                }
        );
        if (userDetails.getAccountStatus().equals(AppConstant.ACCOUNT_LOCKED_STATUS)) {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "User is locked");
            throw new ResourceNotFoundException("Account is locked");
            //return AppConstant.ACCOUNT_FAILURE_STATUS;
        }
        LoginResponse loginResponse = new LoginResponse();
        if (userDetails.getEmail().equals(loginFormDto.getEmail()) && userDetails.getPassword().equals(loginFormDto.getPassword())) {
           loginResponse.setUserId(userDetails.getId());
            loginResponse.setUsername(userDetails.getUsername());
            loginResponse.setEmail(userDetails.getEmail());
        }
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
        return loginResponse;
    }

    @Override
    public void signUp(SignUpDto signUpDto) {
        var methodName = "UserService:signUp";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, signUpDto.getEmail());
        UserDetailsEntity userDetails = new UserDetailsEntity();
        if (userDetailsRepository.existsByEmail(signUpDto.getEmail())) {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, " Email already exists");
            throw new ResourceNotFoundException("Email already exists in database!");
        }
        userDetails.setUsername(signUpDto.getName());
        userDetails.setEmail(signUpDto.getEmail());
        userDetails.setPhoneNumber(signUpDto.getPhoneNumber());

        String tempPassword = PasswordUtils.generateRandomPassword();
        userDetails.setPassword(tempPassword);

        userDetails.setAccountStatus(AppConstant.ACCOUNT_LOCKED_STATUS);

        userDetailsRepository.save(userDetails);

        String to = signUpDto.getEmail();
        StringBuilder body = AppConstant.generateEmailBodyForUnlockPassword(tempPassword, signUpDto.getEmail(), unlockAccountUrl, signUpDto.getName());
        String subject = AppConstant.UNLOCK_PASSWORD_SUBJECT;
        emailUtils.sendEmail(to, subject, body.toString());
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
    }


    @Override
    public void unlockAccount(UnlockFormDto unlockFormDto) {
        var methodName = "UserService:unlockAccount";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, " Unlocking account" + unlockFormDto.getEmail());

        UserDetailsEntity userDetails = userDetailsRepository.findByEmail(unlockFormDto.getEmail()).orElseThrow(() -> {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "Cannot Unlock account because user does not exists");
            throw new ResourceNotFoundException("Cannot Unlock account because user does not exists");
        });
        //  System.out.println(userDetails);
        if (userDetails.getAccountStatus().equals(AppConstant.ACCOUNT_UNLOCKED_STATUS)) {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "Account is already unlocked");
            throw new ResourceNotFoundException("Account already unlocked!");

        }
        if (!unlockFormDto.getTempPassword().equals(userDetails.getPassword())) {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "Temporary password does not match");
            throw new ResourceNotFoundException("Temporary Password does not match with database!");
        }
        if (!unlockFormDto.getPassword().equals(unlockFormDto.getConfirmPassword())) {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "Password confirmation does not match");
            throw new ResourceNotFoundException("Password and confirm password do not match!");
        }
        userDetails.setAccountStatus(AppConstant.ACCOUNT_UNLOCKED_STATUS);
        userDetails.setPassword(unlockFormDto.getPassword());
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
        userDetailsRepository.save(userDetails);
    }

    @Override
    public void forgetPassword(String email) {
        var methodName = "UserService:forgetPassword";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, " Forgetting password");
        UserDetailsEntity userDetails = userDetailsRepository.findByEmail(email).orElseThrow(() -> {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "User not found");
            throw new ResourceNotFoundException("User is not registered provided email address");
        });
        String subject = AppConstant.RECOVER_PASSWORD_EMAIL_SUBJECT;
        String password = userDetails.getPassword();
        StringBuilder body = AppConstant.generateEmailBodyForForgetPassword(password, userDetails.getUsername());

        emailUtils.sendEmail(email, subject, body.toString());
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
    }
}
