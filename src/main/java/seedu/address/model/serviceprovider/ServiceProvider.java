package seedu.address.model.serviceprovider;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;

public class ServiceProvider extends Contact {

    /**
     * Every field must be present and not null.
     *
     */
    public ServiceProvider(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
}
