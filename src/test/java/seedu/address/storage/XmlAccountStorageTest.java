package seedu.address.storage;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.account.AccountList;


public class XmlAccountStorageTest {

    private static final String INVALID_FILEPATH = "someInvalidPath.xml";
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlAccountStorageTest");

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AccountList getAccountList(String filePath) throws Exception {
        return new XmlAccountStorage(Paths.get(filePath)).getAccountList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void getAccountList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        getAccountList(null);
    }

    @Test
    public void getAccountList_missingFile_nullResult() throws Exception {
        assertTrue(getAccountList(INVALID_FILEPATH) == null);
    }

    @Test
    public void getAccountList_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        getAccountList("NotXmlFormatAccountList.xml");
    }

    @Test
    public void getAccountList_invalidUsername_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        getAccountList("InvalidUsernameAccountList.xml");
    }

    @Test
    public void getAccountList_invalidAndValidUsername_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        getAccountList("InvalidAndValidUsernameAccountList copy.xml");
    }
}
