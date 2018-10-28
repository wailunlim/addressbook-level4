package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Defines the service types
 */
enum ServiceType {
    photographer, hotel, catering, dress, ring, transport, invitation
}

/**
 * Represents a Contact's Service in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidServiceName(String)}
 */
public class Service {

    public static final String MESSAGE_SERVICE_NAME_CONSTRAINTS =
            "Valid Services: photographer, hotel, catering, dress, ring, transport, invitation";
    public static final String MESSAGE_SERVICE_COST_CONSTRAINTS =
            "Service cost must be more than $1";

    public final String serviceName;
    public final int serviceCost;

    /**
     * Constructs a {@code Service}.
     *
     * @param service A valid service name.
     * @param cost Cost of this service.
     */
    public Service(String service, int cost) {
        requireNonNull(service);

        checkArgument(isValidServiceName(service), MESSAGE_SERVICE_NAME_CONSTRAINTS);
        checkArgument(isValidServiceCost(cost), MESSAGE_SERVICE_COST_CONSTRAINTS);

        serviceName = service;
        serviceCost = cost;
    }

    public String getName() {
        return serviceName;
    }

    public int getCost() {
        return serviceCost;
    }

    /**
     * Returns true if a given string is a valid service name.
     */
    public static boolean isValidServiceName(String test) {
        for (ServiceType s : ServiceType.values()) {
            if (s.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given cost is above 0.
     */
    public static boolean isValidServiceCost(int test) {
        return (!(test <= 0));
    }

    @Override
    public String toString() {
        return serviceName + " $" + serviceCost;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Service // instanceof handles nulls
                && serviceName.equals(((Service) other).serviceName)); // state check
    }

    @Override
    public int hashCode() {
        return serviceName.hashCode();
    }

}
