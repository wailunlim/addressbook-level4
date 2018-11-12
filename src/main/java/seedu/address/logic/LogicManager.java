package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditPasswordCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RegisterAccountCommand;
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
    private static final String PARAMETER_CENSORED = " <Parameter Censored>";

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
        Command command = null;

        try {
            if (model.isUserLoggedIn()) {
                command = addressBookParser.parseCommand(commandText);
            } else {
                command = addressBookParser.parseCommandBeforeLoggedIn(commandText);
            }
            return command.execute(model, history);
        } finally {
            // do not add to history if logging out, as it is terminating a session.
            if (!commandText.equals(LogoutCommand.COMMAND_WORD)) {
                // if history is any of these instances, censored the parameter as it contains
                // sensitive information
                if (command instanceof LoginCommand) {
                    history.add(LoginCommand.COMMAND_WORD + PARAMETER_CENSORED);
                } else if (command instanceof RegisterAccountCommand) {
                    history.add(RegisterAccountCommand.COMMAND_WORD + PARAMETER_CENSORED);
                } else if (command instanceof EditPasswordCommand) {
                    history.add(EditPasswordCommand.COMMAND_WORD + PARAMETER_CENSORED);
                } else {
                    history.add(commandText);
                }
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
