package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.NoSuchElementException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;
import seedu.address.model.serviceprovider.ServiceProvider;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class AutoMatchCommand extends Command {

    public static final String COMMAND_WORD = "automatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final String contactType;
    private final String contactId;

    public AutoMatchCommand(String contactType, String contactId) {
        this.contactType = contactType;
        this.contactId = contactId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Update filtered contact list to contain all contacts and get the list
        model.updateFilteredContactList(x -> true);
        model.getFilteredContactList();


        // Find the contact for which we are going to find matches for
        int contactId = Integer.parseInt(this.contactId);
        Contact contact;
        try {
            contact = model
                    .getAddressBook()
                    .getContactList()
                    .stream()
                    .filter(c -> (contactType.equals("client") && c instanceof Client)
                            || (contactType.equals("serviceprovider") && c instanceof ServiceProvider))
                    .filter(c -> c.getId() == contactId)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException exception) {
            throw new CommandException(String.format("Non-existent entity %s#%s.", contactType, contactId));
        }

        if (contact instanceof Client) {
            Collection<Service> servicesRequired = contact.getServices().values();
            model.updateFilteredContactList(c -> (c instanceof ServiceProvider
                   && serviceProviderCanFulfilAtLeastOneService((ServiceProvider) c, servicesRequired)));
        } else if (contact instanceof ServiceProvider) {
            Collection<Service> servicesProvided = contact.getServices().values();
            model.updateFilteredContactList(c -> (c instanceof Client
                   && clientRequiresServices((Client) c, servicesProvided)));
        } else {
            // We should never arrive here. If we do, it means there's a Contact subclass that is not handled here.
            throw new CommandException("Unknown entity, neither client nor service provider found in database.");
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    /**
     * Utility function to check if a {@code serviceProvider} can fulfil a particular {@code serviceRequired}.
     * @param serviceProvider The service provider.
     * @param serviceRequired The service required.
     * @return Returns true if the service provider can fulfil the service.
     */
    private static boolean serviceProviderCanFulfilService(ServiceProvider serviceProvider, Service serviceRequired) {
        return serviceProvider
                .getServices()
                .values()
                .stream()
                .filter(serviceProvided -> serviceRequired.isSameServiceTypeAs(serviceProvided))
                .filter(serviceProvided -> serviceRequired.getCost() >= serviceProvided.getCost())
                .count() > 0;
    }

    /**
     * Utility function to check if a {@code serviceProvider} can fulfil at least one {@code serviceRequired} from a
     * collection of {@code Service}.
     * @param serviceProvider The service provider.
     * @param servicesRequired The services required.
     * @return Returns true if the service provider can fulfil at least one of the service.
     */
    private static boolean serviceProviderCanFulfilAtLeastOneService(ServiceProvider serviceProvider,
                                                                     Collection<Service> servicesRequired) {
        return servicesRequired
                .stream()
                .filter(serviceRequired -> serviceProviderCanFulfilService(serviceProvider, serviceRequired))
                .count() > 0;
    }

    /**
     * Utility function to check if a {@code client} requires and can afford a particular {@code serviceOffered}.
     * @param client The client.
     * @param serviceOffered The service offered.
     * @return Returns true if the client requires the service and can afford it.
     */
    private static boolean clientRequiresService(Client client, Service serviceOffered) {
        return client
                .getServices()
                .values()
                .stream()
                .filter(serviceRequired -> serviceRequired.isSameServiceTypeAs(serviceOffered))
                .filter(serviceRequired -> serviceRequired.getCost() >= serviceOffered.getCost())
                .count() > 0;
    }

    /**
     * Utility function to check if a {@code client} requires and can afford a at least one of the services offered by
     * the service provider in {@code serviceOffered}.
     * @param client The client.
     * @param servicesOffered The services offered.
     * @return Returns true if the client requires at least one of the service and can afford it.
     */
    private static boolean clientRequiresServices(Client client, Collection<Service> servicesOffered) {
        return servicesOffered
                .stream()
                .filter(serviceOffered -> clientRequiresService(client, serviceOffered))
                .count() > 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AutoMatchCommand // instanceof handles nulls
                && contactType.equals(((AutoMatchCommand) other).contactType)
                && contactId.equals(((AutoMatchCommand) other).contactId)); // state check
    }
}
