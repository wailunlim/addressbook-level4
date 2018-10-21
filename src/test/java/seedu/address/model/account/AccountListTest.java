package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TypicalAccount;


public class AccountListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getList() {
        AccountList accountList = new AccountList();
        accountList.addAccount(TypicalAccount.ROOTACCOUNT);
        assertTrue(accountList.getList().size() == 1);
        accountList.addAccount(TypicalAccount.SIMUN);
        List<Account> list = accountList.getList();
        assertTrue(list.size() == 2);
        assertTrue(list.get(0).equals(TypicalAccount.ROOTACCOUNT));
        assertTrue(list.get(1).equals(TypicalAccount.SIMUN));
    }

    @Test
    public void indexOfAccount_nullAccount() {
        AccountList accountList = new AccountList();
        accountList.addAccount(TypicalAccount.ROOTACCOUNT);
        thrown.expect(NullPointerException.class);
        accountList.indexOfAccount(null);
    }

    @Test
    public void indexOfAccount_success() {
        AccountList accountList = new AccountList();
        accountList.addAccount(TypicalAccount.ROOTACCOUNT);
        accountList.addAccount(TypicalAccount.SIMUN);
        accountList.addAccount(TypicalAccount.ROSE);
        assertTrue(accountList.indexOfAccount(TypicalAccount.ROOTACCOUNT) == 0);
        assertTrue(accountList.indexOfAccount(TypicalAccount.SIMUN) == 1);
        assertTrue(accountList.indexOfAccount(TypicalAccount.ROSE) == 2);
        assertTrue(accountList.indexOfAccount(TypicalAccount.LUNA) == -1);
    }

    @Test
    public void indexOfAccount_emptyAccountList() {
        AccountList accountList = new AccountList();
        assertTrue(accountList.indexOfAccount(TypicalAccount.ROOTACCOUNT) == -1);
        assertTrue(accountList.indexOfAccount(TypicalAccount.SIMUN) == -1);
        assertTrue(accountList.indexOfAccount(TypicalAccount.ROSE) == -1);
    }

    @Test
    public void hasAccount_nullAccount() {
        AccountList accountList = new AccountList();
        accountList.addAccount(TypicalAccount.ROOTACCOUNT);
        thrown.expect(NullPointerException.class);
        accountList.hasAccount(null);
    }

    @Test
    public void hasAccount_success() {
        AccountList accountList = new AccountList();
        accountList.addAccount(TypicalAccount.ROOTACCOUNT);
        assertTrue(accountList.hasAccount(TypicalAccount.ROOTACCOUNT));
        assertFalse(accountList.hasAccount(TypicalAccount.SIMUN));
        accountList.addAccount(TypicalAccount.SIMUN);
        assertTrue(accountList.hasAccount(TypicalAccount.SIMUN));
    }

    @Test
    public void hasUserName_nullUsername() {
        AccountList accountList = new AccountList();
        thrown.expect(NullPointerException.class);
        accountList.hasAccount(null);
    }

    @Test
    public void hasUserName() {
        AccountList accountList = new AccountList();
        Account account = new Account("Simun", "P@ssw0rd", Role.SUPER_USER);
        accountList.addAccount(account);
        assertTrue(accountList.hasUserName("Simun"));
        assertTrue(accountList.hasUserName("simun"));
        assertTrue(accountList.hasUserName("SiMuN"));
        assertFalse(accountList.hasUserName("Simun "));
        assertFalse(accountList.hasUserName(" simun"));
        assertFalse(accountList.hasUserName("Tham"));
        accountList.addAccount(new Account("Tham", "P@ssw0rd", Role.SUPER_USER));
        assertTrue(accountList.hasUserName("Tham"));
        assertFalse(accountList.hasUserName(""));
    }
}
