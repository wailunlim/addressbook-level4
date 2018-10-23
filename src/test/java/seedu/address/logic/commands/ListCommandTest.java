package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_LIST_ALL_PERSON;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.ListClientCommandParser.CONTACT_FILTER_CLIENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.EntryContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ListCommand}.
 */
public class ListCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        EntryContainsKeywordsPredicate firstPredicate =
                new EntryContainsKeywordsPredicate(Collections.singletonList("first"));
        EntryContainsKeywordsPredicate secondPredicate =
                new EntryContainsKeywordsPredicate(Collections.singletonList("second"));

        ListCommand findFirstCommand = new ListCommand(firstPredicate, CONTACT_FILTER_CLIENT);
        ListCommand findSecondCommand = new ListCommand(secondPredicate, CONTACT_FILTER_CLIENT);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ListCommand findFirstCommandCopy = new ListCommand(firstPredicate, CONTACT_FILTER_CLIENT);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_everyPersonFound() {
        String expectedMessage = MESSAGE_LIST_ALL_PERSON;
        EntryContainsKeywordsPredicate predicate = preparePredicate(" ");
        ListCommand command = new ListCommand(predicate, CONTACT_FILTER_CLIENT);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredContactList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        EntryContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        ListCommand command = new ListCommand(predicate, CONTACT_FILTER_CLIENT);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredContactList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private EntryContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EntryContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
