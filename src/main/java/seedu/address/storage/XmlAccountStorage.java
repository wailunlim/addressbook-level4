package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;

/**
 * A class to access Account data stored as an xml file on the hard disk.
 */
public class XmlAccountStorage implements AccountStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlAccountStorage.class);

    private Path accountListPath = Paths.get("data" , "accountlist.xml");

    public XmlAccountStorage() {
    }

    @Override
    public Path getAccountStorageFilePath() {
        return accountListPath;
    }

    @Override
    public AccountList getAccountList() throws DataConversionException,
            FileNotFoundException {
        return getAccountList(accountListPath);
    }

    @Override
    public AccountList getAccountList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Account file " + filePath + " not found. Creating a default file.");
            return null;
        }

        XmlSerializableAccountList xmlAccount = XmlFileStorage.loadAccountDataFromSaveFile(filePath);
        try {
            return xmlAccount.toModelType();
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAccount(Account account) throws IOException {
        saveAccount(account, accountListPath);
    }

    @Override
    public void saveAccount(Account account, Path filePath) throws IOException {
        requireNonNull(account);
        requireNonNull(filePath);

        try {
            AccountList accountList = getAccountList();
            accountList.getList().add(account);
            XmlFileStorage.saveAccountDataToFile(filePath, new XmlSerializableAccountList(accountList));
        } catch (DataConversionException e) {
            System.out.println("fail " + e.getMessage());
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
        }
    }

    @Override
    public void populateRootAccountIfMissing(Account account) {
        requireNonNull(account);

        if(!FileUtil.isFileExists(accountListPath)) {
            try {
                FileUtil.createFile(accountListPath);
                AccountList accountList = new AccountList();
                accountList.addAccount(account);
                XmlFileStorage.saveAccountDataToFile(accountListPath, new XmlSerializableAccountList(accountList));
            } catch (IOException e) {
                System.out.println("fail here");
                logger.info("Account: Unable to create file or directory: " + accountListPath + e.getMessage());
            }
        }
    }
}
