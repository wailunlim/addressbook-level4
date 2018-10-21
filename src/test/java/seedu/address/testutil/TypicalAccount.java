package seedu.address.testutil;

import seedu.address.model.account.Account;
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
}
