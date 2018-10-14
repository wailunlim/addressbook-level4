package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.security.AccountManager;
import seedu.address.model.Model;
import seedu.address.model.account.Account;

/**
 * Log user in with his username and password to gain admin access to the system.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final Prefix PREFIX_USERNNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("p/");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log in with your username and password. "
            + "Parameters: "
            + PREFIX_USERNNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNNAME + "heartsquare "
            + PREFIX_PASSWORD + "H3artSquar3";

    public static final String MESSAGE_SUCCESS = "Successfully log in.";
    public static final String MESSAGE_FAILURE = "Login failed. Please check your username or password and try again.";

    private Account account;

    public LoginCommand(Account account) {
        this.account = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        AccountManager accountManager = new AccountManager();
        if(accountManager.loginWithAccountSucceed(account)) {
            return new CommandResult(MESSAGE_SUCCESS);
        }

        throw new CommandException(MESSAGE_FAILURE);
    }
}
