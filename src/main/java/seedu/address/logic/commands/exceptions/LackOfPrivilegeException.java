package seedu.address.logic.commands.exceptions;

/**
 * Represents an error when a user whose account does not have certain privileges tries to
 * execute certain commands meant for account with those privileges.
 * See {@link seedu.address.model.account.Role}.
 */
public class LackOfPrivilegeException extends Exception {
    public LackOfPrivilegeException(String commandName) {
        super("You do not have privilege to access \'" + commandName + "\' command.");
    }
}
