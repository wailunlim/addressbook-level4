package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.LoginSuccessEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * The Login Window. Users have to login before they are redirected to the Main Window.
 */
public class LoginWindow extends UiPart<Stage> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "LoginWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(LoginWindow.class);

    private Stage loginStage;
    private Config config;
    private Logic logic;
    private UserPrefs prefs;
    private ListElementPointer historySnapshot;

    @FXML
    private Label statusPlaceholder;

    @FXML
    private TextField loginCli;

    /**
     * Instantiates the Login Window
     * @param logic Logic parsed from UiManager
     */
    public LoginWindow(Stage loginStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, loginStage);

        // Set dependencies
        this.logic = logic;
        this.loginStage = loginStage;
        this.prefs = prefs;
        this.config = config;
        historySnapshot = logic.getHistorySnapshot();

        setWindowDefaultSize(prefs);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        loginStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        loginStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            loginStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            loginStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    void show() {
        loginStage.show();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleLogin() {
        try {
            CommandResult commandResult = logic.execute(loginCli.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            loginCli.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            statusPlaceholder.setText(commandResult.feedbackToUser);

            // If the login was successful, raise a new login success event and hide the login window.
            if (commandResult.feedbackToUser.equals("Successfully logged in.")) {
                raise(new LoginSuccessEvent());
                hide();
            }

        } catch (CommandException | ParseException | LackOfPrivilegeException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + loginCli.getText());
            statusPlaceholder.setText(e.getMessage());
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        loginCli.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = loginCli.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    private void hide() {
        loginStage.hide();
    }
}
