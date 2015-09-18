package ro.teamnet.bootstrap.extend;

import com.google.common.collect.Iterables;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ClassUtils;
import ro.teamnet.bootstrap.exception.InvalidNumberOfFiltersException;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static ro.teamnet.bootstrap.extend.Filter.FilterType.BETWEEN;

/**
 * Utility class for building specifications.
 */
public class SpecificationBuilder {

    /**
     * Retrieves a simple or compound attribute path based on the given filter, by splitting the filter into attributes.
     *
     * @param root   the root entity
     * @param filter a simple or compound filter
     * @return the attribute path
     */
    private static Path findPath(Root root, Filter filter) {
        Path path = root;
        for (String attribute : filter.getAttributes()) {
            path = path.get(attribute);
        }
        return path;
    }

    /**
     * Check if the java type of the given path is a boolean.
     *
     * @param path - the path to check
     * @return true if the given path is a boolean
     */
    private static boolean isBoolean(Path path) {
        return ClassUtils.isAssignable(Boolean.class, path.getJavaType());
    }

    private static Boolean isDate(Path path) {
        return ClassUtils.isAssignable(LocalDate.class, path.getJavaType())
                || ClassUtils.isAssignable(DateTime.class, path.getJavaType())
                || ClassUtils.isAssignable(Date.class, path.getJavaType());
    }

    public static <T> Specification<T> createSpecification(final Filters filters) {

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                PredicateUtil predicateUtil = new PredicateUtil(cb);

                for (Filter filter : filters) {
                    Path path = findPath(root, filter);

                    if (isBoolean(path)) {
                        predicates.add(getBooleanPredicate(predicateUtil, filter, path));
                    } else if (isDate(path)) {
                        Predicate datePredicate = getDatePredicate(predicateUtil, filter, path);
                        if (datePredicate != null) {
                            predicates.add(datePredicate);
                        }
                    } else {
                        predicates.add(getNonBooleanPredicate(predicateUtil, filter, path));
                    }
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

            /**
             * Creates a predicate by applying a custom {@link Filter} to a boolean expression.
             * Supported filter types are: {@link ro.teamnet.bootstrap.extend.Filter.FilterType#EQUAL} and
             * {@link ro.teamnet.bootstrap.extend.Filter.FilterType#LIKE}.
             * If the filter type is unsupported or the filter value cannot be converted to a valid boolean expression,
             * the returned predicate will always evaluate as false.
             *
             * @param predicateUtil utility class for building predicates
             * @param filter a custom filter
             * @param path the boolean expression to evaluate
             * @return a predicate that tests the expression against a custom filter
             */
            private Predicate getBooleanPredicate(PredicateUtil predicateUtil, Filter filter, Path path) {
                if (Filter.FilterType.EQUAL.equals(filter.getType()) || filter.getType().equals(Filter.FilterType.LIKE)) {
                    Predicate booleanEqualPredicate = predicateUtil.getBooleanEqualPredicate(path, filter.getValue());
                    return filter.getNegation() ? booleanEqualPredicate.not() : booleanEqualPredicate;
                }
                return predicateUtil.getFalsePredicate();
            }

            private Predicate getDatePredicate(PredicateUtil predicateUtil, Filter filter, Path path) {
                Predicate datePredicate = null;
                if (filter.getType().equals(BETWEEN)) {
                    datePredicate = predicateUtil.getBetweenDatesPredicate(path, filter.getValues());
                }
                return datePredicate;
            }

            /**
             * Creates a predicate by applying a custom {@link Filter} to a non-boolean expression.
             * If the filter type is unsupported, the returned predicate will always evaluate as false.
             * Predicates {@link ro.teamnet.bootstrap.extend.Filter.FilterType#STARTS_WITH} and
             * {@link ro.teamnet.bootstrap.extend.Filter.FilterType#ENDS_WITH} are currently supported for String types
             * only.
             *
             * @param predicateUtil utility class for building predicates
             * @param filter a custom filter
             * @param path the boolean expression to evaluate
             * @return a predicate that tests the expression against a custom filter
             */
            private Predicate getNonBooleanPredicate(PredicateUtil predicateUtil, Filter filter, Path path) {
                Predicate predicate = predicateUtil.getFalsePredicate();
                switch (filter.getType()) {
                    case LIKE:
                        if (!isString(path)) {
                            predicate = predicateUtil.getEqualPredicate(path, filter.getValue(), filter.getCaseSensitive());
                            break;
                        }
                    case STARTS_WITH:
                    case ENDS_WITH:
                        if (isString(path)) {
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
                            throw new RuntimeException(e);
                        }
                        break;
                }
                return filter.getNegation() ? predicate.not() : predicate;
            }
        };
    }

