package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_COST_MID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_COST_MIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_HOTEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_PHOTO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.DOMINIC;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.TypicalAccount;
import seedu.address.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * AddServiceCommand.
 */
public class AddServiceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
    private CommandHistory commandHistory = new CommandHistory();
    private Service validService = new Service(VALID_SERVICE_PHOTO, VALID_SERVICE_COST_MIN);

    @Test
    public void execute_serviceAddedToClientWithNoServices_success() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact newClient = new ClientBuilder(ALICE).withServices(validService.toString()).build();
        AddServiceCommand command = new AddServiceCommand(INDEX_FIRST_PERSON, validService, ContactType.CLIENT);

        String expectedMessageClient = String.format(AddServiceCommand.MESSAGE_ADD_SERVICE_SUCCESS,
                newClient.getName(), validService);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        expectedModel.updateContact(model.getFilteredContactList().get(0), newClient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessageClient, expectedModel);
    }

    @Test
    public void execute_serviceAddedToVendorWithNoServices_success() {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact newVendor = new VendorBuilder(DOMINIC).withServices(validService.toString()).build();
        AddServiceCommand command = new AddServiceCommand(INDEX_FIRST_PERSON, validService, ContactType.VENDOR);

        String expectedMessageVendor = String.format(AddServiceCommand.MESSAGE_ADD_SERVICE_SUCCESS,
                newVendor.getName(), validService);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());
        expectedModel.updateContact(model.getFilteredContactList().get(0), newVendor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessageVendor, expectedModel);
    }

    @Test
    public void execute_serviceAddedToClientWithExistingServices_success() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Index indexLastPerson = Index.fromOneBased(model.getFilteredContactList().size());
        Contact lastContact = model.getFilteredContactList().get(indexLastPerson.getZeroBased());

        Contact newClient = new ClientBuilder(lastContact).addService(validService).build();
        AddServiceCommand command = new AddServiceCommand(indexLastPerson, validService, ContactType.CLIENT);

        String expectedMessageClient = String.format(AddServiceCommand.MESSAGE_ADD_SERVICE_SUCCESS,
                lastContact.getName(), validService);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        expectedModel.updateContact(lastContact, newClient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessageClient, expectedModel);
    }

    @Test
    public void execute_serviceAddedToVendorWithExistingServices_success() {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Index indexLastPerson = Index.fromOneBased(model.getFilteredContactList().size());
        Contact lastContact = model.getFilteredContactList().get(indexLastPerson.getZeroBased());

        Contact newVendor = new VendorBuilder(lastContact).addService(validService).build();
        AddServiceCommand command = new AddServiceCommand(indexLastPerson, validService, ContactType.VENDOR);

        String expectedMessageVendor = String.format(AddServiceCommand.MESSAGE_ADD_SERVICE_SUCCESS,
                lastContact.getName(), validService);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        expectedModel.updateFilteredContactList(ContactType.VENDOR.getFilter());
        expectedModel.updateContact(lastContact, newVendor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessageVendor, expectedModel);
    }

    // Duplicate service tests

    @Test
    public void execute_sameServiceAddedToClient_failure() {
        Service duplicateService = new Service("ring", "200.00");
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Index indexLastPerson = Index.fromOneBased(model.getFilteredContactList().size());
        AddServiceCommand command = new AddServiceCommand(indexLastPerson, duplicateService, ContactType.CLIENT);

        String expectedMessageClient = AddServiceCommand.MESSAGE_DUPLICATE_SERVICE;
        assertCommandFailure(command, model, commandHistory, expectedMessageClient);
    }

    @Test
    public void execute_sameServiceAddedToVendor_failure() {
        Service duplicateService = new Service("ring", "200.00");
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Index indexLastPerson = Index.fromOneBased(model.getFilteredContactList().size());
        AddServiceCommand command = new AddServiceCommand(indexLastPerson, duplicateService, ContactType.VENDOR);

        String expectedMessageVendor = AddServiceCommand.MESSAGE_DUPLICATE_SERVICE;
        assertCommandFailure(command, model, commandHistory, expectedMessageVendor);
    }

    // Invalid index tests

    @Test
    public void execute_invalidClientIndex_failure() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        AddServiceCommand command = new AddServiceCommand(outOfBoundIndex, validService, ContactType.CLIENT);

        assertCommandFailure(command, model, commandHistory, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidVendorIndex_failure() {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        AddServiceCommand command = new AddServiceCommand(outOfBoundIndex, validService, ContactType.VENDOR);

        assertCommandFailure(command, model, commandHistory, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, outOfBoundIndex.getOneBased()));
    }

    /**
     * Add service for client where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_clientOutsideFilteredList_success() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        Contact contactOutsideFilteredList = model.getFilteredContactList().get(outOfBoundIndex.getZeroBased());
        showContactAtIndex(model, INDEX_FIRST_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        Contact newClient = new ClientBuilder(contactOutsideFilteredList).withServices(validService.toString()).build();
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        AddServiceCommand command = new AddServiceCommand(outOfBoundIndex, validService, ContactType.CLIENT);

        String expectedMessage = String.format(AddServiceCommand.MESSAGE_ADD_SERVICE_SUCCESS,
                contactOutsideFilteredList.getName(), validService);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        expectedModel.updateContact(expectedModel.getFilteredContactList().get(outOfBoundIndex.getZeroBased()),
                newClient);
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Contact contactToEdit = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        Contact newClient = new ClientBuilder(contactToEdit).withServices(validService.toString()).build();
        AddServiceCommand command = new AddServiceCommand(INDEX_FIRST_PERSON, validService, ContactType.CLIENT);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                TypicalAccount.ROOTACCOUNT);
        expectedModel.updateContact(contactToEdit, newClient);
        expectedModel.commitAddressBook();

        // edit -> first client edited
        command.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered client list to show all persons
        expectedModel.undoAddressBook();
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first client edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        Service service = new Service(VALID_SERVICE_HOTEL, VALID_SERVICE_COST_MID);
        AddServiceCommand command = new AddServiceCommand(outOfBoundIndex, service, ContactType.CLIENT);

        // execution failed -> address book state not added into model
        assertCommandFailure(command, model, commandHistory, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, outOfBoundIndex.getOneBased()));

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Service service = new Service(VALID_SERVICE_HOTEL, VALID_SERVICE_COST_MID);
        final AddServiceCommand standardCommand = new AddServiceCommand(
                INDEX_FIRST_PERSON, service, ContactType.CLIENT);

        // same values -> returns true
        Service copyService = new Service(VALID_SERVICE_HOTEL, VALID_SERVICE_COST_MID);
        AddServiceCommand commandWithSameValues = new AddServiceCommand(
                INDEX_FIRST_PERSON, copyService, ContactType.CLIENT);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new AddServiceCommand(INDEX_SECOND_PERSON, service, ContactType.CLIENT));

        // different service type -> returns false
        Service differentService = new Service(VALID_SERVICE_PHOTO, VALID_SERVICE_COST_MID);
        assertNotEquals(standardCommand, new AddServiceCommand(
                INDEX_FIRST_PERSON, differentService, ContactType.CLIENT));

        // different service cost -> returns false
        differentService = new Service(VALID_SERVICE_HOTEL, VALID_SERVICE_COST_MIN);
        assertNotEquals(standardCommand, new AddServiceCommand(
                INDEX_FIRST_PERSON, differentService, ContactType.CLIENT));
    }

}
