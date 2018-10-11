package seedu.address.model.contact;

/**
 * An interface to be implemented by the client and the service provider. The Addressbook will use this interface
 *     to retrieve details of the contact.
 */
public interface Contact {

    // Get the name of the contact
    Name getName();

    // Get the address of the contact
    Address getAddress();

    // Get the email of the contact
    Email getEmail();

    // get the phone number of the contact
    Phone getPhone();

}
