package ro.teamnet.bootstrap.extend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Filtering option applied to queries. It consists of a collection of filtering conditions.
 */
public class Filters implements Iterable<Filter> {
    private List<Filter> filters;

    public Filters() {
        this.filters = new ArrayList<>();
    }

    public Filters(Collection<Filter> filters) {
        this();
        this.addFilters(filters);
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void addFilters(Collection<Filter> filters) {
        this.filters.addAll(filters);
    }

    public boolean isEmpty() {
        return filters.isEmpty();
    }

    @Override
    public Iterator<Filter> iterator() {
        return filters.iterator();
    }

    /**
     * Sets the filters with the ones provided.
     *
     * @param filters A list of filters to replace the underlying one.
     */
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
