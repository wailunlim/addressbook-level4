package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.contact.EntryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public abstract class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
    public abstract ListCommand parse(String args);

    /**
     * Given {@code String} args, get the list of name keywords in a list form.
     * @param args String to retrieve the name keywords from
     * @return A list of the name keywords
     */
    List<String> getListOfNameKeywords(String args) {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return Collections.emptyList();
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return Arrays.asList(nameKeywords);
    }
}
