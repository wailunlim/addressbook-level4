package seedu.address.storage;

/**
 * This class is responsible for login and logout system implemented in the
 * application.
 */
public class UserAuthenticationManager {
    /**
     * Indicate if user has successfully logged in.
     */
    private static boolean loginSuccess;

    /**
     * Prevents user from instantiating an object of UserAuthenticationManager.
     */
    private UserAuthenticationManager() {
    }

    /**
     * Attempts to log User in to the Application.
     */
    public static void logIn() {
        //TODO add logic here
        loginSuccess = true;
    }

    /**
     * Return true if User has entered valid login credentials, and should be
     * granted access to the System.
     * @return true if User enters valid login credentials, false otherwise.
     */
    public static boolean getLoginSuccess () {
        return loginSuccess;
    }
}
