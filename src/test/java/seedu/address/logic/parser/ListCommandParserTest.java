package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ListClientCommandParser.contactFilterClient;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.contact.EntryContainsKeywordsPredicate;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListClientCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(new EntryContainsKeywordsPredicate(new ArrayList<>()),
                contactFilterClient);

        // empty string
        assertParseSuccess(parser, "", expectedListCommand);

        // multiple whitespaces
        assertParseSuccess(parser, "     ", expectedListCommand);

        // new lines
        assertParseSuccess(parser, " \n  \n  ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(new EntryContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")), contactFilterClient);
        assertParseSuccess(parser, "Alice Bob", expectedListCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedListCommand);
    }

}
