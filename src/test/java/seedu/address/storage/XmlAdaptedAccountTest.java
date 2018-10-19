package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAccount.ROSE;
import static seedu.address.testutil.TypicalAccount.SIMUN;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Role;
import seedu.address.testutil.Assert;


public class XmlAdaptedAccountTest {
    private static final String EMPTY_USERNAME = "";
    private static final String EMPTY_PASSWORD = "";
    private static final Role NULL_ROLE = null;


    private static final String VALID_USERNAME = "Victoria95";
    private static final String VALID_USERNAME2 = "Samantha95";
    private static final String VALID_PASSWORD = "myP@ssw0rd";
    private static final String VALID_PASSWORD2 = "myP@ssw1rd";
    private static final Role VALID_ROLE_SUPERUSER = Role.SUPER_USER;
    private static final Role VALID_ROLE_READONLYUSER = Role.READ_ONLY_USER;

    @Test
    public void toModelType_validSuperUserAccount_returnAccount() throws Exception {
        XmlAdaptedAccount account = new XmlAdaptedAccount(SIMUN);
        assertEquals(SIMUN, account.toModelType());
    }

    @Test
    public void toModelType_validReadOnlyUserAccount_returnAccount() throws Exception {
        XmlAdaptedAccount account = new XmlAdaptedAccount(ROSE);
        assertEquals(ROSE, account.toModelType());
    }

    @Test
    public void toModelType_emptyUsername_throwsIllegalValueException() {
        XmlAdaptedAccount account =
                new XmlAdaptedAccount(EMPTY_USERNAME, VALID_PASSWORD, VALID_ROLE_SUPERUSER);
        String expectedMessage = Account.USERNAME_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        XmlAdaptedAccount person = new XmlAdaptedAccount(null, VALID_PASSWORD, VALID_ROLE_SUPERUSER);
        String expectedMessage = Account.USERNAME_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emptyPassword_throwsIllegalValueException() {
        XmlAdaptedAccount account =
                new XmlAdaptedAccount(VALID_USERNAME, EMPTY_PASSWORD, VALID_ROLE_SUPERUSER);
        String expectedMessage = Account.PASSWORD_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        XmlAdaptedAccount person = new XmlAdaptedAccount(VALID_USERNAME, null, VALID_ROLE_SUPERUSER);
        String expectedMessage = Account.PASSWORD_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        XmlAdaptedAccount account =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD, NULL_ROLE);
        String expectedMessage = Account.ROLE_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void equals_success() {
        XmlAdaptedAccount account1 =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD, VALID_ROLE_SUPERUSER);

        XmlAdaptedAccount account2 =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD, VALID_ROLE_SUPERUSER);
        assertTrue(account1.equals(account2));
    }

    @Test
    public void equals_differentUsername_failure() {
        XmlAdaptedAccount account1 =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD, VALID_ROLE_SUPERUSER);

        XmlAdaptedAccount account2 =
                new XmlAdaptedAccount(VALID_USERNAME2, VALID_PASSWORD, VALID_ROLE_SUPERUSER);
        assertFalse(account1.equals(account2));
    }

    @Test
    public void equals_differentPassword_failure() {
        XmlAdaptedAccount account1 =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD, VALID_ROLE_SUPERUSER);

        XmlAdaptedAccount account2 =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD2, VALID_ROLE_SUPERUSER);
        assertFalse(account1.equals(account2));
    }

    @Test
    public void equals_differentRole_failure() {
        XmlAdaptedAccount account1 =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD, VALID_ROLE_SUPERUSER);

        XmlAdaptedAccount account2 =
                new XmlAdaptedAccount(VALID_USERNAME, VALID_PASSWORD, VALID_ROLE_READONLYUSER);
        assertFalse(account1.equals(account2));
    }
}
