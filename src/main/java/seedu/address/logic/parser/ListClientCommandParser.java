package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.contact.ContactInformation;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListClientCommandParser extends ListCommandParser {

    public static final Predicate<Contact> CONTACT_FILTER_CLIENT = contact -> contact instanceof Client;

    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = createLegalArgumentMultimap(args);

        Optional<String> name = argMultimap.getValue(PREFIX_NAME);
        Optional<String> phone = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);
        Optional<String> address =  argMultimap.getValue(PREFIX_ADDRESS);
        List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);

        return new ListCommand(new ContactContainsKeywordsPredicate(
                new ContactInformation(name, phone, email, address, tagList)), CONTACT_FILTER_CLIENT);
    }
}
