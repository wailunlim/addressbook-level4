package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RegisterAccountCommand;
import seedu.address.model.account.Account;
import seedu.address.model.account.Role;
import seedu.address.testutil.TypicalAccount;

public class RegisterAccountCommandParserTest {
    private static final String BLANK_SPACE = " ";
    private static final Account rootAccount = TypicalAccount.ROOTACCOUNT;
    private RegisterAccountCommandParser parser = new RegisterAccountCommandParser();

    @Test
    public void parse_allFieldsPresent_successSuperUser() {
        RegisterAccountCommand registerAccountCommand = new RegisterAccountCommand(new Account("username",
                "password", Role.SUPER_USER));
        assertParseSuccess(parser, BLANK_SPACE + RegisterAccountCommand.PREFIX_USERNNAME.getPrefix()
                + "username" + BLANK_SPACE + RegisterAccountCommand.PREFIX_PASSWORD.getPrefix()
                + "password" + BLANK_SPACE + RegisterAccountCommand.PREFIX_ROLE + "superuser",
                registerAccountCommand);
    }

    @Test
    public void parse_allFieldsPresent_successReadOnlyUser() {
        RegisterAccountCommand registerAccountCommand = new RegisterAccountCommand(new Account("username",
                "password", Role.READ_ONLY_USER));
        assertParseSuccess(parser, BLANK_SPACE + RegisterAccountCommand.PREFIX_USERNNAME.getPrefix()
                        + "username" + BLANK_SPACE + RegisterAccountCommand.PREFIX_PASSWORD.getPrefix()
                        + "password" + BLANK_SPACE + RegisterAccountCommand.PREFIX_ROLE + "readonlyuser",
                registerAccountCommand);
    }

    @Test
    public void parse_invalidWrong_parseException() {
        String expectedMessage = RegisterAccountCommand.MESSAGE_INVALIDROLE;
        // missing username prefix
        assertParseFailure(parser, BLANK_SPACE + RegisterAccountCommand.PREFIX_USERNNAME.getPrefix()
                        + "username" + BLANK_SPACE + RegisterAccountCommand.PREFIX_PASSWORD.getPrefix()
                        + "password" + BLANK_SPACE + RegisterAccountCommand.PREFIX_ROLE + "invalidRole",
                expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterAccountCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, BLANK_SPACE + RegisterAccountCommand.PREFIX_PASSWORD.getPrefix()
                + rootAccount.getPassword() + BLANK_SPACE + RegisterAccountCommand.PREFIX_ROLE + "superuser",
                expectedMessage);

        // missing password prefix
        assertParseFailure(parser, BLANK_SPACE + RegisterAccountCommand.PREFIX_USERNNAME.getPrefix()
                + rootAccount.getUserName() + BLANK_SPACE + RegisterAccountCommand.PREFIX_ROLE
                + "superuser", expectedMessage);

        // missing role prefix
        assertParseFailure(parser, BLANK_SPACE + RegisterAccountCommand.PREFIX_USERNNAME.getPrefix()
                + rootAccount.getUserName() + BLANK_SPACE + RegisterAccountCommand.PREFIX_USERNNAME.getPrefix()
                + rootAccount.getUserName(), expectedMessage);
    }
}
