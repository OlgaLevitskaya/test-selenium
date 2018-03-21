import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import ru.stqa.selenium.factory.WebDriverPool;

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
        commonTest(WebDriverPool.DEFAULT.getDriver(new InternetExplorerOptions()));
    }

    private void commonTest(WebDriver driver) {
        driver.get("http://seleniumhq.org/");
        //driver.get("http://localhost/litecart/public_html");
    }
}
