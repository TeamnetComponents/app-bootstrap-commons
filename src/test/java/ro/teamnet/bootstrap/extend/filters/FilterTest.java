package ro.teamnet.bootstrap.extend.filters;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.bootstrap.extend.Filter;

/**
 * Test class for Filter.
 */
public class FilterTest {
    @Test
    public void whenFilterPropertyIsNullNoAttributesShouldBeRetrieved() {
        String[] attributes = new Filter(null, "val").getAttributes();
        Assert.assertEquals("Splitting a filter with a null property should produce no attributes!", 0, attributes.length);
    }

    @Test
    public void whenFilterPropertyIsEmptyNoAttributesShouldBeRetrieved() {
        String[] attributes = new Filter("", "val").getAttributes();
        Assert.assertEquals("Splitting a filter with an empty property should produce no attributes!", 0, attributes.length);
    }

    @Test
    public void whenSplittingSimpleFiltersAttributeArrayShouldOnlyContainTheFilterProperty() {
        String filterProperty = "simpleAttribute";
        String[] attributes = new Filter(filterProperty, "val").getAttributes();
        Assert.assertEquals("Splitting a simple filter should produce a single attribute!", 1, attributes.length);
        Assert.assertEquals("The attribute of a simple filter should match the property!", filterProperty, attributes[0]);
    }

    @Test
    public void whenSplittingCompoundFiltersAttributesShouldBeRetrievedInTheSameOrder() {
        String[] attributes = new Filter("attr0.attr1.attr2.attr3", "val").getAttributes();
        Assert.assertEquals("Splitting the compound filter produced the wrong number of attributes!", 4, attributes.length);
        for (int i = 0; i < attributes.length; i++) {
            String attribute = attributes[i];
            Assert.assertEquals("The attribute is incorrect!", "attr" + i, attribute);
        }
    }
}
