import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * [x] Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров
 * Сделайте сценарий, проверяющий наличие стикеров у всех товаров в учебном приложении
 * litecart на главной странице. Стикеры -- это полоски в левом верхнем углу изображения товара,
 * на которых написано New или Sale или что-нибудь другое. Сценарий должен проверять, что у каждого
 * товара имеется ровно один стикер.
 * Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
 * Если возникают проблемы с выбором локаторов для поиска элементов -- обращайтесь в чат за помощью.
 */
public class CheckStickerNewTest extends TestBase {
    @Before
    public void loadUrl() {
        driver.get(LITECART_URL);
    }

    @Test
    public void testCheckSticker() {
        List<WebElement> webElements = driver.findElements(By.cssSelector(".products li"));
        Assert.assertNotNull("Товары не найдены!", webElements);
        for (WebElement element : webElements) {
            List<WebElement> stickers = element.findElements(By.cssSelector(".sticker"));
            Assert.assertNotNull("Стикер не найден!", stickers);
            System.out.println("Товар: " + element.findElement(By.className("name")).getText() +
                    ", количество стикеров = " + stickers.size());
            Assert.assertTrue("Количество стикеров = " + stickers.size() + " Ожидалось = 1", stickers.size() == 1);
        }
    }
}
