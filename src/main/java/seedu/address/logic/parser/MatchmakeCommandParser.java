package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;

import seedu.address.logic.commands.MatchmakeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MatchmakeCommand object
 */
public class MatchmakeCommandParser implements Parser<MatchmakeCommand> {

    private final int ENTITY_TYPE = 0;
    private final int ENTITY_ID = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the MatchmakeCommand
     * and returns an MatchmakeCommand object for execution.
     */
    public MatchmakeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new MatchmakeCommand(null, null);
        }

        String[] entity = trimmedArgs.split("#");
        String entityType = entity[ENTITY_TYPE];
        String entityId = entity[ENTITY_ID];

        return new MatchmakeCommand(entityType, entityId);
    }

}
