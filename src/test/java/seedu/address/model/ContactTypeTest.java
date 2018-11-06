package seedu.address.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;

public class ContactTypeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fromString_clientString_shouldEqualClientContactType() throws ParseException {
        assertEquals(ContactType.fromString("client"), ContactType.CLIENT);
    }

    @Test
    public void fromString_vendorString_shouldEqualVendorContactType() throws ParseException {
        assertEquals(ContactType.fromString("vendor"), ContactType.VENDOR);
    }

    @Test
    public void fromString_invalidString_throwParseException() throws ParseException {
        thrown.expect(ParseException.class);
        ContactType.fromString("dog");
    }
}
