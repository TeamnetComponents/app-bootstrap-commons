package ro.teamnet.bootstrap.extend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoRepositoryBean
public class AppRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements AppRepository<T, ID> {

    @SuppressWarnings("UnusedDeclaration")
    private EntityManager entityManager;

    public AppRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    // There are two constructors to choose from, either can be used.
    public AppRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);

        // This is the recommended method for accessing inherited class dependencies.
        this.entityManager = entityManager;
    }

    @Override
    public Page<T> findAll(Pageable pag) {
        AppPageable pageable = (AppPageable) pag;
        Page<T> page;
        if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
            Specification<T> specification = createSpecification(pageable);
            page = super.findAll(specification, pageable);
        } else {
            page = super.findAll(pageable);

        }
        if (pageable instanceof PageRequest)
            return page;

        return new PageResource<T>(new AppPageImpl<T>(page != null ? page.getContent() : null,
                pageable, page != null ? page.getTotalElements() : 0,
                pageable.getFilters()));
    }

    @Override
    public AppPage<T> findAll(AppPageable appPageable) {
        Filters filters = appPageable.getFilters() == null
                ? new Filters()
                : appPageable.getFilters();

        Page<T> page = filters.isEmpty()
                ? super.findAll(appPageable)
                : super.findAll(createSpecification(appPageable), appPageable);

        List<T> content = new ArrayList<>();
        for (T item : page) {
            content.add((T) item);
        }

        return new AppPageImpl<T>(content, appPageable, page.getTotalElements(), filters);
    }

    @Override
    public List<T> findAll(Filters filters, Sort sort) {
        Specification<T> specification = SpecificationBuilder.<T>createSpecification(filters);
        return findAll(specification, sort);
    }

    public Specification<T> createSpecification(final AppPageable pageable) {
        return SpecificationBuilder.<T>createSpecification(pageable.getFilters());
    }
}
