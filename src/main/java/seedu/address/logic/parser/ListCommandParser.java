package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ContactType;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.contact.ContactInformation;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    private final ContactType contactType;

    public ListCommandParser(ContactType contactType) {
        this.contactType = contactType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = createLegalArgumentMultimap(args);

        Optional<String> name = argMultimap.getValue(PREFIX_NAME);
        Optional<String> phone = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);
        Optional<String> address = argMultimap.getValue(PREFIX_ADDRESS);
        List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);

        return new ListCommand(new ContactContainsKeywordsPredicate(
                new ContactInformation(name, phone, email, address, tagList)), contactType);
    }

    /**
     * Creates a {@code ArgumentMultimap} using the arguments from the input.
     * @param args The arguments from the input
     * @return the {@code ArgumentMultimap} generated using the input arguments.
     * @throws ParseException If a legal {@code ArgumentMultimap} is not able to be created due to an invalid command
     *     format.
     */
    protected ArgumentMultimap createLegalArgumentMultimap(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG)
                && !argMultimap.isEmpty())
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(ListCommand.MESSAGE_USAGE, contactType)));
        }

        return argMultimap;
    }


    /**
     * Returns true if at least one of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> !argumentMultimap.getValue(prefix).orElse("empty").equals(""));
    }
}
