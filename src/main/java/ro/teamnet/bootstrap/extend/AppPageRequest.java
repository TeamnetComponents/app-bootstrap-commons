package ro.teamnet.bootstrap.extend;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * O concreta de tip pagina venita de la client
 *
 */
public class AppPageRequest extends PageRequest implements AppPageable, Serializable {

    private Filters filters = new Filters();
    private String locale;

    public AppPageRequest(int page, int size) {
        super(page, size);
        if (filters == null) {
            this.filters = new Filters();
        }
    }

    public AppPageRequest(int page, int size, Sort.Direction direction, String... properties) {
        super(page, size, direction, properties);
        if (filters == null) {
            this.filters = new Filters();
        }
    }

    public AppPageRequest(int page, int size, Filters filters) {
        super(page, size);
        this.filters = filters;
        if (filters == null) {
            this.filters = new Filters();
        }
    }

    public AppPageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
        if (filters == null) {
            this.filters = new Filters();
        }
    }

    public AppPageRequest(int page, int size, Sort sort, Filters filters) {
        super(page, size, sort);
        this.filters = filters;
        if (filters == null) {
            this.filters = new Filters();
        }
    }


    public AppPageRequest(int page, int size, Sort sort, Filters filters, String locale) {
        super(page, size, sort);
        this.filters = filters;
        if (filters == null) {
            this.filters = new Filters();
        }
        this.locale = locale;
    }


    @Override
    public Filters getFilters() {
        return filters;
    }

    @Override
    public String locale() {
        return this.locale;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppPageRequest)) return false;
        if (!super.equals(o)) return false;

        AppPageRequest that = (AppPageRequest) o;

        return !(filters != null ? !filters.equals(that.filters) : that.filters != null) && !(locale != null ? !locale.equals(that.locale) : that.locale != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (filters != null ? filters.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        return result;
    }
}
