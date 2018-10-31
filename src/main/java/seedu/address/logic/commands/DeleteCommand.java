package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Deletes a client identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD_GENERAL = "%1$s%2$s delete";
    /*
    the below are necessary for switch statements as a constant expression is required for to compile switch
    statements
     */
    public static final String COMMAND_WORD_CLIENT = "client delete";
    public static final String COMMAND_WORD_SERVICE_PROVIDER = "serviceprovider delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD_GENERAL
            + ": Deletes the %1$s identified by the assigned unique %1$s ID.\n"
            + "Parameters: #<ID> (must be a positive integer)\n"
            + "Example: " + String.format(COMMAND_WORD_GENERAL, "%1$s", "#3");

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted %1$s: %2$s";

    private final Index id;
    private final ContactType contactType;

    public DeleteCommand(Index id, ContactType contactType) {
        this.id = id;
        this.contactType = contactType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException,
            LackOfPrivilegeException {
        requireNonNull(model);

        if (!model.getUserAccount().hasDeletePrivilege()) {
            throw new LackOfPrivilegeException(String.format(COMMAND_WORD_GENERAL, contactType, "#<ID>"));
        }

        model.updateFilteredContactList(contactType.getFilter()
                .and(contact -> contact.getId() == id.getOneBased()));

        List<Contact> filteredList = model.getFilteredContactList();

        if (filteredList.size() == 0) {
            // filtered list size is 0, meaning there is no such contact
            model.updateFilteredContactList(contactType.getFilter());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (filteredList.size() > 1) {
            throw new RuntimeException("ID is not unique!");
        }

        // filtered list size is 1 (unique ID for client/serviceprovider)
        Contact contactToDelete = filteredList.get(0);
        model.deleteContact(contactToDelete);
        model.updateFilteredContactList(contactType.getFilter());
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && id.equals(((DeleteCommand) other).id)); // state check
    }
}
