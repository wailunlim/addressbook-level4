package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.HelpWindowHandle;
import seedu.address.logic.commands.HelpCommand;

/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class HelpCommandSystemTest extends AddressBookSystemTest {
    /**
     * ATTENTION!!!! : On some computers, this test may fail when run on "
     + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
     + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
     + "tests on headless mode. See UsingGradle.adoc on how to do so.";
     */
    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void help_multipleCommands_onlyOneHelpWindowOpen() {
        getMainMenu().openHelpWindowUsingMenu();

        getMainWindowHandle().focus();
        getMainMenu().openHelpWindowUsingAccelerator();

        getMainWindowHandle().focus();
        executeCommand(HelpCommand.COMMAND_WORD);

        assertEquals(1, guiRobot.getNumberOfWindowsShown(HelpWindowHandle.HELP_WINDOW_TITLE));
    }
}
