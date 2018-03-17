import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * [x] Задание 7. Сделайте сценарий, проходящий по всем разделам админки
 * Сделайте сценарий, который выполняет следующие действия в учебном приложении litecart.
 * 1) входит в панель администратора http://localhost/litecart/admin
 * 2) прокликивает последовательно все пункты меню слева, включая вложенные пункты
 * 3) для каждой страницы проверяет наличие заголовка (то есть элемента с тегом h1)
 * Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
 */
public class BrowsingTabTest extends TestBase {
    private static final String appL = "ul#box-apps-menu li#app-"; //локатор для меню apps
    private static final String docsL = "ul.docs li"; //локатор для меню docs

    @Before
    public void loadAdmin() {
        loginAdmin();
        //ждем когда появляется меню - загрузка может быть долгой
        wait.until(presenceOfElementLocated(By.cssSelector("ul#box-apps-menu")));
    }

    @Test
    public void testBrowsingTabTest() {
        List<WebElement> menuList = driver.findElements(By.cssSelector(appL));
        for (int i = 1; i <= menuList.size(); i++) {
            //клик по основному разделу
            driver.findElement(By.cssSelector(appL + ":nth-child(" + i + ")")).click();
            List<WebElement> element = driver.findElements(By.cssSelector(docsL));
            if (element.size() > 0) {
                for (int j = 1; j <= element.size(); j++) {
                    //клики по вложенным пунктам
                    driver.findElement(By.cssSelector(docsL + ":nth-child(" + j + ")")).click();
                    //проверка что есть h1
                    driver.findElement(By.cssSelector("#content h1"));
                }
            }
        }
    }


}
