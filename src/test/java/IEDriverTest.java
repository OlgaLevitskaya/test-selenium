import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Запуск InternetExplorerDriver
 */
public class IEDriverTest {
    public static WebDriver ieDriver;

    @BeforeClass
    public static void beforeClass() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        /* если нельзя менять политики безопасности */
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        /* если масштаб не 100% */
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        ieDriver = new InternetExplorerDriver(options);
    }

    @AfterClass
    public static void afterClass() {
        if (ieDriver != null) {
            ieDriver.quit();
            ieDriver = null;
        }
    }

    @Test
    public void testieDriver() {
        ieDriver.get("https://yandex.ru");
    }
}
