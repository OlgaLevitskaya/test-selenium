import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.List;

/**
 * [x] Задание 10. Проверить, что открывается правильная страница товара
 * Сделайте сценарий, который проверяет, что при клике на товар открывается правильная страница товара в учебном приложении litecart.
 * Более точно, нужно открыть главную страницу, выбрать первый товар в блоке Campaigns и проверить следующее:
 * а) на главной странице и на странице товара совпадает текст названия товара
 * б) на главной странице и на странице товара совпадают цены (обычная и акционная)
 * в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
 * г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
 * (цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
 * д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
 * Необходимо убедиться, что тесты работают в разных браузерах, желательно проверить во всех трёх ключевых браузерах (Chrome, Firefox, IE).
 * <p>
 * Цвета:
 * String color = link.getCssValue("color");
 * String color = link.getCssValue("border");
 * getTagName, getAttribute("id"), getAttribute("class")
 * В автотестах - проверка только, что нужным элементам назначены нужные классы
 * <p>
 * Размер и положение элемента:
 * Point location = link.getLocation();
 * Dimension size = link.getSize();
 * "два в одном" -- новый способ, который пока не все драйверы поддерживают
 * Rectangle rect = link.getRect();
 *
 * Секретный интерфес Locatable
 * (Locatable element).getCoordinates().inView()
 * можно проскролировать элемент в видимую часть окна
 */
public class Task10_ColorSizeElementsTest {
    private static final Color GRAY = new Color(119, 119, 119, 1);
    private static final Color RED = new Color(204, 0, 0, 1);
    private static final String BOLD_TEXT = "700";

    @AfterClass
    public static void stopAllDrivers() {
        WebDriverPool.DEFAULT.dismissAll();
    }

//    @Test
//    public void testFirefox() {
//        commonTest(WebDriverPool.DEFAULT.getDriver(new FirefoxOptions()));
//    }

    @Test
    public void testChrome() {
        commonTest(WebDriverPool.DEFAULT.getDriver(new ChromeOptions()));
    }

//    @Test
//    public void testIE() {
//        commonTest(WebDriverPool.DEFAULT.getDriver(new InternetExplorerOptions()));
//    }

    private void commonTest(WebDriver driver) {
        driver.get("http://localhost/litecart/");
        List<WebElement> elements = driver.findElements(By.cssSelector("#box-campaigns li"));
        Assert.assertNotNull("Нет товара в блоке Campaigns", elements);

        //выбрать первый товар в блоке Campaigns
        WebElement firstItem = elements.get(0);

        //сохраняем название товара
        String name = firstItem.findElement(By.cssSelector(".name")).getText();
        //сохраняем цены (обычная и акционная)
        WebElement regular_price = firstItem.findElement(By.cssSelector(".regular-price"));
        WebElement campaign_price = firstItem.findElement(By.cssSelector(".campaign-price"));

        //в) обычная цена зачёркнутая и серая
        Assert.assertEquals("Обычная цена не " + GRAY.asRgba(), GRAY.asRgba(), regular_price.getCssValue("color"));
        Assert.assertEquals("Обычная цена не перечеркнута!", "line-through", regular_price.getCssValue("text-decoration-line"));

        //г) акционная жирная и красная
        Assert.assertEquals("Акционная цена не " + RED.asRgba(), RED.asRgba(), campaign_price.getCssValue("color"));
        Assert.assertEquals("Акционная цена не жирная", BOLD_TEXT, campaign_price.getCssValue("font-weight"));

        //д) акционная цена крупнее, чем обычная
        Assert.assertTrue("Акционная цена Не крупнее, чем обычная", parseFontSize(regular_price) < parseFontSize(campaign_price));
    }

    /**
     * Преобразуем размер шрифта (like 18px) к типу float для сравнения
     *
     * @param element
     * @return
     */
    private float parseFontSize(WebElement element) {
        return Float.parseFloat(element.getCssValue("font-size").replace("px", ""));
    }
}
