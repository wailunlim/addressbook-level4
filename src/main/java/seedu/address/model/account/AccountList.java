package seedu.address.model.account;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class AccountList {
    private List<Account> accountList;

    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return accountList.contains(account);
    }
}
