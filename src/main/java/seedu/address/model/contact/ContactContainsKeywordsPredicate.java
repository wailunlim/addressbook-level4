package seedu.address.model.contact;

import java.util.function.Predicate;

public class ContactContainsKeywordsPredicate implements Predicate<Contact> {
    private final ContactInformation keywords;

    public ContactContainsKeywordsPredicate(ContactInformation keywords) {
        this.keywords = keywords;
    }

    public ContactContainsKeywordsPredicate() {
        this.keywords = new ContactInformation();
    }

    @Override
    public boolean test(Contact contact) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.getName().map(name -> contact.getName().toString().contains(name)).orElse(true) &&
                keywords.getPhone().map(phone -> contact.getPhone().toString().contains(phone)).orElse(true) &&
                keywords.getEmail().map(email -> contact.getEmail().toString().contains(email)).orElse(true) &&
                keywords.getAddress().map(address -> contact.getAddress().toString().contains(address))
                        .orElse(true) &&
                (keywords.getTags().stream().allMatch(tag -> contact.getTags().toString().contains(tag)) ||
                        keywords.getTags().isEmpty());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactContainsKeywordsPredicate) other).keywords)); // state check
    }
}