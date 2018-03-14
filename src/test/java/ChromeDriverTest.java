import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Запуск Chrome Driver
 */
public class ChromeDriverTest extends TestBase{
    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeClass
    public static void beforeClass() {
        driver = new ChromeDriver();
        Assert.assertNotNull(driver);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testDriver() {
        driver.get("https://yandex.ru");
    }

    @AfterClass
    public static void afterClass() {
        driver.quit();
        driver = null;
    }
}
