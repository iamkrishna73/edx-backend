package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.constant.EmailConstant;
import io.iamkrishna73.edx.constant.LoggingConstant;
import io.iamkrishna73.edx.dtos.LoginFormDto;
import io.iamkrishna73.edx.dtos.SignUpDto;
import io.iamkrishna73.edx.dtos.UnlockFormDto;
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
    @Value("${frontend.url.unlockAccount}")
    private String unlockAccountUrl;
    private final UserDetailsRepository userDetailsRepository;
    private final EmailUtils emailUtils;

    public UserService(UserDetailsRepository userDetailsRepository, EmailUtils emailUtils) {
        this.userDetailsRepository = userDetailsRepository;
        this.emailUtils = emailUtils;
    }

    @Override
    public String login(LoginFormDto loginFormDto) {
        return null;
    }

    @Override
    public void signUp(SignUpDto signUpDto) {
        var methodName = "UserService:signUp";
        UserDetailsEntity userDetails = new UserDetailsEntity();
        userDetails.setUsername(signUpDto.getName());
        if (userDetailsRepository.existsByEmail(signUpDto.getEmail())) {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, " Email already exists");
            throw new ResourceNotFoundException("Email already exists in database!");

        }
        userDetails.setEmail(signUpDto.getEmail());
        userDetails.setPhoneNumber(signUpDto.getPhoneNumber());

        String tempPassword = PasswordUtils.generateRandomPassword();
        userDetails.setPassword(tempPassword);

        userDetails.setAccountStatus("LOCKED");

        userDetailsRepository.save(userDetails);

        String to = signUpDto.getEmail();
          StringBuilder body = generateEmailBody(tempPassword, signUpDto.getEmail());


        emailUtils.sendEmail(to, EmailConstant.subject, body.toString());
    }

    private StringBuilder generateEmailBody(String tempPassword, String email) {
        StringBuilder body = new StringBuilder();
        body.append("<html>"); // Opening HTML tag
        body.append("<body>"); // Opening body tag
        body.append("<p>Dear User,</p>"); // Salutation
        body.append("<p>Thank you for registering with us! Please use the temporary password below to unlock your account:</p>"); // Message
        body.append("<p><b>Temporary Password:</b> " + tempPassword + "</p>"); // Temporary password
        body.append("<p>Click the link below to unlock your account:</p>"); // Instruction
        body.append("<a href='" + unlockAccountUrl + "?email=" + email + "'>Unlock Account</a>"); // Clickable link
        body.append("</body>"); // Closing body tag
        body.append("</html>"); // Closing HTML tag
        return body;
    }

    @Override
    public void unlockAccount(UnlockFormDto unlockFormDto) {
        var methodName = "UserService:unlockAccount";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, " Unlocking account" + unlockFormDto.getEmail());
        System.out.println("Hello");
        System.out.println(unlockFormDto);

        UserDetailsEntity userDetails = userDetailsRepository.findByEmail(unlockFormDto.getEmail());
        System.out.println(userDetails);
        if (userDetails.getAccountStatus().equals("UNLOCKED"))  {
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
        userDetails.setAccountStatus("UNLOCKED");
        userDetails.setPassword(unlockFormDto.getPassword());
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
        userDetailsRepository.save(userDetails);
    }

    @Override
    public String forgetPassword(String email) {
        return null;
    }
}
