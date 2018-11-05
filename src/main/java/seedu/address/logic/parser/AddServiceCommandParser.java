package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE;

import java.util.List;
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
        Index id;
        try {
            id = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, argMultimap.getPreamble()));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_SERVICE, PREFIX_COST)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(AddServiceCommand.MESSAGE_USAGE, contactType, "#<ID>")));
        }

        List<String> allServiceNames = argMultimap.getAllValues(PREFIX_SERVICE);
        List<String> allServiceCost = argMultimap.getAllValues(PREFIX_COST);

        // Guard against missing or extraneous service name/cost
        if (allServiceNames.size() != 1 || allServiceCost.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(AddServiceCommand.MESSAGE_USAGE, contactType, "#<ID>")));
        }

        String serviceName = allServiceNames.get(0).toLowerCase();
        String serviceCost = allServiceCost.get(0);

        // Guard against invalid service types
        if (!Service.isValidServiceName(serviceName)) {
            throw new ParseException(Service.MESSAGE_SERVICE_NAME_CONSTRAINTS);
        }
        if (!Service.isValidServiceCost(serviceCost)) {
            throw new ParseException(Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        }

        Service service = new Service(serviceName, serviceCost);
        return new AddServiceCommand(id, service, contactType);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
