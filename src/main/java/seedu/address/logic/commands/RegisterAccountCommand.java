package seedu.address.logic.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.XmlAccountStorage;

/**
 * Register a new account and save the account into database. Only a SUPER_USER account
 * can register a new account.
 */
public class RegisterAccountCommand extends Command {
    public static final String COMMAND_WORD = "registeraccount";
    public static final Prefix PREFIX_USERNNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("p/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a new account. "
            + "Parameters: "
            + PREFIX_USERNNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_ROLE + "ROLE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNNAME + "newUserName "
            + PREFIX_PASSWORD + "newPassword "
            + PREFIX_ROLE + "superuser ";

    public static final String MESSAGE_SUCCESS = "Successfully registered the account.";
    public static final String MESSAGE_FAILURE = "Failed to register the new account. "
            + "Please make sure to use only \"r/superuser\" "
            + "or r/readonlyuser\" for role.";
    public static final String MESSAGE_FAILURE_FILENOTFOUND = "Failed to find the file to save account to.";
    public static final String MESSAGE_FAILURE_DUPLICATE = "Username is taken. Please try again with another username.";
    public static final String MESSAGE_INVALIDROLE = "Role should contains only"
            + "\"superuser\" or \"readonlyuser\".";

    private Account account;
    private Path accountListPath;

    /**
     * Creates a new RegisterAccountCommand with the account, and save the account to the default path.
     * @param account The account to be registered.
     */
    public RegisterAccountCommand(Account account) {
        this.account = account;
        this.accountListPath = null;
    }

    /**
     * Create  a new RegisterAccountCommand with the account, and save to the specified path.
     * @param account The account to be registered
     * @param accountListPath The Path to save the account to.
     */
    public RegisterAccountCommand(Account account, Path accountListPath) {
        this.account = account;
        this.accountListPath = accountListPath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException,
            LackOfPrivilegeException {
        if (!model.getUserAccount().hasAccountCreationPrivilege()) {
            throw new LackOfPrivilegeException(COMMAND_WORD);
        }

        AccountStorage accountStorage = accountListPath == null
                ? new XmlAccountStorage()
                : new XmlAccountStorage(accountListPath);

        try {
            if (accountStorage.getAccountList().hasUserName(account.getUserName())) {
                throw new CommandException(MESSAGE_FAILURE_DUPLICATE);
            }
            accountStorage.saveAccount(account);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException | DataConversionException e) {
            if (e instanceof FileNotFoundException) {
                throw new CommandException(MESSAGE_FAILURE_FILENOTFOUND);
            }
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterAccountCommand // instanceof handles nulls
                && account.equals(((RegisterAccountCommand) other).account));
    }
}
