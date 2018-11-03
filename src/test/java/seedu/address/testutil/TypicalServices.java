package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_COST_MID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_COST_MIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_HOTEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_PHOTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.contact.Service;

/**
 * A utility class containing a list of {@code Service} objects to be used in tests.
 */
public class TypicalServices {
    // Manually added
    public static final Service TRANSPORT = new Service("transport", "288.00");
    public static final Service INVITATION = new Service("invitation", "1000.00");
    public static final Service RING = new Service("ring", "3888.88");
    public static final Service DRESS = new Service("dress", "188.88");
    public static final Service CATERING = new Service("catering", "5888.88");

    // Manually added - Service details found in {@code CommandTestUtil}
    public static final Service PHOTOGRAPHER = new Service(VALID_SERVICE_PHOTO, VALID_SERVICE_COST_MID);
    public static final Service HOTEL = new Service(VALID_SERVICE_HOTEL, VALID_SERVICE_COST_MIN);

    private TypicalServices() {} // prevents instantiation

    public static List<Service> getTypicalServices() {
        return new ArrayList<>(Arrays.asList(TRANSPORT, INVITATION, RING, DRESS, CATERING, PHOTOGRAPHER, HOTEL));
    }
}
