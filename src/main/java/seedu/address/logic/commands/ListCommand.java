package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.EntryContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final EntryContainsKeywordsPredicate predicate;
    private final Predicate<Contact> contactFilter;

    public ListCommand(EntryContainsKeywordsPredicate predicate, Predicate<Contact> contactFilter) {
        this.predicate = predicate;
        this.contactFilter = contactFilter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (predicate.equals(new EntryContainsKeywordsPredicate(Collections.emptyList()))) {
            model.updateFilteredContactList(contactFilter);
            return new CommandResult(Messages.MESSAGE_LIST_ALL_PERSON);
        }

        model.updateFilteredContactList(contact -> contactFilter.test(contact) && predicate.test(contact));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
