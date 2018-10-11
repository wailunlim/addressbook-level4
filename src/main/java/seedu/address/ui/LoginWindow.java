package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * The Login Window. Users have to login before they are redirected to the Main Window.
 */
public class LoginWindow extends UiPart<Stage> {

    private static final String FXML = "LoginWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    @FXML
    private Label lblStatusPlaceholder;

    @FXML
    private TextField usernamePlaceholder;

    @FXML
    private TextField pwPlaceholder;

    @FXML
    private Button loginBtn;

    @FXML
    private Text forgetPw;

    /**
     * Instantiates the Login Window
     * @param primaryStage Primary stage parsed from UiManager
     * @param logic Logic parsed from UiManager
     */
    public LoginWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
    }

    @FXML
    private void Login(ActionEvent actionEvent) {
        // Search for user in AccountList and match the password
        // Temporary login check
        if (usernamePlaceholder.getText().equals("user")
                && pwPlaceholder.getText().equals("pw")) {
            lblStatusPlaceholder.setText("Successfully logged in");
            // Start main app

        } else {
            lblStatusPlaceholder.setText("Authentication failed");
        }
    }

    /**
     * Shows password reset request page.
     * Password reset request sent to admin.
     */
    public void handleForgetPw() {
        // Send to password reset page
    }



}
