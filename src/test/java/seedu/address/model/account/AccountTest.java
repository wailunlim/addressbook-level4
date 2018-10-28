package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AccountTest {

    @Test
    public void hasWritePrivilege() {
        Account hasWritePrivilegeAccount = new Account("root", "p@ssw0rd", Role.SUPER_USER);
        Model modelHasWritePrivilege = new ModelManager(hasWritePrivilegeAccount);
        assertTrue(modelHasWritePrivilege.getUserAccount().hasWritePrivilege());
        Account noWritePrivilegeAccount = new Account("rose", "p@ssw0rd", Role.READ_ONLY_USER);
        Model modelNoWritePrivilege = new ModelManager(noWritePrivilegeAccount);
        assertFalse(modelNoWritePrivilege.getUserAccount().hasWritePrivilege());
    }

    @Test
    public void hasDeletePrivilege() {
        Account hasDeletePrivilegeAccount = new Account("root", "p@ssw0rd", Role.SUPER_USER);
        Model modelHasDeletePrivilege = new ModelManager(hasDeletePrivilegeAccount);
        assertTrue(modelHasDeletePrivilege.getUserAccount().hasDeletePrivilege());
        Account noDeletePrivilegeAccount = new Account("rose", "p@ssw0rd", Role.READ_ONLY_USER);
        Model modelNoDeletePrivilege = new ModelManager(noDeletePrivilegeAccount);
        assertFalse(modelNoDeletePrivilege.getUserAccount().hasDeletePrivilege());
    }

    @Test
    public void hasAccountCreationPrivilege() {
        Account hasAccountCreationPrivilegeAccount = new Account("root", "p@ssw0rd", Role.SUPER_USER);
        Model modelHasAccountCreationPrivilege = new ModelManager(hasAccountCreationPrivilegeAccount);
        assertTrue(modelHasAccountCreationPrivilege.getUserAccount().hasAccountCreationPrivilege());
        Account noAccountCreationPrivilegeAccount = new Account("rose", "p@ssw0rd", Role.READ_ONLY_USER);
        Model modelNoAccountCreationPrivilege = new ModelManager(noAccountCreationPrivilegeAccount);
        assertFalse(modelNoAccountCreationPrivilege.getUserAccount().hasAccountCreationPrivilege());
    }

    @Test
    public void transformToHashedAccountSuccess() {
        String plainTextPassword = "plainTextPassword";
        Account account = new Account("Username", plainTextPassword, Role.SUPER_USER);
        assertTrue(account.getPassword().equals(plainTextPassword));

        account.transformToHashedAccount();
        assertFalse(account.getPassword().equals(plainTextPassword));
    }

    @Test
    public void equals() {
        Account account1 = new Account("testusername", "testP@ssw0rD", Role.SUPER_USER);
        Account account2 = new Account("testusername", "testP@ssw0rD", Role.SUPER_USER);
        assertEquals(account1, account2);
        Account account3 = new Account("TESTUserNaMe", "testP@ssw0rD", Role.SUPER_USER);
        assertEquals(account2, account3);
        Account differentAccount = new Account("username", "testP@ssw0rD", Role.SUPER_USER);
        assertNotEquals(account2, differentAccount);
        Account differentPassword = new Account("testusername", "test123", Role.SUPER_USER);
        assertNotEquals(account2, differentPassword);
        Account roleDoesNotMatter = new Account("testusername", "testP@ssw0rD", Role.READ_ONLY_USER);
        assertEquals(account2, roleDoesNotMatter);
    }
}
