package seedu.address.model;

import java.util.List;
import java.util.stream.Stream;

/**
 * Searchable model
 */
public interface Searchable {
    /**
     * Filters a stream of Searchable for the specified query string.
     *
     * @param searchableStream Source stream
     * @param queryString      Query string
     * @return
     */
    public List<Searchable> search(Stream<Searchable> searchableStream, String queryString);
}
