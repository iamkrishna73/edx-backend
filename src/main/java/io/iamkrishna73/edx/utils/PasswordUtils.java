package io.iamkrishna73.edx.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtils {
    public static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = RandomStringUtils.random(8, characters);
        return password;

    }

}
