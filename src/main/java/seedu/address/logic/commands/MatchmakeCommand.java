package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;
import seedu.address.model.serviceprovider.ServiceProvider;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class MatchmakeCommand extends Command {

    public static final String COMMAND_WORD = "matchmake";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final String entityType;
    private final String entityId;

    public MatchmakeCommand(String entityType, String entityId) {
        this.entityType = entityType;
        this.entityId = entityId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        // Update filtered contact list to contain all contacts and get the list
        model.updateFilteredContactList(x -> true);
        model.getFilteredContactList();

        // Find the contact for which we are going to matchmake
        int contactId = Integer.parseInt(entityId);
        Contact contact = model.getAddressBook().getContactList().get(contactId);

        // Filter
        model.updateFilteredContactList(anotherContact -> {
            if (contact instanceof Client && anotherContact instanceof ServiceProvider) {
                return true;
            }
            if (contact instanceof ServiceProvider && anotherContact instanceof Client) {
                return true;
            }
            return false;
        });

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchmakeCommand // instanceof handles nulls
                && entityType.equals(((MatchmakeCommand) other).entityType)
                && entityId.equals(((MatchmakeCommand) other).entityId)); // state check
    }
}
