package ro.teamnet.bootstrap.extend;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import ro.teamnet.bootstrap.extend.filters.GridFiltersTest;
import ro.teamnet.bootstrap.extend.filters.GridLikeFilterTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mihaela Petre
 * @since 02.11.2015
 */

/**
 * Main class for running grid filtering tests
 */
public class FilteringTestsRunner {
    public static void main(String[] args) {

        Result resultOtherFilters = JUnitCore.runClasses(GridFiltersTest.class);
        Result resultLikeFilters = JUnitCore.runClasses(GridLikeFilterTest.class);

        List<Result> resultList = new ArrayList<>();
        resultList.add(resultLikeFilters);
        resultList.add(resultOtherFilters);

        for(Result result : resultList){
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(result.wasSuccessful());
        }

    }

}
