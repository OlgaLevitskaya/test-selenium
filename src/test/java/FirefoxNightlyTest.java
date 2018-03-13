import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;

/**
 * Запуск FirefoxDriver для старой версии Firefox Nightly
 * https://nightly.mozilla.org/
 * Firefox 49+
 */
public class FirefoxNightlyTest {
    public static WebDriver firefoxDriver;
    public static String PATH = "C:\\Program Files\\Firefox Nightly\\firefox.exe";

    @BeforeClass
    public static void beforeClass() {
        // новая схема более явно (этот способ указания опций рекомендуется, начиная с версии 3.3)
        FirefoxOptions options = new FirefoxOptions().setLegacy(false);
        options.setBinary(new FirefoxBinary(new File(PATH)));
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
