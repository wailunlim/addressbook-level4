package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;
import seedu.address.storage.XmlSerializableAccountList;
import seedu.address.testutil.TypicalAccount;

public class EditPasswordCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "Account");
    private static final Path VALID_ACCOUNTLIST = TEST_DATA_FOLDER.resolve("accountlist.xml");
    private static final String OLD_PASSWORD = "@myPassword";
    private static final String NEW_PASSWORD = "SomeNewP@Ssw0rd";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    // set up the model to contain the account with index 2 in the account list.
    @Before
    public void setUp() throws Exception {
        XmlSerializableAccountList xmlSerializableAccountList =
                XmlUtil.getDataFromFile(VALID_ACCOUNTLIST, XmlSerializableAccountList.class);
        Account account = xmlSerializableAccountList.toModelType().getList().get(2);
        model = new ModelManager(account);
        expectedModel = new ModelManager(account);
    }

    @Test
    public void constructor_nullOldPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditPasswordCommand(null, NEW_PASSWORD);
    }

    @Test
    public void constructor_nullNewPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditPasswordCommand(OLD_PASSWORD, null);
    }

    @Test
    public void execute_wrongPassword() {
        String userTypedWrongPassword = "wrongPasswordTypedByUser";
        EditPasswordCommand editPasswordCommand = new EditPasswordCommand(userTypedWrongPassword,
                NEW_PASSWORD, VALID_ACCOUNTLIST);
        assertCommandFailure(editPasswordCommand, model, commandHistory,
                EditPasswordCommand.MESSAGE_FAILURE_PASSWORDWRONG);
    }

    @Test
    public void execute_samePassword() {
        EditPasswordCommand editPasswordCommand = new EditPasswordCommand(OLD_PASSWORD,
                OLD_PASSWORD, VALID_ACCOUNTLIST);
        assertCommandFailure(editPasswordCommand, model, commandHistory,
                EditPasswordCommand.MESSAGE_FAILURE_SAMEPASSWORD);
    }

    @Test
    public void execute_changeNewPassword_success() {
        // change old password to new password
        EditPasswordCommand editPasswordCommand = new EditPasswordCommand(OLD_PASSWORD,
                NEW_PASSWORD, VALID_ACCOUNTLIST);
        assertCommandSuccess(editPasswordCommand, model, commandHistory,
                EditPasswordCommand.MESSAGE_SUCCESS, expectedModel);

        // change back new password to old password
        EditPasswordCommand editPasswordCommand2 = new EditPasswordCommand(NEW_PASSWORD,
                OLD_PASSWORD, VALID_ACCOUNTLIST);
        assertCommandSuccess(editPasswordCommand2, model, commandHistory,
                EditPasswordCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
