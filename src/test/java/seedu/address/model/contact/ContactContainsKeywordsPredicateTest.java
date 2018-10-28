package seedu.address.model.contact;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import seedu.address.testutil.ClientBuilder;

public class ContactContainsKeywordsPredicateTest {

    @Test
    public void test_contactContainsKeywords_returnsTrue() {
        // Test name

        // Zero keywords
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate();
        assertTrue(predicate.test(new ClientBuilder().withName("Alice").build()));

        // One keyword
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("Alice"), Optional.empty(),
                Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("Alice Bob"),
                Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("aLiCe"), Optional.empty(),
                Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Substring
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("lice"), Optional.empty(),
                Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Test phone

        // One keyword
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.of("9999"),
                Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withPhone("9999").build()));

        // Substring
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.of("234"),
                Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withPhone("99992345").build()));

        // Test email

        // One keyword
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.of("abc@email.com"), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withEmail("abc@email.com").build()));

        // Mixed-case keywords
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.of("aBc@EmAiL.com"), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withEmail("abc@email.com").build()));

        // Substring
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.of("email"), Optional.empty(), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withEmail("abc@email.com").build()));

        // Test address

        // One keyword
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of("Yung"), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withAddress("175A Yung Kuang Road 611175").build()));

        // Multiple keywords
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of("Yung Kuang Road"), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withAddress("175A Yung Kuang Road 611175").build()));

        // Mixed-case keywords
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of("yUnG"), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withAddress("175A Yung Kuang Road 611175").build()));

        // Substring
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of("ung"), new ArrayList<>()));
        assertTrue(predicate.test(new ClientBuilder().withAddress("175A Yung Kuang Road 611175").build()));

        // Test tag

        // One keyword
        List<String> tags = new ArrayList<>();
        tags.add("friends");
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), tags));
        assertTrue(predicate.test(new ClientBuilder().withTags("friends", "oweMoney").build()));

        // Multiple keywords
        tags.add("oweMoney");
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), tags));
        assertTrue(predicate.test(new ClientBuilder().withTags("friends", "oweMoney").build()));

        // Mixed-case keywords
        tags.clear();
        tags.add("fRiEnDs");
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), tags));
        assertTrue(predicate.test(new ClientBuilder().withTags("friends", "oweMoney").build()));

        // Substring
        tags.clear();
        tags.add("end");
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), tags));
        assertTrue(predicate.test(new ClientBuilder().withTags("friends", "oweMoney").build()));
    }

    @Test
    public void test_contactDoesNotContainsKeywords_returnsFalse() {
        // Non-matching keyword
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate(new ContactInformation(
                Optional.of("Carl"), Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(
                Optional.of("Carl"), Optional.of("12345"), Optional.of("alice@email.com"), Optional.of("Main Street"),
                new ArrayList<>()));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Only one matching keyword
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(
                Optional.of("Alice"), Optional.of("9999"), Optional.of("alice@email.com"), Optional.of("Pasir Ris"),
                new ArrayList<>()));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@gmail.com").withAddress("Main Street").build()));

        // Not all keywords match
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.of("Alice WaiLun"),
                Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>()));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Not all tags match
        List<String> tags = new ArrayList<>();
        tags.add("friends");
        tags.add("oweMoney");
        predicate = new ContactContainsKeywordsPredicate(new ContactInformation(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), tags));
        assertFalse(predicate.test(new ClientBuilder().withTags("friends").build()));
    }

    @Test
    public void equals() {
        ContactContainsKeywordsPredicate firstPredicate = new ContactContainsKeywordsPredicate(ALICE);
        ContactContainsKeywordsPredicate secondPredicate = new ContactContainsKeywordsPredicate(BENSON);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactContainsKeywordsPredicate firstPredicateCopy = new ContactContainsKeywordsPredicate(ALICE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
