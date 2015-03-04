package ro.teamnet.bootstrap.extend.filters;

import org.hibernate.jpa.criteria.expression.LiteralExpression;
import org.hibernate.jpa.criteria.predicate.LikePredicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ro.teamnet.bootstrap.extend.Filter;
import ro.teamnet.bootstrap.extend.Filters;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Parameterized Test Class for testing grid filtering using like filters (standard like - %filter%, starts_with - filter% and ends_with - %filter)
 *
 * @author Mihaela Petre
 * @since 02.11.2015
 */
@RunWith(Parameterized.class)
public class GridLikeFilterTest {

    private final Filter fInput;
    private boolean fExpected = Boolean.TRUE;


    private static Filter filterLike;
    private static Filter filterStartsWith;
    private static Filter filterEndsWith;


    private GridFiltersCommon gridCommons;

    /**
     * @return list of filters and expected values used as parameters for running Parameterized class {@link ro.teamnet.bootstrap.extend.filters.GridLikeFilterTest}
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        filterLike = new Filter("code", "TEST", Filter.FilterType.LIKE);
        filterStartsWith = new Filter("code", "TEST", Filter.FilterType.STARTS_WITH);
        filterEndsWith = new Filter("code", "TEST", Filter.FilterType.ENDS_WITH);

        return Arrays.asList(new Object[][]{
                {filterLike, true}, {filterStartsWith, true}, {filterEndsWith, true}

        });
    }

    public GridLikeFilterTest(Filter input, boolean expected) {
        this.fInput = input;
        this.fExpected = expected;
    }

    /**
     * Method runs before the  testLikeCreateSpecification  method
     *
     * @throws Exception
     */
    @Before
    public void initMocks() throws Exception {
        Filters filters = new Filters(Arrays.asList(filterLike, filterStartsWith, filterEndsWith));
        gridCommons = new GridFiltersCommon();
        gridCommons.initCommonMocks(filters);

    }

    /**
     * Test method for like, starts_with and ends_with filters.
     */
    @Test
    public void testLikeCreateSpecification() {
        Assert.assertTrue(likeCreateSpecification(fInput) == fExpected);
    }

    /**
     * Generates a boolean value for testing whether the filter argument satisfies the pattern generated using patternBuilder method based on filter type.
     *
     * @param filter represents value used for filtering
     * @return boolean value.
     */
    public boolean likeCreateSpecification(Filter filter) {
        Predicate returnPredicate = GridFiltersCommon.callToPredicate(gridCommons.root, gridCommons.appRepositoryImpl, gridCommons.appPageRequest, gridCommons.query, gridCommons.cb);
        List<LikePredicate> likePredicates = GridFiltersCommon.likePredicates(returnPredicate);

        for (LikePredicate lp : likePredicates) {
            String pattern = "";
            if (lp.getPattern() instanceof LiteralExpression) {
                pattern = ((LiteralExpression) lp.getPattern()).getLiteral().toString();
            }
            fExpected = ((Filter.filterPatternBuilder(filter.getValue(), filter.getType())).matches(pattern) && Predicate.BooleanOperator.AND == returnPredicate.getOperator());
        }
        return fExpected;
    }

}



