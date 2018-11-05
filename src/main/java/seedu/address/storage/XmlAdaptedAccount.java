package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Role;

/**
 * JAXB-friendly version of the Client.
 */
public class XmlAdaptedAccount {
    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private Role role;

    /**
     * Constructs an XmlAdaptedAccount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAccount() {}

    /**
     * Constructs an {@code XmlAdaptedAccount} with the given account details.
     */
    public XmlAdaptedAccount(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Converts a given account into this class for JAXB use.
     *
     * @param source the account given
     */
    public XmlAdaptedAccount(Account source) {
        username = source.getUserName();
        password = source.getPassword();
        role = source.getRole();
    }

    /**
     * Converts this jaxb-friendly adapted account object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted client
     */
    public Account toModelType() throws IllegalValueException {

        if (username == null || username.equals("")) {
            throw new IllegalValueException(Account.USERNAME_CONSTRAINT);
        }
        if (password == null || password.equals("")) {
            throw new IllegalValueException(Account.PASSWORD_CONSTRAINT);
        }

        if (role == null) {
            throw new IllegalValueException(Account.ROLE_CONSTRAINT);
        }

        return new Account(username, password, role);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAccount)) {
            return false;
        }

        XmlAdaptedAccount otherAccount = (XmlAdaptedAccount) other;
        return Objects.equals(username, otherAccount.username)
                && Objects.equals(password, otherAccount.password)
                && Objects.equals(role, otherAccount.role);
    }
}
