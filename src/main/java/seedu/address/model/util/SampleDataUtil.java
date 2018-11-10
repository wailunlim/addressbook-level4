package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Service;
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Vendor;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSamplePersons() {
        return new Contact[] {
            new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("nature"), getServicesMap(
                        "photographer $2000.00", "hotel $1800.00", "dress $800.00", "ring $10000.00")),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("fairytale", "elsa"), getServicesMap(
                        "photographer $1080.00", "hotel $800.00", "catering $3600.00")),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("pastel", "dreamy"), getServicesMap(
                    "photographer $1000.00", "hotel $2800.00", "catering $2600.00", "transport $100.00")),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("disco"), getServicesMap(
                    "ring $2000.00", "transport $280.00", "invitation $1000.00")),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("rock"), getServicesMap(
                        "hotel $400.00", "catering $2000.00")),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getServicesMap(
                        "photographer $2800.00", "dress $200.00", "transport $100.00")),
            new Vendor(new Name("Joe Bros"), new Phone("94311253"), new Email("joebros@example.com"),
                new Address("123, Jurong East Ave 6, #08-111"),
                getTagSet("western", "italian"), getServicesMap(
                        "catering $1000.00", "photographer $1200.00")),
            new Vendor(new Name("Kim Laces"), new Phone("98762432"), new Email("kimmy@example.com"),
                new Address("313, Clementi Ave 5, #02-25"),
                getTagSet("nightgown", "headdress"), getServicesMap(
                        "dress $68.00")),
            new Vendor(new Name("Diamond Affair"), new Phone("18762432"), new Email("diamond@example.com"),
                new Address("52, Pioneer Ave 5, #02-25"),
                getTagSet("diamonds"), getServicesMap(
                        "hotel $700.00", "ring $500.00", "invitation $100.00")),
            new Vendor(new Name("Picture Perfect"), new Phone("93762432"), new Email("pp@example.com"),
                new Address("444, River Valley Ave 5, #02-25"),
                getTagSet("frames", "portraits"), getServicesMap(
                        "invitation $288.88", "photographer $1888.00")),
            new Vendor(new Name("Deliver 2 Go"), new Phone("98761432"), new Email("d2g@example.com"),
                new Address("35, Red Hill Ave 5, #02-25"),
                getTagSet("buffet", "limousine"), getServicesMap(
                        "dress $100.00", "transport $80.00", "catering $2000.00")),
            new Vendor(new Name("Foodie Goodie"), new Phone("91232432"), new Email("foodiegoodie@example.com"),
                new Address("200, Buona Vista Ave 5, #05-02"),
                getTagSet("chinese", "tea"), getServicesMap(
                            "catering $2800.00", "hotel $300.00")),
            new Vendor(new Name("Majestic Suites"), new Phone("98711132"), new Email("majicsuites@example.com"),
                new Address("313, Pasir Ris Ave 5, #02-25"),
                getTagSet("romantic"), getServicesMap(
                    "dress $100.00", "hotel $600.00")),
            new Vendor(new Name("ClickBait"), new Phone("64427373"), new Email("click@example.com"),
                new Address("155, Orchard Ave 3, #03-15"),
                getTagSet("printing"), getServicesMap(
                    "photographer $1500.00", "invitation $100.00")),
            new Vendor(new Name("Mandarin Stays"), new Phone("65534222"), new Email("mandarinstays@example.com"),
                new Address("225, Marina Road Ave 5"),
                getTagSet("attractions"), getServicesMap(
                    "hotel $588.00")),
            new Vendor(new Name("Joseph Stan"), new Phone("98336677"), new Email("jostan@example.com"),
                new Address("323, Toa Payoh Ave 8, #05-02"),
                getTagSet("personal"), getServicesMap(
                    "photographer $2000.00")),
            new Vendor(new Name("Kcooks"), new Phone("68884888"), new Email("kcookery@example.com"),
                new Address("220, Bedok Ave 10, #06-03"),
                getTagSet("korean"), getServicesMap(
                    "catering $2000.00")),
            new Vendor(new Name("Jordan Goh"), new Phone("62324111"), new Email("jg@example.com"),
                new Address("900, Selangor Ave 2, #11-03"),
                getTagSet("finedining"), getServicesMap(
                        "catering $3500.00"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSamplePersons()) {
            sampleAb.addContact(sampleContact);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a services map containing the list of strings given.
     */
    public static Map<String, Service> getServicesMap(String... strings) {
        Map<String, Service> servicesMap = new HashMap<>();
        Arrays.stream(strings)
                .map(s -> {
                    String[] splitString = s.split("\\$");
                    String serviceName = splitString[0].trim();
                    String serviceCost = splitString[1].trim();
                    return new Service(serviceName, serviceCost);
                })
                .forEach(s -> servicesMap.put(s.getName(), s));
        return servicesMap;
    }

}
