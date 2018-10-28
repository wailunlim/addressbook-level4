package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.EditPasswordCommand;

public class EditPasswordCommandParserTest {
    private static final String BLANK_SPACE = " ";
    private static final String OLD_PASSWORD = "@myPassword";
    private static final String NEW_PASSWORD = "s0meNewPassw0rd";
    private EditPasswordCommandParser parser = new EditPasswordCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPasswordCommand.MESSAGE_USAGE);

        // missing old password prefix
        assertParseFailure(parser, BLANK_SPACE + EditPasswordCommand.PREFIX_NEWPASSWORD.getPrefix()
                + NEW_PASSWORD, expectedMessage);

        // missing new password prefix
        assertParseFailure(parser, BLANK_SPACE + EditPasswordCommand.PREFIX_OLDPASSWORD.getPrefix()
                + OLD_PASSWORD, expectedMessage);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        EditPasswordCommand editPasswordCommand = new EditPasswordCommand(OLD_PASSWORD, NEW_PASSWORD);
        assertParseSuccess(parser, BLANK_SPACE + EditPasswordCommand.PREFIX_OLDPASSWORD.getPrefix()
                + OLD_PASSWORD + BLANK_SPACE + EditPasswordCommand.PREFIX_NEWPASSWORD.getPrefix()
                + NEW_PASSWORD, editPasswordCommand);
    }
}
