package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Service;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.vendor.Vendor;

/**
 * A utility class to help with building Vendor objects.
 */
public class VendorBuilder {
    public static final String DEFAULT_NAME = "Dominic Dong";
    public static final String DEFAULT_PHONE = "999";
    public static final String DEFAULT_EMAIL = "dong.siji@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong East Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Map<String, Service> services;
    private int id;

    public VendorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        services = new HashMap<>();
        id = 0;
    }

    /**
     * Initializes the VendorBuilder with the data of {@code contactToCopy}.
     */
    public VendorBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        address = contactToCopy.getAddress();
        tags = new HashSet<>(contactToCopy.getTags());
        services = new HashMap<>(contactToCopy.getServices());
        id = contactToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Vendor} that we are building.
     */
    public VendorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code services} into a {@code Map<String, Service>}
     * and set it to the {@code Vendor} that we are building.
     */
    public VendorBuilder withServices(String ... services) {
        this.services = SampleDataUtil.getServicesMap(services);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code id} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public Contact build() {
        return new Vendor(name, phone, email, address, tags, services, id);
    }

}
