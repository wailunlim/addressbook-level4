package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.serviceprovider.ServiceProvider;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

public class ServiceProviderBuilder {
    public static final String DEFAULT_NAME = "Dominic Dong";
    public static final String DEFAULT_PHONE = "999";
    public static final String DEFAULT_EMAIL = "dong.siji@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong East Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public ServiceProviderBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ServiceProviderBuilder with the data of {@code contactToCopy}.
     */
    public ServiceProviderBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        address = contactToCopy.getAddress();
        tags = new HashSet<>(contactToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code ServiceProvider} that we are building.
     */
    public ServiceProviderBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code ServiceProvider} that we are building.
     */
    public ServiceProviderBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code ServiceProvider} that we are building.
     */
    public ServiceProviderBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code ServiceProvider} that we are building.
     */
    public ServiceProviderBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code ServiceProvider} that we are building.
     */
    public ServiceProviderBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Contact build() {
        return new ServiceProvider(name, phone, email, address, tags);
    }

}
