package seedu.address.model.account;

import seedu.address.logic.security.PasswordAuthentication;

/**
 * Account class represents a single Account that comprises of a username,
 * password, and role associated with an account.
 */
public class Account {
    public static final String USERNAME_CONSTRAINT = "Username cannot be empty.";
    public static final String PASSWORD_CONSTRAINT = "Password cannot be empty.";
    public static final String ROLE_CONSTRAINT = "Role is not specified.";

    private String username;
    private String password;
    private Role role;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.READ_ONLY_USER;
    }

    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void transformToHashedAccount() {
        this.password = PasswordAuthentication.getHashedPasswordFromPlainText(password);
    }

    /**
     * Write privilege refers to the ability to add or update contact.
     * A user has write privilege if he is allowed to add and update existing contact.
     * @return true if a user has write privilege, false otherwise.
     */
    public boolean hasWritePrivilege() {
        return role == Role.SUPER_USER;
    }

    /**
     * Delete privilege refers to the ability to delete a single contact, or all contact
     * from the stored data. A user has delete privilege if he is allowed to delete
     * existing stored data.
     * @return true if a user has delete privilege, false otherwise.
     */
    public boolean hasDeletePrivilege() {
        return role == Role.SUPER_USER;
    }

    /**
     * Account creation privilege refers to the ability to create a new account with a
     * username, password, and specifying a role. A user has account creation privilege
     * if he is allowed to create a new account, either for himself, or for other people.
     * @return true if a user has account creation privilege, false otherwise.
     */
    public boolean hasAccountCreationPrivilege() {
        return role == Role.SUPER_USER;
    }

    /**
     * The root user account hardcoded.
     * @return
     */
    public static Account getRootAccount() {
        return new Account("rootUser", "rootPassword", Role.SUPER_USER);
    }

    /**
     * An Account is equal to another Account if both of them have the
     * same username and password.
     * @param other Other Account to compare
     * @return true if both Accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return otherAccount.getUserName().equalsIgnoreCase(this.username)
                && otherAccount.getPassword().equals(this.password);
    }
}
