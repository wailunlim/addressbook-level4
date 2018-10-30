package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.ContactType;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Returns the contactType of the contact that is different between the 2 lists.
     * @param list1 First list to compare.
     * @param list2 Second list to compare.
     * @return ContactType of the different contact between the 2 lists.
     */
    public static ContactType compareListOfContacts(List<Contact> list1, List<Contact> list2) {

        List<Contact> diffList = list1.stream().filter(contact -> !list2.stream()
                .anyMatch(contactToCheck -> contactToCheck.equals(contact))).collect(Collectors.toList());

        if (diffList.isEmpty()) {
            diffList = list2.stream().filter(contact -> !list1.stream()
                    .anyMatch(contactToCheck -> contactToCheck.equals(contact))).collect(Collectors.toList());
        }

        if (diffList.get(0) instanceof Client) {
            return ContactType.CLIENT;
        } else {
            return ContactType.SERVICE_PROVIDER;
        }
    }
}
