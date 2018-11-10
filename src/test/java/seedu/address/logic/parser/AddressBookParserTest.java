package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.UpdateCommand.COMMAND_WORD_GENERAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddServiceCommand;
import seedu.address.logic.commands.AutoMatchCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditPasswordCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RegisterAccountCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ContactType;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.contact.ContactInformation;
import seedu.address.model.contact.Service;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.VendorBuilder;

public class AddressBookParserTest {
    private static final String BLANK_SPACE = " ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Contact contact = new ClientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(contact));
        assertEquals(new AddCommand(contact), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_registeraccount() throws Exception {
        assertTrue(parser.parseCommand(RegisterAccountCommand.COMMAND_WORD + BLANK_SPACE
                + RegisterAccountCommand.PREFIX_USERNNAME + "user" + BLANK_SPACE
                + RegisterAccountCommand.PREFIX_PASSWORD + "pass" + BLANK_SPACE
                + RegisterAccountCommand.PREFIX_ROLE + "superuser") instanceof RegisterAccountCommand);
    }

    @Test
    public void parseCommand_logoutCommand() throws Exception {
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD) instanceof LogoutCommand);
    }

    @Test
    public void parseCommand_editPasswordCommand() throws Exception {
        assertTrue(parser.parseCommand(EditPasswordCommand.COMMAND_WORD + BLANK_SPACE
                + EditPasswordCommand.PREFIX_OLDPASSWORD + "k2" + BLANK_SPACE
                + EditPasswordCommand.PREFIX_NEWPASSWORD + "new" + BLANK_SPACE) instanceof EditPasswordCommand);
    }

    @Test
    public void parseCommand_exitCommand_afterLoggedIn() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitCommand_beforeLoggedIn() throws Exception {
        assertTrue(parser.parseCommandBeforeLoggedIn(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_loginCommand() throws Exception {
        assertTrue(parser.parseCommandBeforeLoggedIn(LoginCommand.COMMAND_WORD + BLANK_SPACE
                + LoginCommand.PREFIX_USERNNAME + "rootUser" + BLANK_SPACE
                + LoginCommand.PREFIX_PASSWORD + "rootPassword" + BLANK_SPACE) instanceof LoginCommand);
    }

    @Test
    public void parseCommand_helpCommand_beforeLoggedIn() throws Exception {
        assertTrue(parser.parseCommandBeforeLoggedIn(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#"
                        + INDEX_FIRST_PERSON.getOneBased()));
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON, ContactType.CLIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Contact contact = new ClientBuilder().build();
        UpdateCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(contact).build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased()) + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new UpdateCommand(INDEX_FIRST_PERSON, descriptor, ContactType.CLIENT), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        // No arguments
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate();
        ListCommand command = (ListCommand) parser.parseCommand(String.format(ListCommand.COMMAND_WORD_GENERAL,
                ContactType.CLIENT));
        assertEquals(new ListCommand(predicate, ContactType.CLIENT), command);

        // One Argument
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("Alice Bob"),
                Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>()));
        command = (ListCommand) parser.parseCommand(
                String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Alice Bob");
        assertEquals(new ListCommand(predicate, ContactType.CLIENT), command);

    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                "client#" + INDEX_FIRST_PERSON.getOneBased() + " view");
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON, ContactType.CLIENT), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_client_addservice() throws Exception {
        Contact contact = new ClientBuilder().build();
        Service service = new Service("photographer", "1000.00");
        AddServiceCommand command = (AddServiceCommand)
                parser.parseCommand(PersonUtil.getClientAddServiceCommand(contact, service, INDEX_FIRST_PERSON));
        assertEquals(new AddServiceCommand(INDEX_FIRST_PERSON, service, ContactType.CLIENT), command);
    }

    @Test
    public void parseCommand_vendor_addservice() throws Exception {
        Contact contact = new VendorBuilder().build();
        Service service = new Service("photographer", "1000.00");
        AddServiceCommand command = (AddServiceCommand)
                parser.parseCommand(PersonUtil.getServiceProviderAddServiceCommand(
                        contact, service, INDEX_FIRST_PERSON));
        assertEquals(new AddServiceCommand(INDEX_FIRST_PERSON, service, ContactType.VENDOR), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommand_updateCommandNoIdentifier_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(UpdateCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        parser.parseCommand(UpdateCommand.COMMAND_WORD_CLIENT + " n/test name");
    }

    @Test
    public void parseCommand_viewCommandNoIdentifier_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(SelectCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        parser.parseCommand(SelectCommand.COMMAND_WORD_CLIENT);
    }

    @Test
    public void parseCommand_addServiceCommandNoIdentifier_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        parser.parseCommand(AddServiceCommand.COMMAND_WORD_CLIENT);
    }

    @Test
    public void parseCommand_deleteCommandNoIdentifier_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(DeleteCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        parser.parseCommand(DeleteCommand.COMMAND_WORD_CLIENT);
    }

    @Test
    public void parseCommand_autoMatchCommandNoIdentifier_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AutoMatchCommand.MESSAGE_USAGE_CLIENT, ContactType.CLIENT, "#<ID>")));

        parser.parseCommand(AutoMatchCommand.COMMAND_WORD_CLIENT);
    }
}
