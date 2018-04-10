package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Page {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected int WAIT_ELEMENTS_TIME = 10;

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, WAIT_ELEMENTS_TIME);
        driver.manage().timeouts().pageLoadTimeout(WAIT_ELEMENTS_TIME, TimeUnit.SECONDS);
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
}
