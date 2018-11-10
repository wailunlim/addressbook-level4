package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.logic.commands.DeleteCommand.COMMAND_WORD_GENERAL;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.TypicalAccount;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
    private Model modelWithoutWritePrivilege = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
            TypicalAccount.READ_ONLY_USER_ACCOUNT);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIdClientList_success() {
        // delete the first client
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.CLIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIdServiceProviderList_success() {
        // delete the first vendor. vendor == serviceprovider, no difference!
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.VENDOR);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentClientId_throwsCommandException() {
        // delete using a clientId that is utilised
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Index unusedId = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(unusedId, ContactType.CLIENT);

        assertCommandFailure(deleteCommand, model, commandHistory,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, unusedId.getOneBased()));
    }

    @Test
    public void execute_nonExistentVendorId_throwsCommandException() {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Index unusedId = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(unusedId, ContactType.VENDOR);

        assertCommandFailure(deleteCommand, model, commandHistory,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, unusedId.getOneBased()));
    }

    /**
     * deleting a person from heartsquare shows the client/vendor list depending on client#ID delete or
     * vendor#ID delete. Heartsquare's deletion of contacts are not relative to the list shown on the UI but
     * instead specifically client/vendor ID
     */
    @Test
    public void execute_clientShownInClientFilteredList_success() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.CLIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * deleting a person from heartsquare shows the client/vendor list depending on client#ID delete or
     * vendor#ID delete. Heartsquare's deletion of contacts are not relative to the list shown on the UI but
     * instead specifically client/vendor ID
     */
    @Test
    public void execute_serviceProviderShownInServiceProviderFilteredList_success() {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.VENDOR);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientNotShownInClientFilteredList_success() {
        // identify the 2nd client for deletion
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_SECOND_PERSON.getZeroBased());

        // show only the first client only
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        assertFalse(model.getFilteredContactList().contains(contactToDelete));
        Index idNotShownInCurrentList = Index.fromOneBased(contactToDelete.getId());

        DeleteCommand deleteCommand = new DeleteCommand(idNotShownInCurrentList, ContactType.CLIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteVendorNotShownInVendorFilteredList_success() {
        // identify the 2nd vendor for deletion
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_SECOND_PERSON.getZeroBased());

        // show only the first vendor only
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        assertFalse(model.getFilteredContactList().contains(contactToDelete));
        Index idNotShownInCurrentList = Index.fromOneBased(contactToDelete.getId());

        DeleteCommand deleteCommand = new DeleteCommand(idNotShownInCurrentList, ContactType.VENDOR);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_serviceProviderWhileShownClientFilteredList_success() {
        // identify vendor to delete
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());

        // switch to client list
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        assertTrue(!model.getFilteredContactList().contains(contactToDelete));

        // delete the vendor chosen previously
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.VENDOR);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);

        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        // deleting vendor switches the filtered contact list to vendor list
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientWhileShownServiceProviderFilteredList_success() {
        // identify client to delete
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());

        // switch to vendor list
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        assertTrue(!model.getFilteredContactList().contains(contactToDelete));

        //delete the client chosen previously
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.CLIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);

        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        //deleting client switches to client list
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIdFilteredClientList_success() throws Exception {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, ContactType.CLIENT);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();

        // delete -> first client in list deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and show client list
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first client deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIdFilteredVendorList_success() throws Exception {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, ContactType.VENDOR);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();

        // delete -> first client deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered vendor list
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first client deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIdClientList_failure() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Index unusedId = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(unusedId, ContactType.CLIENT);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, unusedId.getOneBased()));

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidIdVendorList_failure() {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, ContactType.VENDOR);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, outOfBoundIndex.getOneBased()));

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validIdWithDifferentFilteredList_sameClientDeleted() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());

        // choose to delete the first contact in the client list
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.CLIENT);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());

        // empty filtered list
        showNoPerson(model);

        // even with filtered list is empty, still can delete a contact using its ID
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);

        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // The contact after undoing is in the same position before deletion
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        assertEquals(model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()), contactToDelete);

        // show vendor list
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());

        // redo the deletion -- it doesn't matter what list is shown
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // the list is switched to client list
        // the contact at position one of the list is no longer contactToDelete
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        assertEquals(model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()).getType(),
                ContactType.CLIENT);
        assertNotEquals(model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()), contactToDelete);
    }

    @Test
    public void executeUndoRedo_validIdWithDifferentFilteredList_sameVendorDeleted() throws Exception {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());

        // choose to delete the first contact in the vendor list
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                ContactType.VENDOR);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, contactToDelete.getType(),
                contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.deleteContact(contactToDelete);
        expectedModel.commitAddressBook();
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());

        // empty filtered list
        showNoPerson(model);

        // even with filtered list is empty, still can delete a contact using its ID
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);

        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // The contact after undoing is in the same position before deletion
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        assertEquals(model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()), contactToDelete);

        // show vendor list
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());

        // redo the deletion -- it doesn't matter what list is shown
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // the list is switched to vendor list
        // the contact at position one of the list is no longer contactToDelete
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        assertEquals(model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()).getType(),
                ContactType.VENDOR);
        assertNotEquals(model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased()), contactToDelete);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON, ContactType.CLIENT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON, ContactType.CLIENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON, ContactType.CLIENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void execute_noDeletePrivilege_throwsLackOfPrivilegeException()
            throws CommandException, LackOfPrivilegeException {
        modelWithoutWritePrivilege.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact contactToDelete = modelWithoutWritePrivilege.getFilteredContactList()
                .get(INDEX_FIRST_PERSON.getZeroBased());

        thrown.expect(LackOfPrivilegeException.class);
        thrown.expectMessage(String.format(COMMAND_WORD_GENERAL, contactToDelete.getType(), "#<ID>"));
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(contactToDelete.getId()),
                contactToDelete.getType());
        deleteCommand.execute(modelWithoutWritePrivilege, commandHistory);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getFilteredContactList().isEmpty());
    }
}
