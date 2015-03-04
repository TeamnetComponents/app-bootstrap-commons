package ro.teamnet.bootstrap.extend.filters;

import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.expression.LiteralExpression;
import org.hibernate.jpa.criteria.predicate.BetweenPredicate;
import org.hibernate.jpa.criteria.predicate.ComparisonPredicate;
import org.hibernate.jpa.criteria.predicate.LikePredicate;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.jpa.internal.metamodel.EntityTypeImpl;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import ro.teamnet.bootstrap.extend.AppPageRequest;
import ro.teamnet.bootstrap.extend.AppRepositoryImpl;
import ro.teamnet.bootstrap.extend.Filter;
import ro.teamnet.bootstrap.extend.Filters;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class contains common methods and properties used by grid filtering tests
 *
 * @author Mihaela Petre
 */
public class GridFiltersCommon {

    @Mock
    protected JpaEntityInformation jpaEntityInformation;


    @Mock
    protected EntityManager entityManager;


    @Mock
    protected EntityManagerFactoryImpl entityManagerFactory;

    @InjectMocks
    protected AppRepositoryImpl appRepositoryImpl;

    @Mock
    protected EntityTypeImpl entityType;


    @Mock
    protected Root root;


    @Mock
    protected CriteriaQuery query;

    protected CriteriaBuilderImpl cb;
    protected AppPageRequest appPageRequest;


    /**
     * Calls {@link org.mockito.MockitoAnnotations#initMocks} method to initialize mock objects and instantiate {@link ro.teamnet.bootstrap.extend.AppPageRequest} object
     *
     * @param filters represents a list of filters
     */
    public void initCommonMocks(Filters filters) {
        appPageRequest = new AppPageRequest(0, 20, filters);
        MockitoAnnotations.initMocks(this);
        cb = new CriteriaBuilderImpl(entityManagerFactory);
    }

    /**
     * @param predicate {@link javax.persistence.criteria.Predicate}
     * @param operator  {@link org.hibernate.jpa.criteria.predicate.ComparisonPredicate.ComparisonOperator}
     * @return list of comparison predicate expressions from a given predicate
     * @see org.hibernate.jpa.criteria.predicate.ComparisonPredicate
     */
    public static List<ComparisonPredicate> comparisonPredicates(Predicate predicate, String operator) {
        List<ComparisonPredicate> comparisonPredicates = new ArrayList<>();
        List<Expression<Boolean>> expressions = predicate.getExpressions();
        for (Expression expression : expressions) {
            if (expression instanceof ComparisonPredicate && ((((ComparisonPredicate) expression).getComparisonOperator()).toString().equals(operator))) {
                comparisonPredicates.add((ComparisonPredicate) expression);
            }
        }
        return comparisonPredicates;
    }

    /**
     * @param predicate {@link javax.persistence.criteria.Predicate}
     * @return list of between predicate expressions from a given predicate
     * @see org.hibernate.jpa.criteria.predicate.BetweenPredicate
     */
    public static List<BetweenPredicate> betweenPredicates(Predicate predicate) {
        List<BetweenPredicate> betweenPredicates = new ArrayList<>();
        List<Expression<Boolean>> expressions = predicate.getExpressions();
        for (Expression expression : expressions) {
            if (expression instanceof BetweenPredicate) {
                betweenPredicates.add((BetweenPredicate) expression);
            }
        }
        return betweenPredicates;
    }

    /**
     * @param predicate {@link javax.persistence.criteria.Predicate}
     * @return list of like predicate expressions from a given predicate
     * @see org.hibernate.jpa.criteria.predicate.LikePredicate
     */
    public static List<LikePredicate> likePredicates(Predicate predicate) {
        List<LikePredicate> likePredicates = new ArrayList<>();
        List<Expression<Boolean>> expressions = predicate.getExpressions();
        for (Expression expression : expressions) {
            if (expression instanceof LikePredicate) {
                likePredicates.add((LikePredicate) expression);
            }
        }
        return likePredicates;
    }


    /**
     * Calls  {@link ro.teamnet.bootstrap.extend.AppRepositoryImpl#createSpecification} and defines restrictions for return particular values when particular methods are called.
     *
     * @param root              {@link javax.persistence.criteria.Root}
     * @param appRepositoryImpl {@link ro.teamnet.bootstrap.extend.AppRepositoryImpl}
     * @param appPageRequest    {@link ro.teamnet.bootstrap.extend.AppPageRequest}
     * @param query             {@link javax.persistence.criteria.CriteriaQuery}
     * @param cb                {@link javax.persistence.criteria.CriteriaBuilder}
     * @return a specification
     */
    public static Predicate callToPredicate(Root root, AppRepositoryImpl appRepositoryImpl,
                                            AppPageRequest appPageRequest, CriteriaQuery query, CriteriaBuilder cb) {
        Path filterPath = mock(Path.class);
        when(filterPath.getJavaType()).thenReturn(Object.class);
        when(root.<String>get(anyString())).thenReturn(filterPath);

        Specification specification = appRepositoryImpl.createSpecification(appPageRequest);

        return specification.toPredicate(root, query, cb);
    }


    /**
     * Method contains the assert statements used for testing the filters
     *
     * @param filterType   represents the type of the tested filter - {@link ro.teamnet.bootstrap.extend.Filter.FilterType}
     * @param cp           - Criteria Builder
     * @param rightOperand represents the value from filter compared to the search term.
     * @param searchTerm   represents the search term.
     * @param values       represents a list of two String values used for between filter.
     */
    public void assertsTest(Filter.FilterType filterType, ComparisonPredicate cp, LiteralExpression rightOperand, String searchTerm, List<String> values) {
        switch (filterType) {
            case EQUAL:
                Assert.assertEquals(rightOperand.getLiteral(), searchTerm);
                Assert.assertEquals(cp.getComparisonOperator(), ComparisonPredicate.ComparisonOperator.EQUAL);
                break;
            case LESS_THAN:
                Assert.assertThat(searchTerm, Matchers.lessThan(rightOperand.getLiteral().toString()));
                Assert.assertEquals(cp.getComparisonOperator(), ComparisonPredicate.ComparisonOperator.LESS_THAN);
                break;
            case LESS_THAN_OR_EQUAL:
                Assert.assertThat(searchTerm, Matchers.lessThanOrEqualTo(rightOperand.getLiteral().toString()));
                Assert.assertEquals(cp.getComparisonOperator(), ComparisonPredicate.ComparisonOperator.LESS_THAN_OR_EQUAL);
                break;
            case GREATER_THAN:
                Assert.assertThat(searchTerm, Matchers.greaterThan(rightOperand.getLiteral().toString()));
                Assert.assertEquals(cp.getComparisonOperator(), ComparisonPredicate.ComparisonOperator.GREATER_THAN);
                break;
            case GREATER_THAN_OR_EQUAL:
                Assert.assertThat(searchTerm, Matchers.greaterThanOrEqualTo(rightOperand.getLiteral().toString()));
                Assert.assertEquals(cp.getComparisonOperator(), ComparisonPredicate.ComparisonOperator.GREATER_THAN_OR_EQUAL);
                break;
            case IN:
                Assert.assertThat(values, IsIterableContainingInAnyOrder.containsInAnyOrder(searchTerm));
                break;
        }

    }
}
