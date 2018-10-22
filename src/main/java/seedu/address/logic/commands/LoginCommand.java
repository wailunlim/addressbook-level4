package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.security.AccountManager;
import seedu.address.model.Model;
import seedu.address.model.account.Account;

import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_SUCCESS = "Successfully logged in.";
    public static final String MESSAGE_FAILURE = "Login failed. Please check your username or password and try again.";

    private Account account;
    private Path accountListPath;

    /**
     * Create a new LoginCommand with default path to AccountList.
     * @param account The account to login with this command.
     */
    public LoginCommand(Account account) {
        requireNonNull(account);
        this.account = account;
        this.accountListPath = null;
    }

    /**
     * Create a new Login Command with the path to AccountList specified.
     * @param account The account to login with this command.
     * @param accountListPath The path to find the AccountList to compare the account with.
     */
    public LoginCommand(Account account, Path accountListPath) {
        requireNonNull(account);
        this.account = account;
        this.accountListPath = accountListPath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        AccountManager accountManager = new AccountManager(accountListPath);
        if (accountManager.loginWithAccountSucceed(account)) {
            return new CommandResult(MESSAGE_SUCCESS);
        }

        throw new CommandException(MESSAGE_FAILURE);
    }
}
