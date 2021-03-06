import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
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
 * <p>
 * Секретный интерфес Locatable
 * (Locatable element).getCoordinates().inView()
 * можно проскролировать элемент в видимую часть окна
 */
public class Task10_ColorSizeElementsTest {

    @AfterClass
    public static void stopAllDrivers() {
        WebDriverPool.DEFAULT.dismissAll();
    }

    @Test
    public void testFirefox() {
        commonTest(WebDriverPool.DEFAULT.getDriver(new FirefoxOptions()));
    }

    @Test
    public void testChrome() {
        commonTest(WebDriverPool.DEFAULT.getDriver(new ChromeOptions()));
    }

    @Test
    public void testIE() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        /* если нельзя менять политики безопасности */
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        /* если масштаб не 100% */
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        commonTest(WebDriverPool.DEFAULT.getDriver(new InternetExplorerOptions(options)));
    }

    private void commonTest(WebDriver driver) {
        driver.get("http://localhost/litecart/public_html");

        //выбрать первый товар в блоке Campaigns
        List<WebElement> elements = driver.findElements(By.cssSelector("#box-campaigns li"));
        Assert.assertFalse("Нет товара в блоке Campaigns", elements.isEmpty());
        WebElement firstItem = elements.get(0);

        //сохраняем название товара
        String name = firstItem.findElement(By.cssSelector(".name")).getText();

        //сохраняем цены (обычная и акционная)
        WebElement price_wrapper = firstItem.findElement(By.cssSelector(getSelectorPriceWrapper()));
        String regular_price_text = price_wrapper.findElement(By.cssSelector(getSelectorRegularPrice())).getText();
        String campaign_price_text = price_wrapper.findElement(By.cssSelector(getSelectorCampaignPrice())).getText();

        //в) обычная цена зачёркнутая и серая
        checkRegularPrice(price_wrapper);

        //г) акционная жирная и красная
        checkCampaignPrice(price_wrapper);

        //д) акционная цена крупнее, чем обычная
        checkSizePrice(price_wrapper);

        //ПЕРЕХОД НА СТРАНИЦУ ТОВАРА
        firstItem.findElement(By.cssSelector("a")).click();

        //а) на главной странице и на странице товара совпадает текст названия товара
        Assert.assertEquals("На главной странице и на странице товара не совпадает текст названия товара!!!",
                driver.findElement(By.cssSelector("#box-product .title")).getText(), name);

        //б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        price_wrapper = driver.findElement(By.cssSelector(getSelectorPriceWrapper()));
        Assert.assertEquals("На главной странице и на странице товара не совпадают обычные цены!",
                price_wrapper.findElement(By.cssSelector(getSelectorRegularPrice())).getText(), regular_price_text);
        Assert.assertEquals("На главной странице и на странице товара не совпадают акционные цены!",
                price_wrapper.findElement(By.cssSelector(getSelectorCampaignPrice())).getText(), campaign_price_text);

        //г) акционная жирная и красная
        checkCampaignPrice(price_wrapper);

        //в) обычная цена зачёркнутая и серая
        checkRegularPrice(price_wrapper);

        //д) акционная цена крупнее, чем обычная
        checkSizePrice(price_wrapper);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    private String getSelectorRegularPrice() {
        return ".regular-price";
    }

    private String getSelectorPriceWrapper() {
        return ".price-wrapper";
    }

    private String getSelectorCampaignPrice() {
        return ".campaign-price";
    }

    //д) акционная цена крупнее, чем обычная
    private void checkSizePrice(WebElement priceWrapper) {
        Assert.assertTrue("Акционная цена Не крупнее, чем обычная",
                parseFontSize(priceWrapper.findElement(By.cssSelector(getSelectorRegularPrice())))
                        < parseFontSize(priceWrapper.findElement(By.cssSelector(" .campaign-price"))));
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

    /**
     * Преобразование цвета полученного из WebElement в Color
     * @param element с цветом
     * @return
     */
    private java.awt.Color parseColor(WebElement element) {
        return new Color(1, 1, 1, 1).fromString(element.getCssValue("color")).getColor();
    }

    //в) обычная цена зачёркнутая и серая (одинаковые значения для каналов R, G и B))
    private void checkRegularPrice(WebElement priceWrapper) {
        java.awt.Color color = parseColor(priceWrapper.findElement(By.cssSelector(getSelectorRegularPrice())));
        Assert.assertTrue("Обычная цена не серая", color.getBlue() == color.getRed()
                && color.getRed() == color.getGreen());
        priceWrapper.findElement(By.cssSelector("s.regular-price"));
    }

    //г) акционная жирная и красная (G и B имеют нулевые значения)
    private void checkCampaignPrice(WebElement priceWrapper) {
        java.awt.Color color = parseColor(priceWrapper.findElement(By.cssSelector(getSelectorCampaignPrice())));
        Assert.assertTrue("Акционная цена не красная", color.getGreen() == 0 && color.getBlue() == 0);
        priceWrapper.findElement(By.cssSelector("strong.campaign-price"));
    }
}
