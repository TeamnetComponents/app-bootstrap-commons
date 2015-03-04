package ro.teamnet.bootstrap.extend;

import org.springframework.data.jpa.domain.Specification;
import ro.teamnet.bootstrap.exception.InvalidNumberOfFiltersException;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for building specifications.
 */
public class SpecificationBuilder {

    public static <T> Specification<T> createSpecification(final Filters filters) {

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                PredicateUtil predicateUtil = new PredicateUtil(cb);
                Predicate predicate = null;

                for (Filter filter : filters) {
                    Path path = root.get(filter.getProperty());
                    switch (filter.getType()) {
                        case LIKE:
                        case STARTS_WITH:
                        case ENDS_WITH:
                            if (path.getJavaType() == String.class) {
                                String filterPattern = (Filter.filterPatternBuilder(filter.getValue(), filter.getType()));
                                predicate = predicateUtil.getLikePredicate(path, filterPattern, filter.getCaseSensitive());
                                break;
                            }
                        case EQUAL:
                            predicate = predicateUtil.getEqualPredicate(path, filter.getValue(), filter.getCaseSensitive());
                            break;
                        case IN:
                            predicate = predicateUtil.getInPredicate(path, filter.getValues(), filter.getCaseSensitive());
                            break;
                        case LESS_THAN:
                            predicate = predicateUtil.getLessThanPredicate(path, filter.getValue(), filter.getCaseSensitive());
                            break;
                        case LESS_THAN_OR_EQUAL:
                            predicate = predicateUtil.getLessThanOrEqualPredicate(path, filter.getValue(), filter.getCaseSensitive());
                            break;
                        case GREATER_THAN:
                            predicate = predicateUtil.getGreaterThanPredicate(path, filter.getValue(), filter.getCaseSensitive());
                            break;
                        case GREATER_THAN_OR_EQUAL:
                            predicate = predicateUtil.getGreaterThanOrEqualPredicate(path, filter.getValue(), filter.getCaseSensitive());

                            break;
                        case BETWEEN:
                            try {
                                predicate = predicateUtil.getBetweenPredicate(path, filter.getValues(), filter.getCaseSensitive());
                            } catch (InvalidNumberOfFiltersException e) {
                                // tratare exceptie
                            }
                            break;
                    }

                    if (filter.getNegation()) {
                        predicates.add(predicateUtil.applyNot(predicate));
                    } else {
                        predicates.add(predicate);
                    }
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}