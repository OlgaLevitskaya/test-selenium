import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class BrowsersTest {
    public static WebDriver chromeDriver;
    public static WebDriver ieDriver;
    public static WebDriver firefoxDriver;
    public static String URL = "https://yandex.ru";


    @BeforeClass
    public static void beforeClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-fullscreen");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehaviour", "dismiss");
        chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println(((HasCapabilities) chromeDriver).getCapabilities());

//        DesiredCapabilities capsIE = new DesiredCapabilities();
//        caps.setCapability("unexpectedAlertBehaviour", "dismiss");
//        WebDriver driver = new InternetExplorerDriver(caps);
//        System.out.println(((HasCapabilities) driver).getCapabilities());

        firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void afterClass() {
        if (chromeDriver != null) {
            chromeDriver.quit();
            chromeDriver = null;
        }
        if (ieDriver != null) {
            ieDriver.quit();
            ieDriver = null;
        }
        if (firefoxDriver != null) {
            firefoxDriver.quit();
            firefoxDriver = null;
        }
    }

    @Test
    public void testChromeDriver() {
        chromeDriver.get(URL);
    }

    @Ignore
    @Test
    public void testIeDriver() {
        ieDriver.get(URL);
    }

    @Test
    public void testFirefoxDriver() {
        firefoxDriver.get(URL);
    }
}
