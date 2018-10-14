package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;

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
