package ro.teamnet.bootstrap.extend.filters;

import org.hamcrest.Matchers;
import org.hibernate.jpa.criteria.expression.LiteralExpression;
import org.hibernate.jpa.criteria.predicate.BetweenPredicate;
import org.hibernate.jpa.criteria.predicate.ComparisonPredicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.bootstrap.extend.Filter;
import ro.teamnet.bootstrap.extend.Filters;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mihaela Petre
 *         Test class for filters
 */
public class GridFiltersTest {

    private GridFiltersCommon gridCommons;

    private Filters filters;
    private static final String SEARCH_TERM = "TEST";


    /**
     * Instantiate filter and mock objects
     *
     * @throws Exception
     */
    @Before
    public void initMocks() throws Exception {

        //initialize filter objects
        Filter filterEqual = new Filter("code", "TEST", Filter.FilterType.EQUAL);
        Filter filterIn = new Filter("code", "TEST", Filter.FilterType.IN);
        Filter filterLessThan = new Filter("code", "Wiki", Filter.FilterType.LESS_THAN);
        Filter filterLessThanOrEqual = new Filter("name", "Testing", Filter.FilterType.LESS_THAN_OR_EQUAL);
        Filter filterGreaterThan = new Filter("description", "ABC", Filter.FilterType.GREATER_THAN);
        Filter filterGreaterThanOrEqual = new Filter("description", "ABCT", Filter.FilterType.GREATER_THAN_OR_EQUAL);
        Filter filterGreaterThanOrEquatNot = new Filter("description", "ABCT", Filter.FilterType.GREATER_THAN_OR_EQUAL);
        filterGreaterThanOrEquatNot.setNegation(true);

        List<String> values = new ArrayList<>();
        values.add("A");
        values.add("Z");
        Filter filterBetween = new Filter("code", values, Filter.FilterType.BETWEEN);

        // set filter list
        filters = new Filters(Arrays.asList(filterEqual, filterLessThan, filterLessThanOrEqual, filterGreaterThan,
                filterGreaterThanOrEqual, filterGreaterThanOrEquatNot, filterIn, filterBetween));
        gridCommons = new GridFiltersCommon();
        gridCommons.initCommonMocks(filters);
    }

    /**
     * Test method for comparison predicate filters
     *
     * @throws Exception
     */
    @Test
    public void comparisonCreateSpecification() throws Exception {
        for (Filter filter : filters) {
            Predicate returnPredicate = GridFiltersCommon.callToPredicate(gridCommons.root,
                    gridCommons.appRepositoryImpl, gridCommons.appPageRequest, gridCommons.query, gridCommons.cb);
            List<ComparisonPredicate> comparisonPredicates = GridFiltersCommon.comparisonPredicates(returnPredicate,
                    filter.getType().toString());

            for (ComparisonPredicate cp : comparisonPredicates) {
                Expression rightOperand = cp.getRightHandOperand();
                if (rightOperand instanceof LiteralExpression) {
                    gridCommons.assertsTest(filter.getType(), cp, (LiteralExpression) rightOperand, SEARCH_TERM, filter.getValues());
                }
            }
            Assert.assertEquals(Predicate.BooleanOperator.AND, returnPredicate.getOperator());
        }
    }

    /**
     * Test method for between predicate filters
     * FilterType: BETWEEN
     *
     * @throws Exception
     */
    @Test
    public void betweenCreateSpecification() throws Exception {
        for (Filter filter : filters) {
            Predicate returnPredicate = GridFiltersCommon.callToPredicate(gridCommons.root, gridCommons.appRepositoryImpl, gridCommons.appPageRequest, gridCommons.query, gridCommons.cb);
            List<BetweenPredicate> betweenPredicates = GridFiltersCommon.betweenPredicates(returnPredicate);

            for (BetweenPredicate bp : betweenPredicates) {
                Expression leftOperand = bp.getLowerBound();
                Expression rightOperand = bp.getUpperBound();

                if (leftOperand instanceof LiteralExpression && rightOperand instanceof LiteralExpression) {
                    Assert.assertThat(SEARCH_TERM, Matchers.greaterThanOrEqualTo(((LiteralExpression) leftOperand).getLiteral().toString()));
                    Assert.assertThat(SEARCH_TERM, Matchers.lessThanOrEqualTo(((LiteralExpression) rightOperand).getLiteral().toString()));
                    Assert.assertEquals(bp.getOperator(), BetweenPredicate.BooleanOperator.AND);
                }
            }
            Assert.assertEquals(Predicate.BooleanOperator.AND, returnPredicate.getOperator());
        }
    }

}


