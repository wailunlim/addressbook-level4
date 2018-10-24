package seedu.address.model.contact;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Contact}'s properties matches with the keywords given.
 */
public class ContactContainsKeywordsPredicate implements Predicate<Contact> {
    private final ContactInformation keywords;

    public ContactContainsKeywordsPredicate() {
        this.keywords = new ContactInformation();
    }

    public ContactContainsKeywordsPredicate(ContactInformation keywords) {
        this.keywords = keywords;
    }

    public ContactContainsKeywordsPredicate(Contact contact) {
        this.keywords = new ContactInformation(contact);
    }

    @Override
    public boolean test(Contact contact) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.getName().map(name -> StringUtil.containsIgnoreCase(contact.getName().toString(), name))
                    .orElse(true)
                && keywords.getPhone().map(phone -> StringUtil.containsIgnoreCase(contact.getPhone().toString(), phone))
                    .orElse(true)
                && keywords.getEmail().map(email -> StringUtil.containsIgnoreCase(contact.getEmail().toString(), email))
                    .orElse(true)
                && keywords.getAddress().map(address -> StringUtil.containsIgnoreCase(contact.getAddress().toString(),
                    address)).orElse(true)
                && (keywords.getTags().stream().allMatch(tag -> StringUtil.containsIgnoreCase(
                        contact.getTags().toString(), tag))
                || keywords.getTags().isEmpty());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactContainsKeywordsPredicate) other).keywords)); // state check
    }
}
