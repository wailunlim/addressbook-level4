package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EntryContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EntryContainsKeywordsPredicate firstPredicate = new EntryContainsKeywordsPredicate(firstPredicateKeywordList);
        EntryContainsKeywordsPredicate secondPredicate = new EntryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EntryContainsKeywordsPredicate firstPredicateCopy =
                new EntryContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_entryContainsKeywords_returnsTrue() {
        // Zero keywords
        EntryContainsKeywordsPredicate predicate = new EntryContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // One keyword
        predicate = new EntryContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new EntryContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new EntryContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new EntryContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Finding address with one Keyword
        predicate = new EntryContainsKeywordsPredicate(Arrays.asList("Main"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        // Finding phone number
        predicate = new EntryContainsKeywordsPredicate(Arrays.asList("123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("123").build()));

        // Finding email
        predicate = new EntryContainsKeywordsPredicate(Arrays.asList("alice@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));
    }

    @Test
    public void test_entryDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        EntryContainsKeywordsPredicate predicate = new EntryContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new EntryContainsKeywordsPredicate(Arrays.asList("123456", "alice1@email.com", "Main2", "Street2"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
