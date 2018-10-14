package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.account.Role;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws LackOfPrivilegeException {
        requireNonNull(model);

        if (!Role.hasDeletePrivilege()) {
            throw new LackOfPrivilegeException(COMMAND_WORD);
        }

        model.resetData(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
