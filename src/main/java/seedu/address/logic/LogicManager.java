package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AutoMatchResult;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, LackOfPrivilegeException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command;
            if (model.isUserLoggedIn()) {
                command = addressBookParser.parseCommand(commandText);
            } else {
                command = addressBookParser.parseCommandBeforeLoggedIn(commandText);
            }
            return command.execute(model, history);
        } finally {
            // do not add to history for logging in as it reveals user password.
            // do not add to history if logging out, as it is terminating a session.
            if (!commandText.contains(LoginCommand.COMMAND_WORD) && !commandText.equals(LogoutCommand.COMMAND_WORD)) {
                history.add(commandText);
            }
        }
    }

    @Override
    public ObservableList<Contact> getFilteredPersonList() {
        return model.getFilteredContactList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public AutoMatchResult getAutoMatchResult() {
        return model.getAutoMatchResult();
    }
}
