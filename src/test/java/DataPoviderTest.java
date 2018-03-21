import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Использование dataprovider
 * https://github.com/TNG/junit-dataprovider
 * Example:
 * https://github.com/TNG/junit-dataprovider/wiki/Features
 */
@RunWith(DataProviderRunner.class)
public class DataPoviderTest {

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {"abc", "abc"},
                {"cba", "abc"},
        };
    }

    @DataProvider
    public static Object[][] test() {
        return new Object[][]{
                {"123", "123"},
                {"321", "123"},
        };
    }

    @Test
    @UseDataProvider("data")
    public void testDataSort(final String input, final String expected) {
        Assert.assertEquals(expected, mySortMethod(input));
    }

    @Test
    @UseDataProvider("test")
    public void testTestSort(final String input, final String expected) {
        Assert.assertEquals(expected, mySortMethod(input));
    }

    @Test
    @DataProvider({"null", ""})
    public void testIsEmptyString2(String str) {
        boolean isEmpty = (str == null) ? true : str.isEmpty();
        Assert.assertTrue(isEmpty);
    }

    private Object mySortMethod(Object input) {
        return input;
    }

    @Test
    @DataProvider(value = {
            "               |  0",
            "a              |  1",
            "abc            |  3",
            "veryLongString | 14",
    }, splitBy = "\\|", trimValues = true)
    public void testStringLength2(String str, int expectedLength) {
        Assert.assertEquals(expectedLength, str.length());
    }

}
