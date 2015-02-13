package ro.teamnet.bootstrap.extend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.teamnet.bootstrap.exception.InvalidNumberOfFiltersException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
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
        List<Filter> filters = appPageable.getFilters() == null
                ? new ArrayList<Filter>()
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

    public Specification<T> createSpecification(final AppPageable pageable) {

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates= new ArrayList<>();
                String filterPattern;
                PredicateUtil predicateUtil = new PredicateUtil(cb,root);
                Predicate predicate = null;

                for (Filter filter : pageable.getFilters()) {
                    switch (filter.getType()) {
                        case EQUAL:
                            predicate = predicateUtil.getEqualPredicate(filter.getProperty(),filter.getValue());
                            break;
                        case IN:
                            predicate = predicateUtil.getInPredicate(filter.getProperty(),filter.getValues());
                            break;
                        case LIKE:
                        case STARTS_WITH:
                        case ENDS_WITH:
                            // like() method is case-sensitive. Used toUpperCase() method for case - insensitive search.
                            filterPattern = (Filter.filterPatternBuilder(filter.getValue(),filter.getType())).toUpperCase();
                            predicate = predicateUtil.getLikePredicate(filter.getProperty(),filterPattern);
                            break;
                        case LESS_THAN:
                            predicate = predicateUtil.getLessThanPredicate(filter.getProperty(),filter.getValue());
                            break;
                        case LESS_THAN_OR_EQUAL:
                            predicate = predicateUtil.getLessThanOrEqualPredicate(filter.getProperty(),filter.getValue());
                            break;
                        case GREATER_THAN:
                            predicate = predicateUtil.getGreaterThanPredicate(filter.getProperty(),filter.getValue());
                            break;
                        case GREATER_THAN_OR_EQUAL:
                            predicate = predicateUtil.getGreaterThanOrEqualPredicate(filter.getProperty(),filter.getValue());

                            break;
                        case BETWEEN:
                            try {
                                predicate = predicateUtil.getBetweenPredicate(filter.getProperty(),filter.getValues());
                            }catch(InvalidNumberOfFiltersException e){
                                // tratare exceptie
                            }
                            break;
                    }
                    if(filter.getNegation()){
                        predicates.add(predicateUtil.applyNot(predicate));
                    }else{
                        predicates.add(predicate);
                    }
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }


        };
    }







}
