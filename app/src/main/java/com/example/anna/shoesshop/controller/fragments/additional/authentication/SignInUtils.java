package com.example.anna.shoesshop.controller.fragments.additional.authentication;

class SignInUtils {

    static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    static boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    static String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