    private static boolean isString(Path path) {
        return ClassUtils.isAssignable(String.class, path.getJavaType());
    }

    /**
     * Utility class for generating predicates based on {@link  javax.persistence.criteria.CriteriaBuilder} methods.
     *
     * @author Mihaela Petre
     * @since 02.10.2015
     */
    private static class PredicateUtil {

        private CriteriaBuilder criteriaBuilder;

        /**
         * Constructor for {@link PredicateUtil}
         *
         * @param criteriaBuilder Criteria Builder
         */
        public PredicateUtil(CriteriaBuilder criteriaBuilder) {
            this.criteriaBuilder = criteriaBuilder;
        }

        @SuppressWarnings("UnusedDeclaration")
        public CriteriaBuilder getCriteriaBuilder() {
            return criteriaBuilder;
        }

        @SuppressWarnings("UnusedDeclaration")
        public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
            this.criteriaBuilder = criteriaBuilder;
        }

        /**
         * If case sensitive flag is on and given path is of type String, builds an upper case expression based on path.
         *
         * @param path
         * @param caseSensitive
         * @return upper case expression based on path, if path type is String and caseSensitive flag is set to true
         */
        private Expression getUpperCaseExpression(Path path, Boolean caseSensitive) {
            return path.getJavaType() != String.class || caseSensitive ? path : criteriaBuilder.upper(path);
        }

        /**
         * If case sensitive flag is on, converts value to upper case.
         *
         * @param value
         * @param caseSensitive
         * @return upper-cased value, if caseSensitive is true
         */
        private String getUpperCaseValue(String value, Boolean caseSensitive) {
            return caseSensitive ? value : value.toUpperCase();
        }

        /**
         * If case sensitive flag is on, converts all values to upper case.
         *
         * @param values
         * @param caseSensitive
         * @return upper-cased values, if caseSensitive is true
         */
        private Collection<String> getUpperCaseValues(Collection<String> values, Boolean caseSensitive) {
            if (!caseSensitive) {
                return values;
            }
            Collection<String> upperCasedValues = new ArrayList<>();
            for (String value : values) {
                upperCasedValues.add(value.toUpperCase());
            }
            return upperCasedValues;
        }

        /**
         * Generates a predicate for testing whether the path argument satisfies the given pattern.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#like} method.
         *
         * @param path
         * @param pattern       a pattern for the 'LIKE' operator.
         * @param caseSensitive
         * @return predicate
         */
        protected Predicate getLikePredicate(Path path, String pattern, Boolean caseSensitive) {
            return criteriaBuilder.like(getUpperCaseExpression(path, caseSensitive), getUpperCaseValue(pattern, caseSensitive));
        }

        /**
         * Generate a predicate for testing whether the path argument is equal with the value argument.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#equal} method.
         *
         * @param path
         * @param value         String
         * @param caseSensitive
         * @return predicate
         */
        protected Predicate getEqualPredicate(Path path, String value, Boolean caseSensitive) {
            return criteriaBuilder.equal(getUpperCaseExpression(path, caseSensitive), getUpperCaseValue(value, caseSensitive));
        }

        /**
         * Generate a predicate testing whether the path argument has the same boolean value as the value argument.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#equal} method.
         *
         * @param path
         * @param value a string representation of a boolean value
         * @return predicate
         */
        protected Predicate getBooleanEqualPredicate(Path path, String value) {
            return Boolean.valueOf(value) ? criteriaBuilder.isTrue(path) : criteriaBuilder.isFalse(path);
        }

        /**
         * Generates a predicate for testing whether  the path argument is a member of the collection.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#in} method.
         *
         * @param path
         * @param values        Collection of String values
         * @param caseSensitive
         * @return predicate
         */
        protected Predicate getInPredicate(Path path, Collection<String> values, Boolean caseSensitive) {
            return criteriaBuilder.and(getUpperCaseExpression(path, caseSensitive).in(getUpperCaseValues(values, caseSensitive)));
        }

        /**
         * Generates a predicate for testing whether the path argument is less than the value argument.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#lessThan} method.
         *
         * @param path
         * @param value         String
         * @param caseSensitive
         * @return predicate
         */
        protected Predicate getLessThanPredicate(Path path, String value, Boolean caseSensitive) {
            return criteriaBuilder.lessThan(getUpperCaseExpression(path, caseSensitive), getUpperCaseValue(value, caseSensitive));
        }

