package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;
import seedu.address.model.account.Role;
/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccount {
    public static final Account SIMUN = new Account("simun", "@myPassword", Role.SUPER_USER);
    public static final Account ROSE = new Account("whiterose", "@myPassword", Role.READ_ONLY_USER);
    public static final Account LUNA = new Account("alex95", "_MyPassword", Role.READ_ONLY_USER);
    public static final Account ROOTACCOUNT = new Account("rootUser", "rootPassword", Role.SUPER_USER);
    public static final Account EMPTYUSERNAME_ACCOUNT = new Account("", "rootPassword", Role.SUPER_USER);

    /**
     * Returns an {@code Account} with all the typical Accounts.
     */
    public static AccountList getTypicalAccountList() {
        AccountList accountList = new AccountList();
        for (Account account : getTypicalAccount()) {
            accountList.addAccount(account);
        }

        return accountList;
    }

    public static List<Account> getTypicalAccount() {
        return new ArrayList<>(Arrays.asList(ROOTACCOUNT, SIMUN, ROSE));
    }
}
