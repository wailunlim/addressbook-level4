package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ListClientCommandParser.CONTACT_FILTER_CLIENT;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.contact.ContactInformation;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListClientCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(new ContactContainsKeywordsPredicate(),
                CONTACT_FILTER_CLIENT);

        // empty string
        assertParseSuccess(parser, "", expectedListCommand);

        // multiple whitespaces
        assertParseSuccess(parser, "     ", expectedListCommand);

        // new lines
        assertParseSuccess(parser, " \n  \n  ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsListCommand() throws Exception {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("Alice Bob"),
                        Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>())),
                        CONTACT_FILTER_CLIENT);

        assertParseSuccess(parser, " n/Alice Bob", expectedListCommand);
    }

}
