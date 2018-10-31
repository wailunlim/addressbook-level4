package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ClearBrowserPanelRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Selects a client identified using it's displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the client identified by the index number used in the displayed client list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Client: %1$s";

    private final Index id;
    private final ContactType contactType;

    public SelectCommand(Index id, ContactType contactType) {
        this.id = id;
        this.contactType = contactType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Contact> filteredContactList = model.getFilteredContactList();

        model.updateFilteredContactList(contactType.getFilter()
                .and(contact -> contact.getId() == id.getOneBased()));

        List<Contact> filteredList = model.getFilteredContactList();

        if (filteredList.size() == 0) {
            // filtered list size is 0, meaning there is no such contact
            model.updateFilteredContactList(contactType.getFilter());
            EventsCenter.getInstance().post(new ClearBrowserPanelRequestEvent());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (filteredList.size() > 1) {
            throw new RuntimeException("ID is not unique!");
        }

        // filtered list size is 1 (unique ID for client/serviceprovider)
        Contact contactToSelect = filteredList.get(0);
        model.updateFilteredContactList(contactType.getFilter());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(Index.fromZeroBased(model.getFilteredContactList()
                .indexOf(contactToSelect))));
        return new CommandResult((String.format(MESSAGE_SELECT_PERSON_SUCCESS, id.getOneBased())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && id.equals(((SelectCommand) other).id)); // state check
    }
}
