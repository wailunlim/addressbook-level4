package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalAccount;

public class LogoutCommandTest {
    private Model model = new ModelManager(TypicalAccount.ROOTACCOUNT);
    private Model expectedModel = new ModelManager(TypicalAccount.ROOTACCOUNT);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {
        assertTrue(model.isUserLogIn());
        LogoutCommand logoutCommand = new LogoutCommand();
        assertCommandSuccess(logoutCommand, model, commandHistory, LogoutCommand.MESSAGE_SUCCESS, expectedModel);
        assertFalse(model.isUserLogIn());
    }
}
