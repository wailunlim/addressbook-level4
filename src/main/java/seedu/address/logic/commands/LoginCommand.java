package seedu.address.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
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
            + PREFIX_USERNNAME + "heartsquare"
            + PREFIX_PASSWORD + "H3artSquar3";

    public static final String MESSAGE_SUCCESS = "Successfully log in.";
    public static final String MESSAGE_FAILURE = "Login failed. Please check your username or password and try again.";

    private static final Logger logger = LogsCenter.getLogger(LoginCommand.class);

    private Account account;
    private AccountStorage accountStorage;

    public LoginCommand(Account account) {
        this.account = account;
        accountStorage = new XmlAccountStorage();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            if (accountStorage.getAccountList().hasAccount(account)) {
                return new CommandResult(MESSAGE_SUCCESS);
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
            throw new CommandException(MESSAGE_FAILURE);
        } catch (IOException e2) {
            logger.warning("Problem while reading from the file containing all the accounts");
            throw new CommandException(MESSAGE_FAILURE);
        }

        throw new CommandException(MESSAGE_FAILURE);
    }
}
