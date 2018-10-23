package seedu.address.model.account;

/**
 * A role refers to what privilege a user account has. A SUPER_USER is the user
 * with full capabilities. A READ_ONLY_USER cannot add, edit, and delete any entries
 * in the database.
 */
public enum Role {
    SUPER_USER, READ_ONLY_USER
}
