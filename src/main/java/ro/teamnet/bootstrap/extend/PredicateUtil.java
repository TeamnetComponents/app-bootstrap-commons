package ro.teamnet.bootstrap.extend;

import com.google.common.collect.Iterables;
import ro.teamnet.bootstrap.exception.InvalidNumberOfFiltersException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Utility class for generating predicates based on {@link  javax.persistence.criteria.CriteriaBuilder} methods.
 * @author Mihaela Petre
 * @since 02.10.2015
 */
public class PredicateUtil {

    private CriteriaBuilder criteriaBuilder;
    private Root root;

    /**
     * Constructor for {@link PredicateUtil}
     * @param criteriaBuilder Criteria Builder
     * @param root Root
     */
    public PredicateUtil(CriteriaBuilder criteriaBuilder, Root root){
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    @SuppressWarnings("UnusedDeclaration")
    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Root getRoot() {
        return root;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRoot(Root root) {
        this.root = root;
    }

    /**
     * Generates a predicate for testing whether the property argument satisfies the given pattern.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#like} method.
     * @param property String
     * @param pattern String. It represents a regular expression.
     * @return predicate
     */
    protected Predicate getLikePredicate( String property, String pattern) {
        return criteriaBuilder.like(criteriaBuilder.upper(root.<String>get(property)), pattern);
    }

    /**
     * Generate a predicate for testing whether the property argument is equal with the value argument.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#equal} method.
     * @param property String
     * @param value String
     * @return predicate
     */
    protected Predicate getEqualPredicate(String property, String value) {
        return criteriaBuilder.equal(root.<String>get(property), value);
    }

    /**
     * Generates a predicate for testing whether  the property argument is a member of the collection.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#in} method.
     * @param property String
     * @param values Collection of String values
     * @return predicate
     */
    protected Predicate getInPredicate(String property, Collection<String> values) {
        return criteriaBuilder.and(root.<String>get(property).in(values));
    }

    /**
     * Generates a predicate for testing whether the property argument is less than the value argument.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#lessThan} method.
     * @param property String
     * @param value String
     * @return predicate
     */
    protected Predicate getLessThanPredicate(String property, String value) {
        return criteriaBuilder.lessThan(root.<String>get(property), value);
    }


    /**
     * Generates a predicate for testing whether the property argument is less than or equal to the value argument.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#lessThanOrEqualTo} method.
     * @param property String
     * @param value String
     * @return predicate
     */
    protected Predicate getLessThanOrEqualPredicate(String property, String value) {
        return criteriaBuilder.lessThanOrEqualTo(root.<String>get(property), value);
    }

    /**
     * Generates a predicate for testing whether the property argument is greater than the value argument.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#greaterThan} method.
     * @param property String
     * @param value String
     * @return predicate
     */
    protected Predicate getGreaterThanPredicate(String property, String value) {
        return criteriaBuilder.greaterThan(root.<String>get(property), value);
    }

    /**
     * Generates a predicate for testing whether the property argument is greater than or equal to the value argument.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#greaterThanOrEqualTo} method.
     * @param property String
     * @param value String
     * @return predicate
     */
    protected Predicate getGreaterThanOrEqualPredicate(String property, String value) {
        return criteriaBuilder.greaterThanOrEqualTo(root.<String>get(property),value);
    }

    /**
     * Generates a predicate for testing whether the property argument is between the values specified in values argument.
     * It uses  {@link  javax.persistence.criteria.CriteriaBuilder#between} method.
     * @param property String
     * @param values List of String values. Values represent the margins interval.
     * @return predicate
     * @throws InvalidNumberOfFiltersException if values argument is null or it has not exactly two String items.
     */
    protected Predicate getBetweenPredicate(String property, Collection<String> values) throws InvalidNumberOfFiltersException {
        String leftValue;              //margin left value
        String rightValue;              //margin right value
        if(values == null || values.size() != 2){
            throw new InvalidNumberOfFiltersException("Invalid number of values");
        }else{
            leftValue = Iterables.get(values,0);
            rightValue =Iterables.get(values,1);
            return criteriaBuilder.between(root.<String>get(property),leftValue,rightValue);
        }
    }

    /**
     * Generates a negation of the given predicate.
     * It uses {@link  javax.persistence.criteria.CriteriaBuilder#not} method.
     * @param predicate predicate
     * @return predicate negation
     */
    Predicate applyNot(Predicate predicate){
        return criteriaBuilder.not(predicate);
    }

}
