package seedu.address.model.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactInformation {
    private Optional<String> name;
    private Optional<String> phone;
    private Optional<String> email;
    private Optional<String> address;
    private List<String> tags;

    public ContactInformation () {
        name = Optional.empty();
        phone = Optional.empty();
        email = Optional.empty();
        address = Optional.empty();
        tags = new ArrayList<>();
    }

    public ContactInformation (Optional<String> name, Optional<String> phone, Optional<String> email,
                               Optional<String> address, List<String> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
    }

    public ContactInformation (Contact contact) {
        name = Optional.of(contact.getName().toString());
        phone = Optional.of(contact.getPhone().toString());
        email = Optional.of(contact.getEmail().toString());
        address = Optional.of(contact.getAddress().toString());
        tags = contact.getTags().stream().map(tag -> tag.toString()).collect(Collectors.toList());
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<String> getPhone() {
        return phone;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getAddress() {
        return address;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean isEmpty() {
        return !name.isPresent() && !phone.isPresent() && !email.isPresent() && !address.isPresent() && tags.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactInformation // instanceof handles nulls
                && name.equals(((ContactInformation) other).name)
                && phone.equals(((ContactInformation) other).phone)
                && email.equals(((ContactInformation) other).email)
                && address.equals(((ContactInformation) other).address)
                && tags.equals(((ContactInformation) other).tags)); // state check
    }
}
