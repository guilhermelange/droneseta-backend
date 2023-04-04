package com.udesc.droneseta.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSecure {
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validate(String password, String hashSenha) {
        String pass = encrypt(password);
        if (pass == null) {
            return false;
        }
        return pass.equals(hashSenha);
    }
}
