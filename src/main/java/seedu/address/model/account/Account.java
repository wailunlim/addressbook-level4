package seedu.address.model.account;

public class Account {
    private String userName;
    private String password;
    private Role role;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    /**
     * An Account is equal to another Account if both of them have the
     * same userName, password, and role.
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
        return otherAccount.getUserName().equals(this.userName)
                && otherAccount.getPassword().equals(this.password)
                && otherAccount.getRole().equals(this.role);
    }
}
