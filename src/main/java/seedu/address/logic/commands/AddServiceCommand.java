package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;

/**
 * Adds a service and budget under a client.
 */
public class AddServiceCommand extends Command {

    public static final String COMMAND_WORD = "addservice";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a service to the  contact identified "
            + "by the index number used in the displayed client/serviceprovider list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SERVICE + "SERVICE "
            + PREFIX_COST + "COST (excluding '$') \n"
            + "Example: " + "client#3 " + COMMAND_WORD + " "
            + PREFIX_SERVICE + "photographer "
            + PREFIX_COST + "1000 ";

    public static final String MESSAGE_ADD_SERVICE_SUCCESS = "New service added for : %1$s \n" + "Service: %2$s";
    public static final String MESSAGE_DUPLICATE_SERVICE = "This service has already been added";

    private final Index index;
    private final Service service;
    private final ContactType contactType;

    /**
     * Creates an AddServiceCommand to add the specified service and budget.
     * @param index of the contact in the filtered contact list to add service to
     * @param service service to add
     * @param contactType specifies if contact is a client or service provider
     */
    public AddServiceCommand(Index index, Service service, ContactType contactType) {
        requireNonNull(index);
        requireNonNull(service);

        this.index = index;
        this.service = service;
        this.contactType = contactType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException,
            LackOfPrivilegeException {
        requireNonNull(model);

        if (!model.getUserAccount().hasWritePrivilege()) {
            throw new LackOfPrivilegeException(COMMAND_WORD);
        }

        // id is unique
        model.updateFilteredContactList(contactType.getFilter().and(contact -> contact.getId() == index.getOneBased()));

        List<Contact> filteredList = model.getFilteredContactList();

        if (filteredList.size() == 0) {
            // filtered list size is 0, meaning there is no such contact
            model.updateFilteredContactList(contactType.getFilter());
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Contact contactToEdit = filteredList.get(0);
        Contact editedContact;

        if (contactToEdit.hasService(service)) {
            throw new CommandException(MESSAGE_DUPLICATE_SERVICE);
        } else {
            editedContact = createContactWithService(contactToEdit, service);
        }

        model.updateContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_SERVICE_SUCCESS, contactToEdit.getName(), service));
    }

    /**
     * Creates a new contact with the newly added service
     * @param contact Current contact to add service to
     * @param service Service to be added
     * @return New contact with newly added service
     */
    private Contact createContactWithService(Contact contact, Service service) {
        EditCommand.EditContactDescriptor editContactDescriptor = new EditCommand.EditContactDescriptor();
        editContactDescriptor.setName(contact.getName());
        editContactDescriptor.setPhone(contact.getPhone());
        editContactDescriptor.setAddress(contact.getAddress());
        editContactDescriptor.setEmail(contact.getEmail());
        editContactDescriptor.setTags(contact.getTags());
        editContactDescriptor.setServices(contact.getServices());
        editContactDescriptor.addService(service);

        Contact newContact = EditCommand.createEditedContact(contact, editContactDescriptor, contactType);
        return newContact;
    }

}
