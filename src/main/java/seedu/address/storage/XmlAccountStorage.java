package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.account.Account;
import seedu.address.model.account.AccountList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class XmlAccountStorage implements AccountStorage {
    private Path accountListPath = Paths.get("data" , "accountlist.xml");
    private static final Logger logger = LogsCenter.getLogger(XmlAccountStorage.class);

    public XmlAccountStorage() {
    }

    @Override
    public Path getAccountStorageFilePath() {
        return accountListPath;
    }

    @Override
    public AccountList getAccountList() throws DataConversionException,
            FileNotFoundException{
        return getAccountList(accountListPath);
    }

    @Override
    public AccountList getAccountList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Account file " + filePath + " not found");
            return null;
        }

        XmlSerializableAccount xmlAccount = XmlFileStorage.loadAccountDataFromSaveFile(filePath);
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

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveAccountDataToFile(filePath, new XmlSerializableAccount(account));
    }
}
