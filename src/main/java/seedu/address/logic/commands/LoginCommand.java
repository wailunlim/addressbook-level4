package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;
import seedu.address.model.account.Role;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.XmlAccountStorage;

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
    private static final Logger logger = LogsCenter.getLogger(LoginCommand.class);

    private String username;
    private String password;
    private Path accountListPath;

    /**
     * Create a new LoginCommand with default path to AccountList.
     */
    public LoginCommand(String username, String password) {
        requireNonNull(username);
        requireNonNull(password);
        this.username = username;
        this.password = password;
        this.accountListPath = null;
    }

    /**
     * Create a new Login Command with the path to AccountList specified.
     */
    public LoginCommand(String username, String password, Path accountListPath) {
        requireNonNull(username);
        requireNonNull(password);
        this.username = username;
        this.password = password;
        this.accountListPath = accountListPath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        AccountStorage accountStorage = accountListPath == null
                ? new XmlAccountStorage()
                : new XmlAccountStorage(accountListPath);

        try {
            AccountList accountList = accountStorage.getAccountList();

            if (accountList.hasUsernameAndPassword(username, password)) {
                Role userAccountRole = accountList.getAccountRole(username);
                Account accountToCommit = new Account(username, password, userAccountRole);
                accountToCommit.transformToHashedAccount();
                model.commitUserLoggedInSuccessfully(accountToCommit);
                EventsCenter.getInstance().post(new DeselectRequestEvent());
                UserPrefs.setUsernameToDisplay(username);
                return new CommandResult(MESSAGE_SUCCESS);
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
        } catch (IOException e2) {
            logger.warning("Problem while reading from the file containing all the accounts");
        }

        throw new CommandException(MESSAGE_FAILURE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && username.equals(((LoginCommand) other).username)
                && password.equals(((LoginCommand) other).password));
    }
}
