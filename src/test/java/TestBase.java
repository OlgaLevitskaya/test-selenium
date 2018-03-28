import org.apache.http.client.utils.URIBuilder;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * Пример параллельного запуска тестов
 * Пример кода из лекции: https://github.com/barancev/selenium-training/blob/master/java-example/src/test/java/ru/stqa/training/selenium/TestBase.java
 * WebDriverFactory: https://github.com/barancev/webdriver-factory
 */
public class TestBase {
    private URIBuilder builder = new URIBuilder();
    public long WAIT_ELEMENTS_TIME = 10;

    public URIBuilder adminBuilder() {
        return litecartBuilder().setPath("/admin");
    }

    public URIBuilder litecartBuilder() {
        return builder.setScheme("http").setHost("localhost/litecart/public_html");
    }

    public String getUrlToString(URIBuilder uriBuilder) {
        String url = null;
        try {
            url = uriBuilder.build().toString();
            System.out.println("Load:" + url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static final String LOGIN = "admin";
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, WAIT_ELEMENTS_TIME);
            driver.manage().timeouts().pageLoadTimeout(WAIT_ELEMENTS_TIME, TimeUnit.SECONDS);
            return;
        }

        driver = new ChromeDriver();
        tlDriver.set(driver);
        wait = new WebDriverWait(driver, WAIT_ELEMENTS_TIME);
        driver.manage().timeouts().pageLoadTimeout(WAIT_ELEMENTS_TIME, TimeUnit.SECONDS);

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
        loginAdmin(adminBuilder());
    }

    public void loginAdmin(URIBuilder uriBuilder) {
        driver.get(getUrlToString(uriBuilder));
        driver.findElement(By.name("username")).sendKeys(LOGIN);
        driver.findElement(By.name("password")).sendKeys(LOGIN);
        driver.findElement(By.name("login")).click();
    }

    /**
     * Проверка наличия (С ОЖИДАНИЕМ)
     *
     * @param driver
     * @param locator
     * @return
     */
    public boolean isElementsPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            return driver.findElements(locator).size() > 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(WAIT_ELEMENTS_TIME, TimeUnit.SECONDS);
        }
    }

    /**
     * Проверка отсутствия (БЕЗ ОЖИДАНИЯ)
     *
     * @param driver
     * @param locator
     * @return
     */
    public boolean isElementsNotPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return driver.findElements(locator).size() == 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(WAIT_ELEMENTS_TIME, TimeUnit.SECONDS);
        }
    }
    /**
     * ЯВНОЕ ОЖИДАНИЕ
     * WebDriverWait wait = new WebDriverWait(driver, 10);
     * WebElement element = wait.until(presenceOfElementLocated(By.name("q")));
     *  WebElement element2 = wait.until((WebDriver d) -> d.findElement(By.name("q")));
     */
}
