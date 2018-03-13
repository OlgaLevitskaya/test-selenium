import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Запуск FirefoxDriver - новая схема ver 49 +
 *
 */
public class FirefoxDriverTest {
    public static WebDriver firefoxDriver;

    @BeforeClass
    public static void beforeClass() {
        // новая схема более явно (этот способ указания опций рекомендуется, начиная с версии 3.3):
        FirefoxOptions options = new FirefoxOptions().setLegacy(false);
        firefoxDriver = new FirefoxDriver(options);
        Assert.assertNotNull(firefoxDriver);
    }

    @AfterClass
    public static void afterClass() {
        if (firefoxDriver != null) {
            firefoxDriver.quit();
            firefoxDriver = null;
        }
    }

    @Test
    public void testFirefoxDriver() {
        firefoxDriver.get("https://yandex.ru");
    }
}
