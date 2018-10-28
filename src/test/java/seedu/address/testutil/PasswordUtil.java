package seedu.address.testutil;

import static org.junit.Assert.assertTrue;

import seedu.address.logic.security.PasswordAuthentication;

/**
 * A utility class for password related methods
 */
public class PasswordUtil {
    /**
     * Assert that the given plainTextPassword corresponds to the hashedpassword supplied.
     * @param plainTextPassword The password in plaintext
     * @param hashedPassword A hashed string
     */
    public static void assertPasswordCorrect(String plainTextPassword, String hashedPassword) {
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        assertTrue(passwordAuthentication.authenticate(plainTextPassword.toCharArray(), hashedPassword));
    }
}

