package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;

/**
 * Panel containing the list of persons.
 */
public class ServiceListPanel extends UiPart<Region> {
    private static final String FXML = "ServiceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ServiceListPanel.class);
    private final String serviceType;

    @FXML
    private ListView<Contact> serviceListView;

    public ServiceListPanel(ObservableList<Contact> contactList, String serviceType) {
        super(FXML);
        setConnections(contactList);
        registerAsAnEventHandler(this);
        this.serviceType = serviceType;
    }

    private void setConnections(ObservableList<Contact> contactList) {
        serviceListView.setItems(contactList);
        serviceListView.setCellFactory(listView -> new ServiceListViewCell());

        // Disable selection from table
        serviceListView.setMouseTransparent(true);
        serviceListView.setFocusTraversable(false);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a service provided by the {@code ServiceProvider}
     * using a {@code ServiceCard}.
     */
    class ServiceListViewCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ServiceCard(contact, serviceType).getRoot());
            }
        }
    }

}
