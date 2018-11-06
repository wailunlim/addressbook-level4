package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;

public class AutoMatchCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_validContactTypeAndId_shouldNotThrow() throws ParseException {
        ExpectedException.none();
        new AutoMatchCommand("client", "1");
    }

    @Test
    public void constructor_nullContactType_throwsNullPointerException() throws ParseException {
        thrown.expect(NullPointerException.class);
        new AutoMatchCommand(null, "1");
    }

    @Test
    public void constructor_nullContactId_throwsNullPointerException() throws ParseException {
        thrown.expect(NullPointerException.class);
        new AutoMatchCommand("client", null);
    }

    @Test
    public void constructor_invalidContactType_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        new AutoMatchCommand("dog", "1");
    }
}
