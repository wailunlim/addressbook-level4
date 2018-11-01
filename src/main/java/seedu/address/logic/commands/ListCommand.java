package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.DeselectRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;

/**
 * Finds and lists all contacts in address book which contain all of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD_GENERAL = "%1$s list";
    public static final String COMMAND_WORD_CLIENT = "client list";
    public static final String COMMAND_WORD_SERVICE_PROVIDER = "serviceprovider list";

    public static final String MESSAGE_USAGE = COMMAND_WORD_GENERAL + ": Lists all %1$ss which contain all of "
            + "the specified keywords (case-insensitive) and displays them as a list with their IDs.\n"
            + "Parameters: n/[KEYWORD] p/[KEYWORD] e/[KEYWORD] a/[KEYWORD] t/[KEYWORD] ...\n"
            + "Example: " + COMMAND_WORD_GENERAL + " n/alice bob charlie";

    private final ContactContainsKeywordsPredicate predicate;
    private final ContactType contactType;

    public ListCommand(ContactContainsKeywordsPredicate predicate, ContactType contactType) {
        this.predicate = predicate;
        this.contactType = contactType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (predicate.equals(new ContactContainsKeywordsPredicate())) {
            model.updateFilteredContactList(contactType.getFilter());
            return new CommandResult(String.format(Messages.MESSAGE_LIST_ALL_X, contactType));
        }

        model.updateFilteredContactList(contactType.getFilter().and(predicate));
        EventsCenter.getInstance().post(new DeselectRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredContactList().size(),
                        contactType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
