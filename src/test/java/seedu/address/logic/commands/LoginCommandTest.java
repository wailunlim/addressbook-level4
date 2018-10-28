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

    private static final String USERNAME = "rootUser";
    private static final String PASSWORD = "rootPassword";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullUsername_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, PASSWORD);
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(USERNAME, null);
    }

    @Test
    public void constructor2_nullUsername_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, PASSWORD, ACCOUNTLIST_WITHROOT);
    }

    @Test
    public void constructor2_nullPasswrod_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(USERNAME, null, ACCOUNTLIST_WITHROOT);
    }

    @Test
    public void execute_rootAccountSucceed() {
        LoginCommand loginCommand = new LoginCommand(USERNAME, PASSWORD, ACCOUNTLIST_WITHROOT);
        assertCommandSuccess(loginCommand, model, commandHistory, LoginCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_userRegisteredAccountSucceed() {
        Account simun = TypicalAccount.SIMUN;
        LoginCommand loginCommandSimun = new LoginCommand(simun.getUserName(), simun.getPassword(),
                ACCOUNTLIST_WITSELFREGISTEREDACCOUNT);
        assertCommandSuccess(loginCommandSimun, model, commandHistory, LoginCommand.MESSAGE_SUCCESS, expectedModel);

        Account rose = TypicalAccount.ROSE;
        LoginCommand loginCommandRose = new LoginCommand(rose.getUserName(), rose.getPassword(),
                ACCOUNTLIST_WITSELFREGISTEREDACCOUNT);
        assertCommandSuccess(loginCommandRose, model, commandHistory, LoginCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_usernameOrPasswordWrong() {
        String usernameWrong = "wrongUserName";
        LoginCommand loginWrongUsername = new LoginCommand(usernameWrong, PASSWORD, ACCOUNTLIST_WITHROOT);
        assertCommandFailure(loginWrongUsername, model, commandHistory, LoginCommand.MESSAGE_FAILURE);

        String passwordWrong = "wrongPassword";
        LoginCommand loginWrongPassword = new LoginCommand(USERNAME, passwordWrong, ACCOUNTLIST_WITHROOT);
        assertCommandFailure(loginWrongPassword, model, commandHistory, LoginCommand.MESSAGE_FAILURE);
    }
}
