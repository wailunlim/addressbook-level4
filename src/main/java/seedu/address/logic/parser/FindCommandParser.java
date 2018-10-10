package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.EntryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public FindCommand parse(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new FindCommand(new EntryContainsKeywordsPredicate(Collections.emptyList()));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new EntryContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
