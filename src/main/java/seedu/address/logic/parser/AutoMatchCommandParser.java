package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CONTACT_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.logic.commands.AutoMatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AutoMatchCommand} object
 */
public class AutoMatchCommandParser implements Parser<AutoMatchCommand> {

    private static final int ENTITY_TYPE = 0;
    private static final int ENTITY_ID = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AutoMatchCommand} and returns an
     * {@code AutoMatchCommand} object for execution.
     */
    public AutoMatchCommand parse(String args) throws ParseException, NumberFormatException {

        String[] entity = args.trim().split("#");
        String entityType = entity[ENTITY_TYPE];
        String entityId;

        try {
            entityId = entity[ENTITY_ID];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_CONTACT_FORMAT, args));
        }

        try {
            return new AutoMatchCommand(entityType, Integer.parseInt(entityId));
        } catch (NumberFormatException exception) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, entityId));
        }
    }

}
