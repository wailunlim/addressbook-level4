package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ContactType;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private final ContactType contactType;

    public DeleteCommandParser(ContactType contactType) {
        this.contactType = contactType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index id = ParserUtil.parseIndex(requireNonNull(args).substring(1));
            return new DeleteCommand(id, contactType);
        } catch (NullPointerException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            String.format(DeleteCommand.MESSAGE_USAGE, contactType, "#<ID>")), e);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, args.substring(1)));
        }
    }

}
