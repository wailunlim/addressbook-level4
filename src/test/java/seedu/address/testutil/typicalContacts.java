package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;
import seedu.address.model.serviceprovider.ServiceProvider;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class typicalContacts {
    public static int clientId = 1;
    public static int serviceProviderId = 1;

    public static final Contact ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withID(clientId++)
            .build();
    public static final Contact BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withID(clientId++)
            .withTags("owesMoney", "friends").build();
    public static final Contact CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withID(clientId++).withAddress("wall street").build();
    public static final Contact DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withID(clientId++).withAddress("10th street").withTags("friends").build();
    public static final Contact ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withID(clientId++).withAddress("michegan ave").build();
    public static final Contact FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withID(clientId++).withAddress("little tokyo").build();
    public static final Contact GEORGE = new ClientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withID(clientId++).withAddress("4th street").build();

    // Manually added
    public static final Contact HOON = new ClientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Contact IDA = new ClientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Client's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Contact BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "n/Meier"; // A keyword that matches MEIER

    // ServiceProviders below
    public static final Contact DOMINIC = new ServiceProviderBuilder().withName("Dominic Dong")
            .withAddress("123, Jurong East Ave 6, #08-111").withEmail("dominicdong@example.com")
            .withPhone("94311253")
            .withID(serviceProviderId++)
            .withTags("friends").build();
    public static final Contact EEHOOI = new ServiceProviderBuilder().withName("Ng Ee Hooi")
            .withAddress("313, Clementi Ave 5, #02-25")
            .withEmail("eehooid@example.com").withPhone("98762432")
            .withID(serviceProviderId++)
            .withTags("owesMoney", "friends").build();
    public static final Contact GAN = new ServiceProviderBuilder().withName("Gan Chin Yao")
            .withAddress("313, Pioneer Ave 5, #02-25")
            .withEmail("gan@example.com").withPhone("18762432")
            .withID(serviceProviderId++)
            .build();
    public static final Contact JIANJIE = new ServiceProviderBuilder().withName("Liau Jian Jie")
            .withAddress("444, River Valley Ave 5, #02-25")
            .withEmail("jj@example.com").withPhone("93762432")
            .withID(serviceProviderId++)
            .build();
    public static final Contact WAILUN = new ServiceProviderBuilder().withName("Lim Wai Lun")
            .withAddress("313, Red Hill Ave 5, #02-25")
            .withEmail("wailunoob@example.com").withPhone("98761432")
            .withID(serviceProviderId++)
            .build();
    public static final Contact SIJI = new ServiceProviderBuilder().withName("Dong SiJi")
            .withAddress("313, Buona Vista Ave 5, #02-25")
            .withEmail("dong.siji@example.com").withPhone("91232432")
            .withID(serviceProviderId++)
            .build();
    public static final Contact CHINYAO = new ServiceProviderBuilder().withName("Chino")
            .withAddress("313, Pasir Ris Ave 5, #02-25")
            .withEmail("chinoyaobi@example.com").withPhone("98711132")
            .withID(serviceProviderId++)
            .build();



    private typicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical contacts.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Contact contact : getTypicalContacts()) {
            ab.addContact(contact);
        }
        return ab;
    }

    public static List<Contact> getTypicalContacts() {
        Client.CID = 1;
        System.out.println(ALICE.getID());
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                DOMINIC, EEHOOI, GAN, JIANJIE, WAILUN, SIJI, CHINYAO));
    }
}
