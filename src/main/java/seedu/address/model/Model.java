package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.contact.Contact;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    //TODO: this predicate shows contacts only. javadocs is incorrect here but KIV first
    Predicate<Contact> PREDICATE_SHOW_ALL_PERSONS = contact -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the address
     *     book.
     */
    void updateContact(Contact target, Contact editedContact);

    /** Returns an unmodifiable view of the filtered contact list */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * The user has logged in with an account successfully. Saves this account
     * into
     * @param account The account user used to log in successfully
     */
    void commitUserLoggedInSuccessfully(Account account);

    /**
     * Get the user account which he used to logged in to this application.
     * @return The account used to logged in to this application.
     */
    Account getUserAccount();

    /**
     * Return true if user has logged in successfully, false otherwise.
     * @return true if user has logged in successfully, false otherwise.
     */
    boolean isUserLoggedIn();

    /**
     * The user has logged out of his account successfully.
     */
    void commitUserLoggedOutSuccessfully();

    /**
     * The user has changed his old password to {@code newPassword}.
     */
    void commiteUserChangedPasswordSuccessfully(String newPassword);

    /**
     * Updates the auto-matching results.
     * @param newResults The new results to replace the old one.
     */
    void updateAutoMatchResult(AutoMatchResult newResults);

    /**
     * Retrieves the last updated results for the auto-matching.
     */
    AutoMatchResult getAutoMatchResult();
}
