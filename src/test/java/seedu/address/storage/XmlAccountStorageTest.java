package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;
import seedu.address.model.account.Role;
import seedu.address.testutil.TypicalAccount;


public class XmlAccountStorageTest {

    private static final String INVALID_FILEPATH = "someInvalidPath.xml";
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlAccountStorageTest");
    private static final Path TEST_ACCOUNTLIST_PATH = Paths.get("src", "test", "data",
            "XmlAccountStorageTest", "accountlist.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AccountList getAccountList(String filePath) throws Exception {
        return new XmlAccountStorage(Paths.get(filePath)).getAccountList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void getAccountList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        getAccountList(null);
    }

    @Test(expected = FileNotFoundException.class)
    public void getAccountList_missingFile_nullResult() throws Exception {
        getAccountList(INVALID_FILEPATH);
    }

    @Test
    public void getAccountList_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        getAccountList("NotXmlFormatAccountList.xml");
    }

    @Test
    public void getAccountList_invalidUsername_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        getAccountList("InvalidUsernameAccountList.xml");
    }

    @Test
    public void getAccountList_invalidAndValidUsername_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        getAccountList("InvalidAndValidUsernameAccountList.xml");
    }

    @Test
    public void populateRootAccountIfMissing_populateForFirstTime() {
        try {
            Files.deleteIfExists(TEST_ACCOUNTLIST_PATH);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        assertFalse(FileUtil.isFileExists(TEST_ACCOUNTLIST_PATH));
        AccountStorage accountStorage = new XmlAccountStorage(TEST_ACCOUNTLIST_PATH);
        accountStorage.populateRootAccountIfMissing(TypicalAccount.ROOTACCOUNT);
        assertTrue(FileUtil.isFileExists(TEST_ACCOUNTLIST_PATH));

        // asserts that the file contains the default account.
        try {
            AccountList accountList = accountStorage.getAccountList(TEST_ACCOUNTLIST_PATH);
            assertTrue(accountList.getList().size() == 1);
            assertTrue(accountList.getList().get(0).equals(TypicalAccount.ROOTACCOUNT));
        } catch (IOException | DataConversionException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void getAccountStorageFilePath_success() {
        AccountStorage accountStorage = new XmlAccountStorage(TEST_DATA_FOLDER);
        assertTrue(accountStorage.getAccountStorageFilePath().equals(TEST_DATA_FOLDER));
    }

    @Test
    public void saveAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAccount(null, "SomeFile.xml");
    }

    /**
     * Save an account to the specific file path.
     * @param account The account to be saved.
     * @param filePath The file path to save the account to.
     */
    private void saveAccount(Account account, String filePath) {
        try {
            new XmlAccountStorage(Paths.get(filePath))
                    .saveAccount(account, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAccount_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAccount(TypicalAccount.SIMUN, null);
    }

    @Test
    public void saveAccount_invalidAccount_throwsNullPointerException() {
        thrown.expect(IllegalArgumentException.class);
        try {
            new XmlAccountStorage(TEST_ACCOUNTLIST_PATH).saveAccount(TypicalAccount.EMPTYUSERNAME_ACCOUNT);
        } catch (IOException e) {
            throw new AssertionError("There should not be an error writing to the file.", e);
        }
    }

    @Test
    public void saveAccount_accountListOnlyContainsRootAccount() {
        populateRootAccountIfMissing_populateForFirstTime();

        AccountStorage accountStorage = new XmlAccountStorage(TEST_ACCOUNTLIST_PATH);

        try {
            // save 1 time
            accountStorage.saveAccount(TypicalAccount.SIMUN);
            AccountList accountList = accountStorage.getAccountList(TEST_ACCOUNTLIST_PATH);
            assertTrue(accountList.getList().size() == 2);
            assertTrue(accountList.getList().get(0).equals(TypicalAccount.ROOTACCOUNT));
            assertTrue(accountList.getList().get(1).equals(TypicalAccount.SIMUN));

            // save again
            accountStorage.saveAccount(TypicalAccount.ROSE);
            accountList = accountStorage.getAccountList(TEST_ACCOUNTLIST_PATH);
            assertTrue(accountList.getList().size() == 3);
            assertTrue(accountList.getList().get(0).equals(TypicalAccount.ROOTACCOUNT));
            assertTrue(accountList.getList().get(1).equals(TypicalAccount.SIMUN));
            assertTrue(accountList.getList().get(2).equals(TypicalAccount.ROSE));
        } catch (DataConversionException | IOException e) {
            throw new AssertionError("There should not be an error saving to the file.", e);
        }
    }

    @Test
    public void updateAccountPassword_success() {
        AccountStorage accountStorage = new XmlAccountStorage(TEST_ACCOUNTLIST_PATH);
        String oldPassword = "@myPassword";
        String newPassword = "Somerand0mNewP@ssword";
        Account account = new Account("whiterose", oldPassword, Role.READ_ONLY_USER);
        int indexOfRose = 2;

        try {
            AccountList accountList = accountStorage.getAccountList();
            List<Account> list = accountList.getList();
            assertTrue(list.get(indexOfRose).getPassword().equals(oldPassword));

            // change to new password
            accountStorage.updateAccountPassword(account, newPassword);
            accountList = accountStorage.getAccountList();
            list = accountList.getList();
            assertFalse(list.get(indexOfRose).getPassword().equals(oldPassword));
            assertTrue(list.get(indexOfRose).getPassword().equals(newPassword));

            // change back to old password
            Account newAccount = new Account("whiterose", newPassword, Role.READ_ONLY_USER);
            accountStorage.updateAccountPassword(newAccount, oldPassword);
            accountList = accountStorage.getAccountList();
            list = accountList.getList();
            assertFalse(list.get(indexOfRose).getPassword().equals(newPassword));
            assertTrue(list.get(indexOfRose).getPassword().equals(oldPassword));
        } catch (DataConversionException | IOException e) {
            throw new AssertionError("File should exist and this line should not be called.", e);
        }
    }
}
