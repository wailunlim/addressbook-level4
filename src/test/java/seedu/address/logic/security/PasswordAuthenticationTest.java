package seedu.address.logic.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PasswordAuthenticationTest {
    private static final PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    private static final String plainTextPassword = "plaintext";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void iteration_tooHigh_fail() {
        thrown.expect(IllegalArgumentException.class);
        new PasswordAuthentication(31);
    }

    @Test
    public void iteration_negative_fail() {
        thrown.expect(IllegalArgumentException.class);
        new PasswordAuthentication(-1);
    }
}
