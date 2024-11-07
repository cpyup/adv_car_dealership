package com.pluralsight.utility;
import org.mindrot.jbcrypt.BCrypt;

public class LoginVerification {
    private static final String storedHash = "$2a$10$.y1maOZX1YvbxeN6/Dlw5ex4n0TjPoNbPL/UILCpfHuK9aKYorVFi";
    private LoginVerification(){}

    public static boolean verifyPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, storedHash);
    }


}
