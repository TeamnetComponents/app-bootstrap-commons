package ro.teamnet.bootstrap.extend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Tip de baza pentru toate clasele din layer-ul de repository
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface AppRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    Page<T> findAll(Pageable pageable);

    AppPage<T> findAll(AppPageable pageable);

    /**
     * Retrieves a collection data based on a list of filters and sorting order.
     *
     * @param filters Filtering criteria.
     * @param sort    A sorting order.
     * @return A collection of items.
     */
    List<T> findAll(Filters filters, Sort sort);

}
