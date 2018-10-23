package systemtests;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.security.AccountManager;
import seedu.address.testutil.TypicalAccount;

/**
 * A class that simplifies logging into the app.
 * Before a user logged in, only 'login' and 'help' command is available.
 * Therefore to test other methods, the user has to log in first.
 */
public class LoginHelper {
    private static final Path ROOT_ACCOUNTLIST_PATH = Paths.get("src", "test", "data",
            "SystemTest", "rootaccount_accountlist.xml");

    /**
     * If the user is not already logged in, log the user in successfully into the app
     * with the root account.
     */
    public static void loginIfNotAlreadyLoggedIn() {
        if (!AccountManager.isLoginSuccess()) {
            AccountManager accountManager = new AccountManager(ROOT_ACCOUNTLIST_PATH);
            accountManager.loginWithAccountSucceed(TypicalAccount.ROOTACCOUNT);
        }
    }
}

