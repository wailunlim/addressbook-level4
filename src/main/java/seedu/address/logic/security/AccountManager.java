package seedu.address.logic.security;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;
import seedu.address.model.account.Role;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.XmlAccountStorage;

/**
 * This class is used for account related purposes, in particular, login,
 * logout, create new account, and forgotten password.
 */
public class AccountManager {
    /**
     * Indicate if user has successfully logged in.
     * TODO Set it to false during production.
     * TODO We set it to True now so we do not have to keep logging in during development.
     */
    private static boolean loginSuccess = false;

    // TODO erase away Role.SUPER_USER in production. We set it to Role.SUPER_USER during
    // TODO development to allow developer to have all privileges to all commands.
    private static Role userRole = Role.SUPER_USER;

    private static final Logger logger = LogsCenter.getLogger(AccountManager.class);

    private AccountStorage accountStorage;

    public AccountManager() {
        accountStorage = new XmlAccountStorage();
    }

    /**
     * The root user account hardcoded.
     * @return
     */
    public static Account getRootAccount() {
        return new Account("rootUser", "rootPassword", Role.SUPER_USER);
    }

    /**
     * After user has successfully logged in, set loginSuccess field to true.
     */
    private void setLoginSuccess() {
        loginSuccess = true;
    }

    /**
     * Return true if user has successfully logged in, else return false.
     * @return True if user has successfully logged in, false otherwise.
     */
    public static boolean isLoginSuccess() {
        return loginSuccess;
    }

    /**
     * User attempts to login with his account.
     * @param account The account to login with
     * @return true if login is successful, false otherwise.
     */
    public boolean loginWithAccountSucceed(Account account) {
        try {
            AccountList accountList = accountStorage.getAccountList();
            int indexInList = accountList.indexOfAccount(account);
            if (indexInList != -1) {
                setLoginSuccess();
                setCurrentUserRole(accountList.getList().get(indexInList).getRole());
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
        } catch (IOException e2) {
            logger.warning("Problem while reading from the file containing all the accounts");
        }

        return isLoginSuccess();
    }

    public static void setCurrentUserRole(Role role) {
        userRole = role;
    }

    public static Role getCurrentUserRole() {
        return userRole;
    }
}
