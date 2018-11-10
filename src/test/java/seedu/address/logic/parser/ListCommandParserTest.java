package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.ContactType;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.contact.ContactInformation;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser(ContactType.CLIENT);

    @Test
    public void parse_emptyArg_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand(new ContactContainsKeywordsPredicate(),
                ContactType.CLIENT);

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
                new ListCommand(new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("Alice Bob"),
                        Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>())),
                        ContactType.CLIENT);

        assertParseSuccess(parser, " n/Alice Bob", expectedListCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(ListCommand.MESSAGE_USAGE, ContactType.CLIENT)));
    }

}
