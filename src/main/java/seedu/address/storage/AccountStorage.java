package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;


/**
 * An interface used to manage storing of Account details. Currently, all Account
 * information will be stored locally.
 */
public interface AccountStorage {
    /**
     * Get the path for storing the Account file.
     * @return The local Path that the Account file is stored in.
     */
    Path getAccountStorageFilePath();

    /**
     * Easy way of getting AccountList. An AccountList stores all the Account information.
     * Throw an exception if the file is not found.
     * @return The local AccountList.
     * @throws DataConversionException if the data file cannot be converted correctly to username and password.
     * @throws IOException if file is not found.
     */
    AccountList getAccountList() throws DataConversionException, IOException;

    /**
     * Return the AccountList which is the file found in the {@code filePath}.
     * Throw an exception if the file is not found.
     * @param filePath the path where the local file resides.
     * @return The local AccountList.
     * @throws DataConversionException if the data file cannot be converted correctly to username and password.
     * @throws IOException if the file is not found.
     */
    AccountList getAccountList(Path filePath) throws DataConversionException, IOException;

    /**
     * Save the {@code account} into the local database. Easy way of calling.
     * @param account The newly created account that is to be saved into database.
     * @throws IOException if the file or directly cannot be created.
     */
    void saveAccount(Account account) throws IOException;

    /**
     * Save the {@code account} into the local database specified by {@code filePath}.
     * @param account The newly created Account that is to be saved into database.
     * @param filePath The Path to save the file in.
     * @throws IOException if the file or directory cannot be created.
     */
    void saveAccount(Account account, Path filePath) throws IOException;
}
