package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.AddServiceCommand.COMMAND_WORD_GENERAL;
import static seedu.address.logic.commands.AddServiceCommand.MESSAGE_DUPLICATE_SERVICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SERVICE_COST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SERVICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SERVICE_COST_DESC_MID;
import static seedu.address.logic.commands.CommandTestUtil.SERVICE_COST_DESC_RING;
import static seedu.address.logic.commands.CommandTestUtil.SERVICE_DESC_HOTEL;
import static seedu.address.logic.commands.CommandTestUtil.SERVICE_DESC_RING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_COST_MID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_HOTEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.GEORGE;
import static seedu.address.testutil.TypicalContacts.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddServiceCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;
import seedu.address.testutil.ClientBuilder;

public class AddServiceCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addService() {
        Model model = getModel();
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());

        /* ---------------- Performing add service operation while an unfiltered list is being shown --------------- */

        /* Case: add a service, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> service added
         */
        Service hotelService = new Service(VALID_SERVICE_HOTEL, VALID_SERVICE_COST_MID);
        Index index = INDEX_FIRST_PERSON;
        String command = String.format(COMMAND_WORD_GENERAL, ContactType.CLIENT, "#" + index.getOneBased())
                + "   " + SERVICE_DESC_HOTEL + "  " + SERVICE_COST_DESC_MID + "    ";
        Contact editedContact = new ClientBuilder(ALICE).withServices(hotelService.toString()).build();
        assertCommandSuccess(command, index, editedContact, hotelService);

        /* Case: undo adding service to the previous client in the list -> previous client restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding service to the previous client in the list -> service added to previous client again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateContact(
                getModel().getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()), editedContact);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* ------------------ Performing add service operation while a filtered list is being shown ---------------- */

        /* Case: filtered client list, add service to the client filtered (client#<id of client shown>) */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredContactList().size());
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + getModel().getFilteredContactList().get(0).getId())
                + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID;
        Contact contactToEdit = getModel().getFilteredContactList().get(index.getZeroBased());
        editedContact = new ClientBuilder(contactToEdit).withServices(hotelService.toString()).build();
        assertCommandSuccess(command, index, editedContact, hotelService);

        /* Case: filtered client list, edit index within bounds of address book but out of bounds of client list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getContactList().size();
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + invalidIndex) + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, invalidIndex));

        /* --------------------------------- Performing invalid add service operation ------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#0") + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#-1") + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredContactList().size() + 1;
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + invalidIndex) + " " + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, invalidIndex));

        /* Case: missing index -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL, ContactType.CLIENT, "# ")
                        + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased()),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        /* Case: invalid service type -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + INVALID_SERVICE_DESC + SERVICE_COST_DESC_MID,
                Service.MESSAGE_SERVICE_NAME_CONSTRAINTS);

        /* Case: invalid service cost -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + SERVICE_DESC_HOTEL + INVALID_SERVICE_COST_DESC,
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "88.888",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "88.88.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "$8888.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        /* Case: add a duplicate service -> rejected */
        assertTrue(getModel().getAddressBook().getContactList().contains(GEORGE));
        int id = getModel().getFilteredContactList().size();

        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + id) + SERVICE_DESC_RING + SERVICE_COST_DESC_MID,
                MESSAGE_DUPLICATE_SERVICE);

        /* Case: wrong order -> rejected */
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + id + SERVICE_DESC_RING + SERVICE_COST_DESC_RING);
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Client, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see AddServiceCommandSystemTest#assertCommandSuccess(String, Index, Contact, Index, Service)
     */
    private void assertCommandSuccess(String command, Index toEdit, Contact editedContact, Service service) {
        assertCommandSuccess(command, toEdit, editedContact, null, service);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code AddServiceCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the client at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see AddServiceCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Contact editedContact,
                                      Index expectedSelectedCardIndex, Service service) {
        Model expectedModel = getModel();
        expectedModel.updateContact(expectedModel.getFilteredContactList().get(toEdit.getZeroBased()), editedContact);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());

        assertCommandSuccess(command, expectedModel,
                String.format(AddServiceCommand.MESSAGE_ADD_SERVICE_SUCCESS, editedContact.getName(), service),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see AddServiceCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
