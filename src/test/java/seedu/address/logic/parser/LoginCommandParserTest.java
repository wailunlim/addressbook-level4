package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.account.Account;
import seedu.address.testutil.TypicalAccount;

public class LoginCommandParserTest {
    private static final String BLANK_SPACE = " ";
    private static final Account rootAccount = TypicalAccount.ROOTACCOUNT;
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        LoginCommand loginCommand = new LoginCommand(rootAccount);
        assertParseSuccess(parser, BLANK_SPACE + LoginCommand.PREFIX_USERNNAME.getPrefix()
                + rootAccount.getUserName() + BLANK_SPACE + LoginCommand.PREFIX_PASSWORD.getPrefix()
                + rootAccount.getPassword(), loginCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, BLANK_SPACE + LoginCommand.PREFIX_PASSWORD.getPrefix()
                        + rootAccount.getPassword(), expectedMessage);
        // missing password prefix
        assertParseFailure(parser, BLANK_SPACE + LoginCommand.PREFIX_USERNNAME.getPrefix()
                        + rootAccount.getUserName(), expectedMessage);
    }
}