        /**
         * Generates a predicate for testing whether the path argument is less than or equal to the value argument.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#lessThanOrEqualTo} method.
         *
         * @param path
         * @param value         String
         * @param caseSensitive
         * @return predicate
         */
        protected Predicate getLessThanOrEqualPredicate(Path path, String value, Boolean caseSensitive) {
            return criteriaBuilder.lessThanOrEqualTo(getUpperCaseExpression(path, caseSensitive), getUpperCaseValue(value, caseSensitive));
        }

        /**
         * Generates a predicate for testing whether the path argument is greater than the value argument.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#greaterThan} method.
         *
         * @param path
         * @param value         String
         * @param caseSensitive
         * @return predicate
         */
        protected Predicate getGreaterThanPredicate(Path path, String value, Boolean caseSensitive) {
            return criteriaBuilder.greaterThan(getUpperCaseExpression(path, caseSensitive), getUpperCaseValue(value, caseSensitive));
        }

        /**
         * Generates a predicate for testing whether the path argument is greater than or equal to the value argument.
         * It uses {@link  javax.persistence.criteria.CriteriaBuilder#greaterThanOrEqualTo} method.
         *
         * @param path
         * @param value         String
         * @param caseSensitive
         * @return predicate
         */
        protected Predicate getGreaterThanOrEqualPredicate(Path path, String value, Boolean caseSensitive) {
            return criteriaBuilder.greaterThanOrEqualTo(getUpperCaseExpression(path, caseSensitive), getUpperCaseValue(value, caseSensitive));
        }

        /**
         * Generates a predicate for testing whether the path argument is between the values specified in values argument.
         * It uses  {@link  javax.persistence.criteria.CriteriaBuilder#between} method.
         *
         * @param path
         * @param values        List of String values. Values represent the margins interval.
         * @param caseSensitive
         * @return predicate
         * @throws InvalidNumberOfFiltersException if values argument is null or it has not exactly two String items.
         */
        protected Predicate getBetweenPredicate(Path path, Collection<String> values, Boolean caseSensitive) throws InvalidNumberOfFiltersException {
            String leftValue;              //margin left value
            String rightValue;              //margin right value
            if (values == null || values.size() != 2) {
                throw new InvalidNumberOfFiltersException("Invalid number of values");
            } else {
                leftValue = Iterables.get(values, 0);
                rightValue = Iterables.get(values, 1);
                return criteriaBuilder.between(path, getUpperCaseValue(leftValue, caseSensitive), getUpperCaseValue(rightValue, caseSensitive));
            }
        }

        /**
         * Creates a predicate that is always false. Used as initial value, in order to avoid null pointer exception.
         *
         * @return a predicate that will always evaluate as false
         */
        protected Predicate getFalsePredicate() {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(false));
        }

        /**
         * Creates a predicate testing whether a date-type path value is between the dates specified in values argument.
         * If one of the interval margins is null, a greater than/ lesser than predicate is created using the non-null margin.
         *
         * @param path   the path to test
         * @param values the date values provided as strings
         * @return a between predicate for date values
         */
        public Predicate getBetweenDatesPredicate(Path path, List<String> values) {
            if (values == null || values.size() != 2) {
                return null;
            }
            String startDateString = values.get(0);
            String endDateString = values.get(1);
            if (startDateString == null && endDateString == null) {
                return null;
            }
            if (endDateString == null) {
                return criteriaBuilder.greaterThanOrEqualTo(path, parseDate(startDateString, path.getJavaType()));
            }
            if (startDateString == null) {
                return criteriaBuilder.lessThanOrEqualTo(path, parseDate(endDateString, path.getJavaType()));
            }
            return criteriaBuilder.between(path, parseDate(startDateString, path.getJavaType()),
                    parseDate(endDateString, path.getJavaType()));
        }

        private Comparable parseDate(String dateString, Class type){
            DateTime dateTime = DateTime.parse(dateString);
            if (ClassUtils.isAssignable(DateTime.class, type)){
                return dateTime;
            }
            if (ClassUtils.isAssignable(LocalDate.class, type)){
                return dateTime.toLocalDate();
            }
            if (ClassUtils.isAssignable(Date.class, type)){
                return dateTime.toDate();
            }
            return dateString;
        }
    }

}