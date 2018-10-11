package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        assignTags(person);
    }

    /**
     * Assigns all tags for the person with a label.
     *
     * @param person Current person to assign tags to
     */
    private void assignTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = createLabelforTag(tag.tagName);
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Creates a label for the tag. Label is set to grey if the tag indicates price.
     * Otherwise, it would be set to pink as default.
     *
     * @param tagName Name of the tag
     * @return new Label for the tag
     */
    private Label createLabelforTag(String tagName) {
        Label tagLabel = new Label(tagName);

        if (tagName.toLowerCase().contains("price")) {
            tagLabel.getStyleClass().add("grey");
        } else {
            tagLabel.getStyleClass().add("pink");
        }

        return tagLabel;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
