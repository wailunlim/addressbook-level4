package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.contact.EntryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public ListCommand parse(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new ListCommand(new EntryContainsKeywordsPredicate(Collections.emptyList()));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ListCommand(new EntryContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
