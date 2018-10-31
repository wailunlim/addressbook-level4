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
    public final Map<Contact, Collection<Service>> resultMap;
    private final Contact target;

    public AutoMatchResult(Contact target) {
        this(target, new HashMap<>());
    }

    private AutoMatchResult(Contact target, Map<Contact, Collection<Service>> resultMap) {
        this.target = target;
        this.resultMap = resultMap;
    }

    public static AutoMatchResult mergeResults(AutoMatchResult resultA, AutoMatchResult resultB) {
        Map<Contact, Collection<Service>> resultMap = Stream.of(resultA.resultMap, resultB.resultMap)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        AutoMatchResult newResult = new AutoMatchResult(resultA.target, resultMap);
        return newResult;
    }
}
