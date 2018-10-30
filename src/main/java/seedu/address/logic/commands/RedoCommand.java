package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        List<Contact> listBeforeRedo = new ArrayList<>(model.getAddressBook().getContactList());
        model.redoAddressBook();
        List<Contact> listAfterRedo = model.getAddressBook().getContactList();

        ContactType toFilter = CollectionUtil.compareListOfContacts(listAfterRedo, listBeforeRedo);

        model.updateFilteredContactList(toFilter.getFilter());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
