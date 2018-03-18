import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

/**
 * [x] Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров
 * Сделайте сценарий, проверяющий наличие стикеров у всех товаров в учебном приложении
 * litecart на главной странице. Стикеры -- это полоски в левом верхнем углу изображения товара,
 * на которых написано New или Sale или что-нибудь другое. Сценарий должен проверять, что у каждого
 * товара имеется ровно один стикер.
 * Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
 * Если возникают проблемы с выбором локаторов для поиска элементов -- обращайтесь в чат за помощью.
 */
public class CheckStickerTest extends TestBase {
    /**
     * Локаторы для групп контента
     */
    private static final String popularL = "box-most-popular"; //Most Popular
    private static final String campaignsL = "box-campaigns"; //Campaigns
    private static final String LastestL = "box-latest-products"; //Latest Products

    @Before
    public void loadUrl() {
        driver.get(LITECART_URL);
    }

    @Test
    public void testBrowsingTabTest() {
        checkSticker(popularL);
        checkSticker(campaignsL);
        checkSticker(LastestL);
    }

    /**
     * Проверка стикера
     *
     * @param locator
     */
    private void checkSticker(String locator) {
        Objects.requireNonNull("locator is null", locator);
        System.out.print("Check comtent in: " + locator + "\n");
        List<WebElement> webElements = driver.findElements(By.cssSelector("div#" + locator + " li"));
        for (WebElement element : webElements) {
            System.out.println(element.findElement(By.className("name")).getText());
            Assert.assertTrue(element.findElements(By.cssSelector("div.image-wrapper [title]")).size() == 1);
        }
    }
}
