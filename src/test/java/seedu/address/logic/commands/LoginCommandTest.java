package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;
import seedu.address.model.account.Role;
import seedu.address.testutil.TypicalAccount;

public class LoginCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "LoginCommandTest");
    private static final Path ACCOUNTLIST_WITHROOT = TEST_DATA_FOLDER.resolve("accountlist_withrootonly.xml");
    private static final Path ACCOUNTLIST_WITSELFREGISTEREDACCOUNT = TEST_DATA_FOLDER
            .resolve("accountlist_withselfregisteredaccount.xml");
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null);
    }

    @Test
    public void constructor2_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, ACCOUNTLIST_WITHROOT);
    }

    @Test
    public void execute_rootAccountSucceed() {
        LoginCommand loginCommand = new LoginCommand(TypicalAccount.ROOTACCOUNT, ACCOUNTLIST_WITHROOT);
        assertCommandSuccess(loginCommand, model, commandHistory, LoginCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_userRegisteredAccountSucceed() {
        LoginCommand loginCommandSimun = new LoginCommand(TypicalAccount.SIMUN, ACCOUNTLIST_WITSELFREGISTEREDACCOUNT);
        assertCommandSuccess(loginCommandSimun, model, commandHistory, LoginCommand.MESSAGE_SUCCESS, expectedModel);

        LoginCommand loginCommandRose = new LoginCommand(TypicalAccount.ROSE, ACCOUNTLIST_WITSELFREGISTEREDACCOUNT);
        assertCommandSuccess(loginCommandRose, model, commandHistory, LoginCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_usernameOrPasswordWrong() {
        Account usernameWrong = new Account("usernameWrong", "rootPassword", Role.SUPER_USER);
        LoginCommand loginWrongUsername = new LoginCommand(usernameWrong, ACCOUNTLIST_WITHROOT);
        assertCommandFailure(loginWrongUsername, model, commandHistory, LoginCommand.MESSAGE_FAILURE);

        Account passwordWrong = new Account("rootUser", "rootPasswordWrong", Role.SUPER_USER);
        LoginCommand loginWrongPassword = new LoginCommand(passwordWrong, ACCOUNTLIST_WITHROOT);
        assertCommandFailure(loginWrongPassword, model, commandHistory, LoginCommand.MESSAGE_FAILURE);
    }
}
