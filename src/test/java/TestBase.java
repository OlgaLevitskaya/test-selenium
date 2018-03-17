import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Пример параллельного запуска тестов
 * Пример кода из лекции: https://github.com/barancev/selenium-training/blob/master/java-example/src/test/java/ru/stqa/training/selenium/TestBase.java
 * WebDriverFactory: https://github.com/barancev/webdriver-factory
 */
public class TestBase {
    public static final String LITECART_URL = "http://localhost/litecart/public_html";
    public static final String ADMIN_URL = "http://localhost/litecart/public_html/admin";
    public static final String LOGIN = "admin";
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            return;
        }

        driver = new ChromeDriver();
        tlDriver.set(driver);
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    driver.quit();
                    driver = null;
                }));
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }

    /**
     * Логин в админку
     */
    public void loginAdmin() {
        driver.get(ADMIN_URL);
        driver.findElement(By.name("username")).sendKeys(LOGIN);
        driver.findElement(By.name("password")).sendKeys(LOGIN);
        driver.findElement(By.name("login")).click();
    }
}
