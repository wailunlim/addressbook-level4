package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class AccountTest {

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
