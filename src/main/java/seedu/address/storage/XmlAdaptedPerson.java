package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Service;
import seedu.address.model.serviceprovider.ServiceProvider;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Client.
 */
public class XmlAdaptedPerson {
    /**
     * Represents the type of contact (either a {@code Client} or a {@code ServiceProvider}).
     */
    public enum ContactType {
        CLIENT,
        SERVICE_PROVIDER
    }

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Client's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private ContactType type;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedService> services = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() { }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given client details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged,
                            List<XmlAdaptedService> services, ContactType type) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (services != null) {
            this.services = new ArrayList<>(services);
        }
        this.type = type;
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given client details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged,
                            ContactType type) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.type = type;
    }

    /**
     * Converts a given Client into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Contact source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        services = source.getServicesStream()
                .map(XmlAdaptedService::new)
                .collect(Collectors.toList());

        if (source instanceof Client) {
            type = ContactType.CLIENT;
        } else if (source instanceof ServiceProvider) {
            type = ContactType.SERVICE_PROVIDER;
        } else {
            throw new RuntimeException("public XmlAdaptedPerson(Contact source)");
        }
    }

    /**
     * Converts this jaxb-friendly adapted client object into the model's Client object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted client
     */
    public Contact toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Service> personServices = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        for (XmlAdaptedService service : services) {
            personServices.add(service.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // Additional metadata to determine if contact is a Client or a ServiceProvider


        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Map<String, Service> modelServices = new HashMap<>();
        for (Service service : personServices) {
            modelServices.put(service.getName(), service);
        }

        if (type == null) {
            throw new IllegalValueException("Contact type must be non-null.");
        }

        if (type.equals(ContactType.CLIENT)) {
            return new Client(modelName, modelPhone, modelEmail, modelAddress, modelTags);
        }
        if (type.equals(ContactType.SERVICE_PROVIDER)) {
            return new ServiceProvider(modelName, modelPhone, modelEmail, modelAddress, modelTags);
        }

        throw new IllegalValueException("Illegal contact type. It can only be a client or a service provider.");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged)
                && type.equals(otherPerson.type)
                && services.equals(otherPerson.services);
    }
}
