import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.stqa.selenium.factory.WebDriverPool;

/**
 * Пример использования фабрики веб драйвера
 * https://github.com/barancev/webdriver-factory
 */
public class WebDriverFactoryTest {
    //The simplest use case
    @Test
    public void testOne() {
        // create a new managed instance
        WebDriver driver = WebDriverPool.DEFAULT.getDriver(new FirefoxOptions());
        // do something with the driver
        driver.get("http://seleniumhq.org/");
        // destroy the instance (calls driver.quit() implicitly)
        // WebDriverPool.DEFAULT.dismissDriver(driver);
    }


    @Test
    public void testSomethingElse() {
        WebDriver driver = WebDriverPool.DEFAULT.getDriver(new ChromeOptions());
        // do something with the driver
        driver.get("http://seleniumhq.org/");
    }

    @AfterClass
    public static void stopAllDrivers() {
        WebDriverPool.DEFAULT.dismissAll();
    }
}
