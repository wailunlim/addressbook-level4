package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CONTACT_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CONTACT_TYPE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AutoMatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AutoMatchCommandParserTest {

    private AutoMatchCommandParser parser = new AutoMatchCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() throws ParseException {
        assertParseSuccess(parser, "client#1", new AutoMatchCommand("client", 1));
    }

    @Test
    public void parse_invalidContactFormat_throwsParseException() {
        assertParseFailure(parser, "client1", String.format(MESSAGE_INVALID_CONTACT_FORMAT, "client1"));
    }

    @Test
    public void parse_invalidContactType_throwsParseException() {
        assertParseFailure(parser, "dog#1", String.format(MESSAGE_INVALID_CONTACT_TYPE, "dog"));
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        assertParseFailure(parser, "client#a", String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, "a"));
    }
}
