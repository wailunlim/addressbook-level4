package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.UpdateCommand.COMMAND_WORD_GENERAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalContacts.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.PersonUtil;

public class UpdateCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = String.format(COMMAND_WORD_GENERAL, ContactType.CLIENT, "#" + index.getOneBased())
                + NAME_DESC_BOB + "  " + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " "
                + TAG_DESC_HUSBAND + " ";
        Contact editedContact = new ClientBuilder(BOB).withTags(VALID_TAG_HUSBAND).withId(1).build();
        assertCommandSuccess(command, index, editedContact);

        /* Case: undo editing the last client in the list -> last client restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last client in the list -> last client edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateContact(
                getModel().getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()), editedContact);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a client with new values same as existing values -> edited */
        command = String.format(COMMAND_WORD_GENERAL, ContactType.CLIENT, "#" + index.getOneBased())
                + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a client with new values same as another client's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getContactList().contains(BOB));
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredContactList().get(index.getZeroBased()), BOB);
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + index.getOneBased()) + NAME_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedContact = new ClientBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedContact);

        /* Case: edit a client with new values same as another client's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_PERSON;
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + index.getOneBased()) + NAME_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedContact = new ClientBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedContact);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + index.getOneBased()) + " " + PREFIX_TAG.getPrefix();
        Contact contactToEdit = getModel().getFilteredContactList().get(index.getZeroBased());
        editedContact = new ClientBuilder(contactToEdit).withTags().build();
        assertCommandSuccess(command, index, editedContact);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered client list, edit the client filtered (client#<id of client shown> _> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredContactList().size());
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + getModel().getFilteredContactList().get(0).getId())
                + " " + NAME_DESC_BOB;
        contactToEdit = getModel().getFilteredContactList().get(index.getZeroBased());
        editedContact = new ClientBuilder(contactToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedContact);

        /* Case: filtered client list, edit index within bounds of address book but out of bounds of client list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getContactList().size();
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + invalidIndex) + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, invalidIndex));

        /* --------------------- Performing edit operation while a client card is selected -------------------------- */

        /* Case: selects first card in the client list, edits a client -> no card should be selected */



        //TODO: How is select going to work now?
        /* Case: selects first card in the client list, edit a client -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllClients();
        index = INDEX_FIRST_PERSON;
        // selectPerson(index);
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + index.getOneBased()) + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new client's name
        // selectPerson(index);
        executeCommand(command);

        // assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#0") + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(UpdateCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#-1") + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(UpdateCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredContactList().size() + 1;
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + invalidIndex) + " " + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, invalidIndex));

        /* Case: unknown command -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + NAME_DESC_BOB, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: missing all fields -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased()),
                UpdateCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + INVALID_PHONE_DESC,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + INVALID_EMAIL_DESC,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + INVALID_ADDRESS_DESC,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + INDEX_FIRST_PERSON.getOneBased())
                        + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a client with new values same as another client's values -> rejected */
        executeCommand(PersonUtil.getAddClientCommand(BOB));
        assertTrue(getModel().getAddressBook().getContactList().contains(BOB));
        Index id = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredContactList().get(id.getZeroBased()).equals(BOB));
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + id.getOneBased()) + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_CONTACT);

        /* Case: edit a client with new values same as another client's values but with different tags -> rejected */
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + id.getOneBased()) + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_CONTACT);

        /* Case: edit a client with new values same as another client's values but with different address -> rejected */
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + id.getOneBased()) + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_CONTACT);

        /* Case: edit a client with new values same as another client's values but with different phone -> rejected */
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + id.getOneBased()) + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_CONTACT);

        /* Case: edit a client with new values same as another client's values but with different email -> rejected */
        command = String.format(COMMAND_WORD_GENERAL,
                ContactType.CLIENT, "#" + id.getOneBased()) + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Client, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Index, Contact, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Contact editedContact) {
        assertCommandSuccess(command, toEdit, editedContact, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code UpdateCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the client at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Contact editedContact,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateContact(expectedModel.getFilteredContactList().get(toEdit.getZeroBased()), editedContact);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());

        assertCommandSuccess(command, expectedModel,
                String.format(UpdateCommand.MESSAGE_EDIT_CONTACT_SUCCESS, ContactType.CLIENT, editedContact),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
