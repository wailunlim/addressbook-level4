package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DeselectRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.security.PasswordAuthentication;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.storage.AccountStorage;
import seedu.address.storage.XmlAccountStorage;

/**
 * Edit the password of the user.
 */
public class EditPasswordCommand extends Command {
    public static final String COMMAND_WORD = "change password";
    public static final Prefix PREFIX_OLDPASSWORD = new Prefix("o/");
    public static final Prefix PREFIX_NEWPASSWORD = new Prefix("n/");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit your current account password. "
            + "Parameters: "
            + PREFIX_OLDPASSWORD + "OLD_PASSWORD "
            + PREFIX_NEWPASSWORD + "NEW_PASSWORD "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_OLDPASSWORD + "thisIsMyOldPassw0rd "
            + PREFIX_NEWPASSWORD + "thisIsMyNewPassw0rd";

    public static final String MESSAGE_SUCCESS = "Password changed successfully.";
    public static final String MESSAGE_FAILURE_PASSWORDWRONG = "Failed to change your password. "
            + "Old password was not correct.";
    public static final String MESSAGE_FAILURE_FILENOTFOUND = "Failed to change your password. Unable to find"
            + "your current account file path.";
    public static final String MESSAGE_FAILURE_SAMEPASSWORD = "Failed to change your password. "
            + "Your old password and new password is the same.";
    public static final String MESSAGE_FAILURE = "Failed to change your password.";

    private String userTypedOldPassword;
    private String userTypedNewPassword;
    private Path accountListPath;

    public EditPasswordCommand(String userTypedOldPassword, String userTypedNewPassword) {
        requireNonNull(userTypedOldPassword);
        requireNonNull(userTypedNewPassword);
        this.userTypedOldPassword = userTypedOldPassword;
        this.userTypedNewPassword = userTypedNewPassword;
        this.accountListPath = null;
    }

    public EditPasswordCommand(String userTypedOldPassword, String userTypedNewPassword, Path accountListPath) {
        requireNonNull(userTypedOldPassword);
        requireNonNull(userTypedNewPassword);
        this.userTypedOldPassword = userTypedOldPassword;
        this.userTypedNewPassword = userTypedNewPassword;
        this.accountListPath = accountListPath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Account currentAccount = model.getUserAccount();
        String username = currentAccount.getUserName();
        String actualOldPassword = currentAccount.getPassword();

        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

        if (!passwordAuthentication.authenticate(userTypedOldPassword.toCharArray(), actualOldPassword)) {
            throw new CommandException(MESSAGE_FAILURE_PASSWORDWRONG);
        }

        if (userTypedOldPassword.equals(userTypedNewPassword)) {
            throw new CommandException(MESSAGE_FAILURE_SAMEPASSWORD);
        }

        AccountStorage accountStorage = accountListPath == null
                ? new XmlAccountStorage()
                : new XmlAccountStorage(accountListPath);

        try {
            String hashedNewPassword = PasswordAuthentication.getHashedPasswordFromPlainText(userTypedNewPassword);

            accountStorage.updateAccountPassword(username, hashedNewPassword);
            model.commiteUserChangedPasswordSuccessfully(hashedNewPassword);
            EventsCenter.getInstance().post(new DeselectRequestEvent());
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE_FILENOTFOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditPasswordCommand // instanceof handles nulls
                && userTypedOldPassword.equals(((EditPasswordCommand) other).userTypedOldPassword)
                && userTypedNewPassword.equals(((EditPasswordCommand) other).userTypedNewPassword));
    }
}
