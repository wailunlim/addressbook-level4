package seedu.address.logic.security;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.XmlAccountStorage;

/**
 * This class is used for account related purposes, in particular, login,
 * logout, create new account, and forgotten password.
 */
public class AccountManager {
    private static AccountList accountList;
    /**
     * Indicate if user has successfully logged in.
     * TODO Set it to false during production.
     * TODO We set it to True now so we do not have to keep logging in during development.
     */
    private static boolean loginSuccess = true;

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
        return new Account("rootUser", "rootPassword");
    }

    public static AccountList getDefaultAccountList() {
        AccountList accountList = new AccountList();
        accountList.addAccount(getRootAccount());
        AccountManager.accountList = accountList;

        return accountList;
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
            if (accountStorage.getAccountList().hasAccount(account)) {
                setLoginSuccess();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
        } catch (IOException e2) {
            logger.warning("Problem while reading from the file containing all the accounts");
        }

        return isLoginSuccess();
    }
}
