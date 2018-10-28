package seedu.address.logic.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordAuthenticationTest {
    private static final PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    private static final String plainTextPassword = "plaintext";

    @Test
    public void hashIsWorking_success() {
        String hashed = passwordAuthentication.hash(plainTextPassword.toCharArray());
        // hashed and plaintext are different
        assertFalse(hashed.equals(plainTextPassword));
        // plaintext is the correct password for the hash
        assertTrue(passwordAuthentication.authenticate(plainTextPassword.toCharArray(), hashed));
        String wrongPassword = "wrongPassword";
        // wrongPassword is not the correct password for the hash
        assertFalse(passwordAuthentication.authenticate(wrongPassword.toCharArray(), hashed));
    }

    @Test
    public void staticMethodWorking() {
        String hashed = PasswordAuthentication.getHashedPasswordFromPlainText(plainTextPassword);
        assertTrue(passwordAuthentication.authenticate(plainTextPassword.toCharArray(), hashed));
    }
}
