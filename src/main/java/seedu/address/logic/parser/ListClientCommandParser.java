package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.EntryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListClientCommandParser extends ListCommandParser {

    public static final Predicate<Contact> CONTACT_FILTER_CLIENT = contact -> contact instanceof Client;

    @Override
    public ListCommand parse(String args) {
        return new ListCommand(new EntryContainsKeywordsPredicate(getListOfNameKeywords(args)), CONTACT_FILTER_CLIENT);
    }
}
