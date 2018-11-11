package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LogoutRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Log the user out of the application.
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log out of the application. "
            + "\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully logged out.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.commitUserLoggedOutSuccessfully();
        EventsCenter.getInstance().post(new LogoutRequestEvent());
        history.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
