package seedu.address.model.contact;

import java.util.List;
import java.util.Optional;

public class ContactInformation {
    private Optional<String> name;
    private Optional<String> phone;
    private Optional<String> email;
    private Optional<String> address;
    private List<String> tags;

    public ContactInformation (Optional<String> name, Optional<String> phone, Optional<String> email,
                               Optional<String> address, List<String> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
    }

    public ContactInformation () {

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
}
