package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AutoMatchResult;
import seedu.address.model.contact.Contact;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     * @throws LackOfPrivilegeException If the user account does not have the privilege to
     * execute that command.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, LackOfPrivilegeException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Contact> getFilteredPersonList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /**
     * Retrieves the last updated results for the auto-matching.
     */
    AutoMatchResult getAutoMatchResult();
}
