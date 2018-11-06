package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ContactType;

public class AutoMatchCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_validContactTypeAndId_shouldNotThrow() throws ParseException {
        ExpectedException.none();
        new AutoMatchCommand("client", 1);
    }

    @Test
    public void constructor_nullContactType_throwsNullPointerException() throws ParseException {
        thrown.expect(NullPointerException.class);
        new AutoMatchCommand(null, 1);
    }

    @Test
    public void constructor_invalidContactType_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        new AutoMatchCommand("dog", 1);
    }

    @Test
    public void equals_exactAutoMatchCommands_shouldBeEqual() throws ParseException {
        ExpectedException.none();
        AutoMatchCommand command = new AutoMatchCommand(ContactType.CLIENT.toString(), 1);
        assertEquals(command, command);
    }

    @Test
    public void equals_sameAutoMatchCommands_shouldBeEqual() throws ParseException {
        ExpectedException.none();
        AutoMatchCommand commandA = new AutoMatchCommand(ContactType.CLIENT.toString(), 1);
        AutoMatchCommand commandB = new AutoMatchCommand(ContactType.CLIENT.toString(), 1);
        assertEquals(commandA, commandB);
    }

    @Test
    public void equals_differentTypeAutoMatchCommands_shouldNotBeEqual() throws ParseException {
        ExpectedException.none();
        AutoMatchCommand commandA = new AutoMatchCommand(ContactType.CLIENT.toString(), 1);
        AutoMatchCommand commandB = new AutoMatchCommand(ContactType.VENDOR.toString(), 1);
        assertNotEquals(commandA, commandB);
    }

    @Test
    public void equals_differentIdAutoMatchCommands_shouldNotBeEqual() throws ParseException {
        ExpectedException.none();
        AutoMatchCommand commandA = new AutoMatchCommand(ContactType.CLIENT.toString(), 1);
        AutoMatchCommand commandB = new AutoMatchCommand(ContactType.CLIENT.toString(), 2);
        assertNotEquals(commandA, commandB);
    }

    @Test
    public void equals_differentClasses_shouldNotBeEqual() throws ParseException {
        ExpectedException.none();
        AutoMatchCommand command = new AutoMatchCommand(ContactType.CLIENT.toString(), 1);
        Object notCommand = new Object();
        assertNotEquals(command, notCommand);
    }
}
