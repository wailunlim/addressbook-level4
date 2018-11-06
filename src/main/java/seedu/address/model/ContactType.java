package seedu.address.model;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CONTACT_TYPE;

import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;
import seedu.address.model.vendor.Vendor;

/**
 * Specifies the different contact types. This enum includes the correct filter required to filter a specific
 * contact type from a list of contacts of different contact types.
 */
public enum ContactType {
    CLIENT {
        @Override
        public Predicate<Contact> getFilter() {
            return contact -> contact instanceof Client;
        }

        @Override
        public String toString() {
            return CLIENT_STRING;
        }
    },
    VENDOR {
        @Override
        public Predicate<Contact> getFilter() {
            return contact -> contact instanceof Vendor;
        }

        @Override
        public String toString() {
            return VENDOR_STRING;
        }
    };

    private static final String CLIENT_STRING = "client";
    private static final String VENDOR_STRING = "vendor";

    public abstract Predicate<Contact> getFilter();

    /**
     * Utility static function to get a {@code ContactType} from a {@code string}.
     *
     * @param string The string to convert from.
     * @return The contact type from the string.
     */
    public static ContactType fromString(String string) throws ParseException {
        switch (string) {
        case CLIENT_STRING:
            return CLIENT;
        case VENDOR_STRING:
            return VENDOR;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_CONTACT_TYPE, string));
        }
    }
}
