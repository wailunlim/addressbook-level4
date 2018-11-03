package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DeselectRequestEvent;
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
    public static final String COMMAND_WORD_GENERAL = "%1$s%2$s addservice";
    public static final String COMMAND_WORD_CLIENT = "client addservice";
    public static final String COMMAND_WORD_SERVICE_PROVIDER = "serviceprovider addservice";

    public static final String MESSAGE_USAGE = COMMAND_WORD_GENERAL + ": Adds a service to the %1$s identified "
            + "by the assigned unique %1$s ID.\n"
            + "Parameters: #<ID> (must be a positive integer) "
            + PREFIX_SERVICE + "SERVICE "
            + PREFIX_COST + "COST (excluding symbols to 2 decimal places) \n"
            + "Example: " + String.format(COMMAND_WORD_GENERAL, "%1$s", "#3") + " "
            + PREFIX_SERVICE + "photographer "
            + PREFIX_COST + "1000.00 \n"
            + Service.MESSAGE_SERVICE_NAME_CONSTRAINTS + "\n"
            + Service.MESSAGE_SERVICE_COST_CONSTRAINTS;

    public static final String MESSAGE_ADD_SERVICE_SUCCESS = "New service added for : %1$s \n" + "Service: %2$s";
    public static final String MESSAGE_DUPLICATE_SERVICE = "This service has already been added";

    private final Index id;
    private final Service service;
    private final ContactType contactType;

    /**
     * Creates an AddServiceCommand to add the specified service and budget.
     * @param id of the contact in the filtered contact list to add service to
     * @param service service to add
     * @param contactType specifies if contact is a client or service provider
     */
    public AddServiceCommand(Index id, Service service, ContactType contactType) {
        requireNonNull(id);
        requireNonNull(service);

        this.id = id;
        this.service = service;
        this.contactType = contactType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException,
            LackOfPrivilegeException {
        requireNonNull(model);

        if (!model.getUserAccount().hasWritePrivilege()) {
            throw new LackOfPrivilegeException(String.format(COMMAND_WORD_GENERAL, contactType, "#<ID>"));
        }

        // id is unique
        model.updateFilteredContactList(contactType.getFilter().and(contact -> contact.getId() == id.getOneBased()));

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
        model.updateFilteredContactList(contactType.getFilter());
        model.commitAddressBook();
        EventsCenter.getInstance().post(new DeselectRequestEvent());
        return new CommandResult(String.format(MESSAGE_ADD_SERVICE_SUCCESS, contactToEdit.getName(), service));
    }

    /**
     * Creates a new contact with the newly added service
     * @param contact Current contact to add service to
     * @param service Service to be added
     * @return New contact with newly added service
     */
    private Contact createContactWithService(Contact contact, Service service) {
        UpdateCommand.EditContactDescriptor editContactDescriptor = new UpdateCommand.EditContactDescriptor();
        editContactDescriptor.setName(contact.getName());
        editContactDescriptor.setPhone(contact.getPhone());
        editContactDescriptor.setAddress(contact.getAddress());
        editContactDescriptor.setEmail(contact.getEmail());
        editContactDescriptor.setTags(contact.getTags());
        editContactDescriptor.setServices(contact.getServices());
        editContactDescriptor.addService(service);

        return UpdateCommand.createEditedContact(contact, editContactDescriptor, contactType);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddServiceCommand)) {
            return false;
        }

        // state check
        AddServiceCommand e = (AddServiceCommand) other;
        return id.equals(e.id);
    }

}
