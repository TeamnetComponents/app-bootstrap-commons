package ro.teamnet.bootstrap.extend;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public interface AppPageable extends Pageable, Serializable {
    public Filters getFilters();

    public String locale();
}
