import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.util.logging.Level;

/**
 * [x] Задание 17. Проверьте отсутствие сообщений в логе браузера
 * Сделайте сценарий, который проверяет, не появляются ли в логе браузера сообщения при открытии страниц в учебном
 * приложении, а именно -- страниц товаров в каталоге в административной панели.
 * Сценарий должен состоять из следующих частей:
 * 1) зайти в админк
 * 2) открыть каталог, категорию, которая содержит товары
 * (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
 * 3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения (любого уровня)
 */
public class Task15_LogBrowserTest {
    public static final String URL_ADMIN = "http:/localhost/litecart/public_html/admin";
    static ChromeDriver driver;

    @BeforeClass
    public static void beforeClass() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(caps);
        driver.get(URL_ADMIN);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.get(URL_ADMIN + "?app=catalog&doc=catalog&category_id=1");

    }

    @Test
    public void testTask15() {
        By selector = By.cssSelector(".dataTable .row a[title=Edit]");
        List<WebElement> elements = driver.findElements(selector);
        for (int i = 2; i < elements.size(); i++) {
            elements.get(i).click();
            LogEntries logEntries = driver.manage().logs().get("browser");
            logEntries.forEach(l -> System.out.println(l));
            Assert.assertTrue("Нет сообщения на странице", logEntries.filter(Level.INFO).size() > 0);
            driver.findElement(By.cssSelector("button[name=cancel]")).click();
            elements = driver.findElements(selector);
        }
    }


    @AfterClass
    public static void stop() {
        driver.quit();
        driver = null;
    }
}
