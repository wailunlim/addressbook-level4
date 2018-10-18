package seedu.address.logic.parser;

import seedu.address.logic.commands.MatchMakeCommand;

/**
 * Parses input arguments and creates a new MatchMakeCommand object
 */
public class MatchmakeCommandParser implements Parser<MatchMakeCommand> {

    private final int ENTITY_TYPE = 0;
    private final int ENTITY_ID = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the MatchMakeCommand
     * and returns an MatchMakeCommand object for execution.
     */
    public MatchMakeCommand parse(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new MatchMakeCommand(null, null);
        }

        String[] entity = trimmedArgs.split("#");
        String entityType = entity[ENTITY_TYPE];
        String entityId = entity[ENTITY_ID];

        return new MatchMakeCommand(entityType, entityId);
    }

}
