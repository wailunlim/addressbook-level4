package seedu.address.model.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactList;
import seedu.address.model.contact.exceptions.ContactNotFoundException;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.testutil.ClientBuilder;

public class UniqueContactListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueContactList<Contact> uniqueContactList = new UniqueContactList<>();

    @Test
    public void contains_nullContact_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueContactList.contains(null);
    }

    @Test
    public void contains_contactNotInList_returnsFalse() {
        assertFalse(uniqueContactList.contains(ALICE));
    }

    @Test
    public void contains_contactInList_returnsTrue() {
        uniqueContactList.add(ALICE);
        assertTrue(uniqueContactList.contains(ALICE));
    }

    @Test
    public void contains_contactWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContactList.add(ALICE);
        Contact editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueContactList.contains(editedAlice));
    }

    @Test
    public void add_nullContact_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueContactList.add(null);
    }

    @Test
    public void add_duplicateContact_throwsDuplicateContactException() {
        uniqueContactList.add(ALICE);
        thrown.expect(DuplicateContactException.class);
        uniqueContactList.add(ALICE);
    }

    @Test
    public void setContact_nullTargetContact_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueContactList.setContact(null, ALICE);
    }

    @Test
    public void setContact_nullEditedContact_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueContactList.setContact(ALICE, null);
    }

    @Test
    public void setContact_targetContactNotInList_throwsContactNotFoundException() {
        thrown.expect(ContactNotFoundException.class);
        uniqueContactList.setContact(ALICE, ALICE);
    }

    @Test
    public void setContact_editedContactIsSamePerson_success() {
        uniqueContactList.add(ALICE);
        uniqueContactList.setContact(ALICE, ALICE);
        UniqueContactList<Contact> expectedUniqueContactList = new UniqueContactList<>();
        expectedUniqueContactList.add(ALICE);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasSameIdentity_success() {
        uniqueContactList.add(ALICE);
        Contact editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueContactList.setContact(ALICE, editedAlice);
        UniqueContactList<Contact> expectedUniqueContactList = new UniqueContactList<>();
        expectedUniqueContactList.add(editedAlice);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasDifferentIdentity_success() {
        uniqueContactList.add(ALICE);
        uniqueContactList.setContact(ALICE, BOB);
        UniqueContactList<Contact> expectedUniqueContactList = new UniqueContactList<>();
        expectedUniqueContactList.add(BOB);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasNonUniqueIdentity_throwsDuplicateContactException() {
        uniqueContactList.add(ALICE);
        uniqueContactList.add(BOB);
        thrown.expect(DuplicateContactException.class);
        uniqueContactList.setContact(ALICE, BOB);
    }

    @Test
    public void remove_nullContact_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueContactList.remove(null);
    }

    @Test
    public void remove_contactDoesNotExist_throwsContactNotFoundException() {
        thrown.expect(ContactNotFoundException.class);
        uniqueContactList.remove(ALICE);
    }

    @Test
    public void remove_existingContact_removesContact() {
        uniqueContactList.add(ALICE);
        uniqueContactList.remove(ALICE);
        UniqueContactList<Contact> expectedUniqueContactList = new UniqueContactList<>();
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullUniqueContactList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueContactList.setContacts((UniqueContactList<Contact>) null);
    }

    @Test
    public void setContacts_uniqueContactList_replacesOwnListWithProvidedUniqueContactList() {
        uniqueContactList.add(ALICE);
        UniqueContactList<Contact> expectedUniqueContactList = new UniqueContactList<>();
        expectedUniqueContactList.add(BOB);
        uniqueContactList.setContacts(expectedUniqueContactList);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueContactList.setContacts((List<Contact>) null);
    }

    @Test
    public void setContacts_list_replacesOwnListWithProvidedList() {
        uniqueContactList.add(ALICE);
        List<Contact> contactList = Collections.singletonList(BOB);
        uniqueContactList.setContacts(contactList);
        UniqueContactList<Contact> expectedUniqueContactList = new UniqueContactList<>();
        expectedUniqueContactList.add(BOB);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_listWithDuplicateContacts_throwsDuplicateContactException() {
        List<Contact> listWithDuplicateContacts = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateContactException.class);
        uniqueContactList.setContacts(listWithDuplicateContacts);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueContactList.asUnmodifiableObservableList().remove(0);
    }
}
