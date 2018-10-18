package seedu.address.model.contact;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Client}'s {@code Name} matches any of the keywords given.
 */
public class EntryContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    public EntryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EntryContainsKeywordsPredicate) other).keywords)); // state check
    }

}
