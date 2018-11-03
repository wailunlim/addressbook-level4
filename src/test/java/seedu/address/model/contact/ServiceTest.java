package seedu.address.model.contact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalServices.HOTEL;
import static seedu.address.testutil.TypicalServices.TRANSPORT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameServiceType() {
        // same object -> returns true
        assertTrue(TRANSPORT.isSameServiceTypeAs(TRANSPORT));

        // null -> returns false
        assertFalse(TRANSPORT.isSameServiceTypeAs(null));

        // different service type -> returns false
        Service editedTransport = new Service("photographer", "288.00");
        assertFalse(TRANSPORT.isSameServiceTypeAs(editedTransport));
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertEquals(TRANSPORT, new Service("transport", "288.00"));

        // same object -> returns true
        assertEquals(TRANSPORT, TRANSPORT);

        // null -> returns false
        assertNotEquals(null, TRANSPORT);

        // different type -> returns false;
        assertNotEquals(5, TRANSPORT);

        // different service -> returns false;
        assertNotEquals(HOTEL, TRANSPORT);

        // same cost, different no. of decimal places -> returns true
        Service editedTransport = new Service("transport", "288");
        assertEquals(TRANSPORT, editedTransport);

        // different cost -> returns false
        editedTransport = new Service("transport", "100");
        assertNotEquals(TRANSPORT, editedTransport);

        // different service type -> returns false
        editedTransport = new Service("photographer", "288.00");
        assertNotEquals(TRANSPORT, editedTransport);
    }

    @Test
    public void string() {
        // same service -> returns true
        assertEquals(TRANSPORT.toString(), TRANSPORT.toString());

        // same values -> returns true
        Service editedService = new Service("transport", "288.00");
        assertEquals(TRANSPORT.toString(), editedService.toString());

        // different service type -> returns false
        editedService = new Service("photographer", "288.00");
        assertNotEquals(TRANSPORT.toString(), editedService.toString());

        // different cost -> returns false
        editedService = new Service("transport", "100.00");
        assertNotEquals(TRANSPORT.toString(), editedService.toString());

        // different cost decimal -> returns false
        editedService = new Service("transport", "288");
        assertNotEquals(TRANSPORT.toString(), editedService.toString());

        // different service -> returns false
        assertNotEquals(TRANSPORT.toString(), HOTEL.toString());
    }
}
