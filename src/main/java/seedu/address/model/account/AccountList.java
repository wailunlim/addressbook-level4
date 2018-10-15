package seedu.address.model.account;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * AccountList represents the entire list of accounts that were created
 * and stored in the local database. Currently, all username and password are
 * stored locally in a xml file without encryption.
 */
public class AccountList {
    private List<Account> accountList;

    public AccountList() {
        accountList = new ArrayList<>();
    }

    public List<Account> getList() {
        return accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    /**
     * Returns the index of the first occurrence of the given account in this accountList,
     * or -1 if accountList does not contain the account.
     * @param account The account to be checked.
     * @return The index of the first occurrence of the account in the list, or -1 if
     * accountList does not contain the account.
     */
    public int indexOfAccount(Account account) {
        requireNonNull(account);
        return accountList.indexOf(account);
    }

    /**
     * Returns true if accountList contains the given account.
     * @param account The account to be checked.
     * @return true if the accountList contains the account, false otherwise.
     */
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return indexOfAccount(account) != -1;
    }

    public boolean hasUserName(String username) {
        requireNonNull(username);
        for(Account account : accountList) {
            if (account.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }
}
