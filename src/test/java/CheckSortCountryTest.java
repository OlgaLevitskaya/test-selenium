import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * [x] Задание 9. Проверить сортировку стран и геозон в админке
 * Сделайте сценарии, которые проверяют сортировку стран и геозон (штатов) в учебном приложении litecart.
 * <p>
 * 1) на странице http://localhost/litecart/admin/?app=countries&doc=countries
 * а) проверить, что страны расположены в алфавитном порядке
 * б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить,
 * что зоны расположены в алфавитном порядке
 * <p>
 * 2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
 * зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
 */
public class CheckSortCountryTest extends TestBase {

    @Before
    public void loadUrl() {
        loginAdmin();
    }

    @Test
    public void testCheckSortCountry() {
        URIBuilder uriBuilder = litecartBuilder()
                .addParameter("app", "countries")
                .addParameter("doc", "countries");
        driver.get(getUrlToString(uriBuilder));
        List<String> countryList = new ArrayList<>();

        List<WebElement> rows = getRows();
        for (int i = 0; i < rows.size(); i++) {
            //сохраняем название страны
            countryList.add(rows.get(i).findElement(By.cssSelector("td:nth-child(5)")).getText());

            //б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить,
            //что зоны расположены в алфавитном порядке
            int zone = Integer.parseInt(rows.get(i).findElement(By.cssSelector("td:nth-child(6)")).getText());
            if (zone != 0) {
                checkSortedZone(rows.get(i));
                rows = getRows();
            }
        }
        //а) проверить, что страны расположены в алфавитном порядке
        checkSorted(countryList);


        //2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
        // зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
        uriBuilder = litecartBuilder()
                .addParameter("app", "geo_zones")
                .addParameter("doc", "geo_zones");
        driver.get(getUrlToString(uriBuilder));
        rows = getRows();
        List<String> zoneList = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            //клик на страну
            rows.get(i).findElement(By.cssSelector("td:nth-child(3) a")).click();

            List<WebElement> zList = driver.findElements(By.cssSelector("#table-zones td:nth-child(3)"));
            for (WebElement zone : zList) {
                zoneList.add(zone.findElement(By.cssSelector("[selected=selected]")).getText());
            }
            checkSorted(zoneList);
            zoneList.clear();

            //Переход обратно
            driver.findElement(By.cssSelector(".button-set button[name=cancel]")).click();
            rows = getRows();
        }
    }

    private List<WebElement> getRows() {
        return driver.findElements(By.cssSelector("tr.row"));
    }

    /**
     * Проверка сортировки зон
     *
     * @param row - строка из таблицы стран
     */
    private void checkSortedZone(WebElement row) {
        Objects.requireNonNull(row, "row = null");
        row.findElement(By.cssSelector("a")).click();

        List<WebElement> zoneNames = driver.findElements(By.cssSelector("#table-zones td:nth-child(3)>input[type=hidden]"));
        Assert.assertNotNull("Значения зон в таблице не надено!", zoneNames);

        List<String> zoneList = new ArrayList<>();
        for (WebElement element : zoneNames) {
            String attribute = element.getAttribute("defaultValue");
            zoneList.add(attribute);
        }
        checkSorted(zoneList);
        //Переход обратно
        driver.findElement(By.cssSelector(".button-set button[name=cancel]")).click();
    }

    /**
     * Проверка сортировки списка по алфавиту
     *
     * @param expectedList
     */
    private void checkSorted(List<String> expectedList) {
        List<String> sortedZone = expectedList.stream().sorted().collect(Collectors.toList());
        System.out.println("Проверка сортировки списка" + expectedList.toString());
        Assert.assertTrue("Список  не отсортирован по алфавиту!!!\n" + expectedList.toString(), expectedList.equals(sortedZone));
    }
}
