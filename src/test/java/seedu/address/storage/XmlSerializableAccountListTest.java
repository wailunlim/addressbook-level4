package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalAccount.ROOTACCOUNT;
import static seedu.address.testutil.TypicalAccount.ROSE;
import static seedu.address.testutil.TypicalAccount.SIMUN;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;
import seedu.address.testutil.TypicalAccount;

public class XmlSerializableAccountListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableAccountListTest");
    private static final Path TYPICAL_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("typicalAccount_AccountList.xml");
    private static final Path INVALID_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("invalidAccount_AccountList.xml");
    private static final Path DUPLICATE_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("duplicateAccount_AccountList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalAccountFile_success() throws Exception {
        XmlSerializableAccountList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ACCOUNT_FILE,
                XmlSerializableAccountList.class);
        AccountList accountListFromFile = dataFromFile.toModelType();
        AccountList typicalAccountAccountList = TypicalAccount.getTypicalAccountList();
        assertEquals(accountListFromFile, typicalAccountAccountList);
    }

    @Test
    public void toModelType_invalidAccountFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAccountList dataFromFile = XmlUtil.getDataFromFile(INVALID_ACCOUNT_FILE,
                XmlSerializableAccountList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateAccounts_throwsIllegalValueException() throws Exception {
        XmlSerializableAccountList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ACCOUNT_FILE,
                XmlSerializableAccountList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAccountList.MESSAGE_DUPLICATE_ACCOUNT);
        dataFromFile.toModelType();
    }

    @Test
    public void equals_success() {
        AccountList accountList1 = TypicalAccount.getTypicalAccountList();
        AccountList accountList2 = TypicalAccount.getTypicalAccountList();
        XmlSerializableAccountList xmlSerializableAccountList1 = new XmlSerializableAccountList(accountList1);
        XmlSerializableAccountList xmlSerializableAccountList2 = new XmlSerializableAccountList(accountList2);
        assertEquals(xmlSerializableAccountList1, xmlSerializableAccountList2);
    }

    @Test
    public void notEquals_success() {
        ArrayList<Account> accountArrayList1 = new ArrayList<>(Arrays.asList(ROOTACCOUNT, SIMUN, ROSE));
        ArrayList<Account> accountArrayList2 = new ArrayList<>(Arrays.asList(ROOTACCOUNT, SIMUN));
        AccountList accountList1 = new AccountList();
        AccountList accountList2 = new AccountList();
        for (Account account : accountArrayList1) {
            accountList1.addAccount(account);
        }

        for (Account account : accountArrayList2) {
            accountList2.addAccount(account);
        }
        XmlSerializableAccountList xmlSerializableAccountList1 = new XmlSerializableAccountList(accountList1);
        XmlSerializableAccountList xmlSerializableAccountList2 = new XmlSerializableAccountList(accountList2);
        assertNotEquals(xmlSerializableAccountList1, xmlSerializableAccountList2);
    }
}
