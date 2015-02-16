package ro.teamnet.bootstrap.extend;

import com.google.common.collect.Iterables;
import ro.teamnet.bootstrap.exception.InvalidNumberOfFiltersException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class for generating predicates based on {@link  javax.persistence.criteria.CriteriaBuilder} methods.
 *
 * @author Mihaela Petre
 * @since 02.10.2015
 */
public class PredicateUtil {

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
     * Generates a negation of the given predicate.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#not} method.
     *
     * @param predicate predicate
     * @return predicate negation
     */
    Predicate applyNot(Predicate predicate) {
        return criteriaBuilder.not(predicate);
    }

}
