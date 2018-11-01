package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.contact.Contact;

/**
 * Provides a handle for {@code ServiceListPanel} containing the list of {@code ServiceCard}.
 */
public class ServiceListPanelHandle extends NodeHandle<ListView<Contact>> {
    public static final String SERVICE_LIST_VIEW_ID = "#serviceListView";

    private static final String CARD_PANE_ID = "#cardPane";

    public ServiceListPanelHandle(ListView<Contact> serviceListPanelNode) {
        super(serviceListPanelNode);
    }

    /**
     * Navigates the listview to display {@code contact}.
     */
    public void navigateToCard(Contact contact) {
        if (!getRootNode().getItems().contains(contact)) {
            throw new IllegalArgumentException("Client does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(contact);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the client card handle of a client associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ServiceCardHandle getServiceCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ServiceCardHandle::new)
                .filter(handle -> handle.equals(getPerson(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Contact getPerson(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
