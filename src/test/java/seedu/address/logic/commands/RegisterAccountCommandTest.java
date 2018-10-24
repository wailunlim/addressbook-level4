package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;
import seedu.address.model.account.Role;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.XmlAccountStorage;
import seedu.address.testutil.TypicalAccount;


public class RegisterAccountCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "RegisterAccountCommandTest");
    private static final Path ACCOUNTLIST_WITHROOT = TEST_DATA_FOLDER.resolve("accountlist_withrootonly.xml");
    private static final Path INVALID_ACCOUNTLIST_WRONGXMLSTUCTURE =
            TEST_DATA_FOLDER.resolve("invalidaccountlist_wrongstructure.xml");
    private static final Path INVALID_ACCOUNTLIST_NOTEVENXMLSTRUCTURE =
            TEST_DATA_FOLDER.resolve("notevenxmlstructure.xml");
    private static final Account ACCOUNT_TO_SAVE = new Account("username123", "password456", Role.SUPER_USER);

    private Model model = new ModelManager(TypicalAccount.ROOTACCOUNT);
    private Model expectedModel = new ModelManager(TypicalAccount.ROOTACCOUNT);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_registerNewAccount_success() {
        createDefaultAccountListXmlIfMissing();
        RegisterAccountCommand registerAccountCommand = new RegisterAccountCommand(ACCOUNT_TO_SAVE,
                ACCOUNTLIST_WITHROOT);
        assertCommandSuccess(registerAccountCommand, model, commandHistory, RegisterAccountCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test(expected = LackOfPrivilegeException.class)
    public void execute_noPrivilege_throwsLackOfPrivilegeException() throws LackOfPrivilegeException, CommandException {
        Model model = new ModelManager(TypicalAccount.ROSE);
        RegisterAccountCommand registerAccountCommand = new RegisterAccountCommand(ACCOUNT_TO_SAVE,
                ACCOUNTLIST_WITHROOT);
        registerAccountCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_registerWithAnExistingUsername_failure() {
        createDefaultAccountListXmlIfMissing();
        String expectedMessage = RegisterAccountCommand.MESSAGE_FAILURE_DUPLICATE;
        RegisterAccountCommand registerAccountCommand = new RegisterAccountCommand(TypicalAccount.ROOTACCOUNT,
                ACCOUNTLIST_WITHROOT);
        assertCommandFailure(registerAccountCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_cannotFindFileToSaveTo_failure() {
        Path invalidPath = Paths.get("src", "test", "data", "invalidFile", "invalidfile.xml");
        RegisterAccountCommand invalidFilePathCommand = new RegisterAccountCommand(ACCOUNT_TO_SAVE, invalidPath);
        String expectedMessage = RegisterAccountCommand.MESSAGE_FAILURE_FILENOTFOUND;
        assertCommandFailure(invalidFilePathCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_filePathWithInvalidXmlStructure_failure() {
        RegisterAccountCommand invalidAccountListCommand = new RegisterAccountCommand(ACCOUNT_TO_SAVE,
                INVALID_ACCOUNTLIST_WRONGXMLSTUCTURE);
        String expectedMessage = RegisterAccountCommand.MESSAGE_FAILURE;
        assertCommandFailure(invalidAccountListCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_filePathNotEvenXmlStructure_failure() {
        RegisterAccountCommand invalidAccountListCommand = new RegisterAccountCommand(ACCOUNT_TO_SAVE,
                INVALID_ACCOUNTLIST_NOTEVENXMLSTRUCTURE);
        String expectedMessage = RegisterAccountCommand.MESSAGE_FAILURE;
        assertCommandFailure(invalidAccountListCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Create a AccountList xml file with root account populated to the path {@code ACCOUNTLIST_WITHROOT}.
     */
    private void createDefaultAccountListXmlIfMissing() {
        try {
            Files.deleteIfExists(ACCOUNTLIST_WITHROOT);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        assertFalse(FileUtil.isFileExists(ACCOUNTLIST_WITHROOT));
        AccountStorage accountStorage = new XmlAccountStorage(ACCOUNTLIST_WITHROOT);
        accountStorage.populateRootAccountIfMissing(TypicalAccount.ROOTACCOUNT);
        assertTrue(FileUtil.isFileExists(ACCOUNTLIST_WITHROOT));
    }
}
