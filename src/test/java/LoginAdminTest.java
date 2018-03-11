import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginAdminTest {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static final String ADMIN_URL = "http://localhost/litecart/public_html/admin";
    public static final String LOGIN = "admin";

    @BeforeClass
    public static void beforeClass() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testDriver() {
        driver.get(ADMIN_URL);
        driver.findElement(By.name("username")).sendKeys(LOGIN);
        driver.findElement(By.name("password")).sendKeys(LOGIN);
        driver.findElement(By.name("login")).click();
    }

    @AfterClass
    public static void afterClass() {
        driver.quit();
        driver = null;
    }
}
