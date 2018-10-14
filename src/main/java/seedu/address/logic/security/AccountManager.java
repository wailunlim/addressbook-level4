package seedu.address.logic.security;

import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;

/**
 * This class is used for account related purposes, in particular, login,
 * logout, create new account, and forgotten password.
 */
public class AccountManager {
    private static AccountList accountList;
    /**
     * Indicate if user has successfully logged in.
     */
    private static boolean loginSuccess;

    public AccountManager() {
        loginSuccess = false;
    }

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
    public boolean loginWithAccount(Account account) {
        if (accountList.hasAccount(account)) {
            setLoginSuccess();
        }

        return isLoginSuccess();
    }
}
