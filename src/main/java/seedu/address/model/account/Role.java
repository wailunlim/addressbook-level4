package seedu.address.model.account;

import seedu.address.logic.security.AccountManager;

/**
 * A role refers to what privilege a user account has. A SUPER_USER is the user
 * with full capabilities. A READ_ONLY_USER cannot add, edit, and delete any entries
 * in the database.
 */
public enum Role {
    SUPER_USER, READ_ONLY_USER;

    /**
     * Write privilege refers to the ability to add or update contact.
     * A user has write privilege if he is allowed to add and update existing contact.
     * @return true if a user has write privilege, false otherwise.
     */
    public static boolean hasWritePrivilege() {
        return AccountManager.getCurrentUserRole() == SUPER_USER;
    }

    /**
     * Delete privilege refers to the ability to delete a single contact, or all contact
     * from the stored data. A user has delete privilege if he is allowed to delete
     * existing stored data.
     * @return true if a user has delete privilege, false otherwise.
     */
    public static boolean hasDeletePrivilege() {
        return AccountManager.getCurrentUserRole() == SUPER_USER;
    }
}
