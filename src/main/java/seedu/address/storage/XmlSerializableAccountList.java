package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;

/**
 * An Immutable Account that is serializable to XML format
 */
@XmlRootElement(name = "accountmanager")
public class XmlSerializableAccountList {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Database contains duplicate account.";

    @XmlElement
    private List<XmlAdaptedAccount> accounts;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAccountList() {
        accounts = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAccountList(AccountList src) {
        this();
        System.out.println(src.getList().size());
        for(Account c : src.getList()) {
            System.out.println(c.getUserName());
        }
        accounts.addAll(src.getList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
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

        if (!(other instanceof XmlSerializableAccountList)) {
            return false;
        }
        return accounts.equals(((XmlSerializableAccountList) other).accounts);
    }
}
