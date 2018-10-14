package seedu.address.storage;

import java.util.*;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;
import seedu.address.model.account.Role;

/**
 * An Immutable Account that is serializable to XML format
 */
@XmlRootElement(name = "accountmanager")
public class XmlSerializableAccount {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Database contains duplicate account.";

    @XmlElement
    private List<XmlAdaptedAccount> accounts;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAccount() {
        accounts = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAccount(Account src) {
        this();
        accounts = new ArrayList<>();
        accounts.add(new XmlAdaptedAccount(src));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public AccountList toModelType() throws IllegalValueException {
        AccountList accountList = new AccountList();
        for (XmlAdaptedAccount acc : accounts) {
            Account account = acc.toModelType();
            if (accountList.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            accountList.addAccount(account);
        }
        return accountList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAccount)) {
            return false;
        }
        return accounts.equals(((XmlSerializableAccount) other).accounts);
    }
}

/**
 * JAXB-friendly version of the Person.
 */
class XmlAdaptedAccount {
    @XmlElement(required = true)
    private String userName;
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
    public XmlAdaptedAccount(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedAccount(Account source) {
        userName = source.getUserName();
        password = source.getPassword();
        role = source.getRole();
    }

    /**
     * Converts this jaxb-friendly adapted account object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Account toModelType() throws IllegalValueException {

        if (userName == null) {
            throw new IllegalValueException("Username is null.");
        }
        if (password == null) {
            throw new IllegalValueException("Password is null.");
        }

        if (role == null) {
            throw new IllegalValueException("Role is null.");
        }

        return new Account(userName, password, role);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedAccount otherAccount = (XmlAdaptedAccount) other;
        return Objects.equals(userName, otherAccount.userName)
                && Objects.equals(password, otherAccount.password)
                && Objects.equals(role, otherAccount.role);
    }
}

