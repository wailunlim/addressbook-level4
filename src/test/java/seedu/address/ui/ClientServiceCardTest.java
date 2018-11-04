package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysService;

import org.junit.Test;

import guitests.guihandles.ServiceCardHandle;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ClientBuilder;

public class ClientServiceCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Contact contact = new ClientBuilder()
                .withId(1)
                .withServices("photographer $1000.00").build();
        ServiceCard serviceCard = new ServiceCard(contact, "photographer");
        uiPartRule.setUiPart(serviceCard);
        assertCardDisplay(serviceCard, contact, 1);
    }

    @Test
    public void equals() {
        Contact contact = new ClientBuilder()
                .withServices("photographer $1000.00").build();
        ServiceCard serviceCard = new ServiceCard(contact, "photographer");

        // same client, same index -> returns true
        ServiceCard copy = new ServiceCard(contact, "photographer");
        assertTrue(serviceCard.equals(copy));

        // same object -> returns true
        assertTrue(serviceCard.equals(serviceCard));

        // null -> returns false
        assertFalse(serviceCard.equals(null));

        // different types -> returns false
        assertFalse(serviceCard.equals(0));

        // different client, same index -> returns false
        Contact differentContact = new ClientBuilder()
                .withName("differentName")
                .withServices("photographer $1000.00").build();
        assertFalse(serviceCard.equals(new ServiceCard(differentContact, "photographer")));
    }

    /**
     * Asserts that {@code serviceCard} displays the details of {@code expectedPerson} correctly along with the cost
     * of service and matches {@code expectedId}.
     */
    private void assertCardDisplay(ServiceCard serviceCard, Contact expectedContact, int expectedId) {
        guiRobot.pauseForHuman();

        ServiceCardHandle serviceCardHandle = new ServiceCardHandle(serviceCard.getRoot());

        // verify id is displayed correctly
        assertEquals("#" + Integer.toString(expectedId) + ". ", serviceCardHandle.getId());

        // verify client details are displayed correctly
        assertCardDisplaysService(expectedContact, serviceCardHandle);
    }
}
