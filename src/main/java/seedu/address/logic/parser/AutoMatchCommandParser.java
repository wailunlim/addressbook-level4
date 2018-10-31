package seedu.address.logic.parser;

import seedu.address.logic.commands.AutoMatchCommand;

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
    public AutoMatchCommand parse(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new AutoMatchCommand(null, null);
        }

        String[] entity = trimmedArgs.split("#");
        String entityType = entity[ENTITY_TYPE];
        String entityId = entity[ENTITY_ID];

        return new AutoMatchCommand(entityType, entityId);
    }

}
