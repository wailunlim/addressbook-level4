package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.EntryContainsKeywordsPredicate;
import seedu.address.model.serviceprovider.ServiceProvider;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListServiceProviderCommandParser extends ListCommandParser {
    public static final Predicate<Contact> CONTACT_FILTER_SERVICE_PROVIDER = contact ->
            contact instanceof ServiceProvider;

    @Override
    public ListCommand parse(String args) {
        return new ListCommand(new EntryContainsKeywordsPredicate(getListOfNameKeywords(args)),
                CONTACT_FILTER_SERVICE_PROVIDER);
    }
}
