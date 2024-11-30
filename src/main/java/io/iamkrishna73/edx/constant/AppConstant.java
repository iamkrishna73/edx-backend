package io.iamkrishna73.edx.constant;

public class AppConstant {
    public static final String UNLOCK_PASSWORD_SUBJECT = "Unlock Your Account";
    public static final String ACCOUNT_LOCKED_STATUS = "LOCKED";
    public static final String ACCOUNT_UNLOCKED_STATUS = "UNLOCKED";
    public static final String RECOVER_PASSWORD_EMAIL_SUBJECT = "Password Recover Email";

    public static StringBuilder generateEmailBodyForUnlockPassword(String tempPassword, String email, String unlockAccountUrl, String name) {
        StringBuilder body = new StringBuilder();
        body.append("<html>"); // Opening HTML tag
        body.append("<body>"); // Opening body tag
        body.append("<p>Hi " + name + ",</p>"); // Salutation
        body.append("<p>Thank you for registering with us! Please use the temporary password below to unlock your account:</p>"); // Message
        body.append("<p><b>Temporary Password:</b> " + tempPassword + "</p>"); // Temporary password
        body.append("<p>Click the link below to unlock your account:</p>"); // Instruction
        body.append("<a href='" + unlockAccountUrl + "?email=" + email + "'>Unlock Account</a>"); // Clickable link
        body.append("</body>"); // Closing body tag
        body.append("</html>"); // Closing HTML tag
        return body;
    }

    public static StringBuilder generateEmailBodyForForgetPassword(String password, String username) {
        StringBuilder body = new StringBuilder();
        body.append("<html>"); // Opening HTML tag
        body.append("<body>"); // Opening body tag
        body.append("<p>Hi "+username + ",</p>"); // Salutation
        body.append("<p>Thank you for registering with us! Please find password:</p>"); // Message
        body.append("<p><b> Password:</b> " + password + "</p>"); // Temporary password
        body.append("</body>"); // Closing body tag
        body.append("</html>"); // Closing HTML tag
        return body;
    }
}
