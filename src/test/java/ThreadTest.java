import org.junit.Test;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

/**
 * Пример параллельного запуска тестов
 * Пример кода из лекции: https://github.com/barancev/selenium-training/blob/master/java-example/src/test/java/ru/stqa/training/selenium/TestBase.java
 * WebDriverFactory: https://github.com/barancev/webdriver-factory
 */
public class ThreadTest extends TestBase {
    @Test
    public void myFirstTest() {
        driver.navigate().to("http://www.google.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("q")).sendKeys("webdriver");
    }

    @Test
    public void mySecondTest() {
        driver.navigate().to("http://www.google.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("q")).sendKeys("webdriver");
    }
}
