package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.security.AccountManager;


public class RoleTest {

    @Test
    public void hasWritePrivilege() {
        Account account1 = new Account("root", "p@ssw0rd", Role.SUPER_USER);
        AccountManager.setCurrentUserRole(account1.getRole());
        assertTrue(Role.hasWritePrivilege());
        Account account2 = new Account("rose", "p@ssw0rd", Role.READ_ONLY_USER);
        AccountManager.setCurrentUserRole(account2.getRole());
        assertFalse(Role.hasWritePrivilege());
    }

    @Test
    public void hasDeletePrivilege() {
        Account account1 = new Account("root", "p@ssw0rd", Role.SUPER_USER);
        AccountManager.setCurrentUserRole(account1.getRole());
        assertTrue(Role.hasDeletePrivilege());
        Account account2 = new Account("rose", "p@ssw0rd", Role.READ_ONLY_USER);
        AccountManager.setCurrentUserRole(account2.getRole());
        assertFalse(Role.hasDeletePrivilege());
    }

    @Test
    public void hasAccountCreationPrivilege() {
        Account account1 = new Account("superuser", "p@ssw0rd", Role.SUPER_USER);
        AccountManager.setCurrentUserRole(account1.getRole());
        assertTrue(Role.hasAccountCreationPrivilege());
        Account account2 = new Account("readonlyuser", "p@ssw0rd", Role.READ_ONLY_USER);
        AccountManager.setCurrentUserRole(account2.getRole());
        assertFalse(Role.hasAccountCreationPrivilege());
    }
}
