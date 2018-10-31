package seedu.address.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;

/**
 * The results from an auto-matching operation.
 */
public class AutoMatchResult {
    private final Map<Contact, Collection<Service>> resultMap;
    private final Contact target;

    public AutoMatchResult(Contact target) {
        this(target, new HashMap<>());
    }

    private AutoMatchResult(Contact target, Map<Contact, Collection<Service>> resultMap) {
        this.target = target;
        this.resultMap = resultMap;
    }

    /**
     * Adds a {@code Collection} of {@code Service} that the {@code Contact} was shortlisted for.
     * @param contact
     * @param services
     */
    public void put(Contact contact, Collection<Service> services) {
        if (services.size() > 0) {
            resultMap.put(contact, services);
        }
    }

    /**
     * Gets a {@code Collection} of {@code Contact} that was shortlisted.
     * @return
     */
    public Collection<Contact> getContacts() {
        return resultMap.keySet();
    }

    /**
     * Gets the mapping of a {@code Collection} of {@code Service} that was matched for the {@code Contact}.
     * @return
     */
    public Map<Contact, Collection<Service>> getContactAndServicesMap() {
        return resultMap;
    }

    /**
     * Prunes all {@code Contact} from {@code resultMap} that does not have at least one {@code Service}.
     */
    private void prune() {
        Collection<Contact> contactsToPruneStream = resultMap
                .keySet()
                .stream()
                .filter(contact -> resultMap.get(contact).size() < 1)
                .collect(Collectors.toList());
        for (Contact contact : contactsToPruneStream) {
            resultMap.remove(contact);
        }
    }

    /**
     * Merges 2 {@code AutoMatchResult} into one. To be used as a combiner.
     * @param resultA First result.
     * @param resultB Second result.
     * @return Merged result.
     */
    public static AutoMatchResult mergeResults(AutoMatchResult resultA, AutoMatchResult resultB) {
        Map<Contact, Collection<Service>> resultMap = Stream.of(resultA.resultMap, resultB.resultMap)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        AutoMatchResult newResult = new AutoMatchResult(resultA.target, resultMap);
        return newResult;
    }
}
