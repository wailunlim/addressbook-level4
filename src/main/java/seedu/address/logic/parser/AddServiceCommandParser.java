package seedu.address.logic.parser;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddServiceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ContactType;
import seedu.address.model.contact.Service;

/**
 * Parses input arguments and creates a new AddServiceCommand object
 */
public class AddServiceCommandParser implements Parser<AddServiceCommand> {
    private final ContactType contactType;

    public AddServiceCommandParser(ContactType contactType) {
        this.contactType = contactType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddServiceCommand
     * and returns an AddServiceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddServiceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SERVICE, PREFIX_COST);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format
                    (MESSAGE_INVALID_COMMAND_FORMAT, AddServiceCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_SERVICE, PREFIX_COST)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddServiceCommand.MESSAGE_USAGE));
        }

        Optional<String> serviceName = argMultimap.getValue(PREFIX_SERVICE);
        Optional<String> serviceCost = argMultimap.getValue(PREFIX_COST);

        if (serviceName.isPresent() && serviceCost.isPresent()) {
            Service service = new Service(serviceName.get(), parseInt(serviceCost.get()));
            return new AddServiceCommand(index, service, contactType);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddServiceCommand.MESSAGE_USAGE));
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
