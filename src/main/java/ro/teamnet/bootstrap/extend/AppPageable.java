package ro.teamnet.bootstrap.extend;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Interfata de tip pagina transmisa de un client web pentru a putea pagina datele
 */
public interface AppPageable extends Pageable, Serializable {
    /**
     * Filtrarea datelor
     * @return
     */
    public Filters getFilters();

    /**
     * Localizarea pentru date
     * @return
     */
    public String locale();
}
