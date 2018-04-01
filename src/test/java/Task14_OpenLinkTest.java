import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

/**
 * [x] Задание 14. Проверьте, что ссылки открываются в новом окне
 * Сделайте сценарий, который проверяет, что ссылки на странице редактирования страны открываются в новом окне.
 * Сценарий должен состоять из следующих частей:
 * 1) зайти в админку
 * 2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
 * 3) открыть на редактирование какую-нибудь страну или начать создание новой
 * 4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой -- они ведут на внешние страницы
 * и открываются в новом окне, именно это и нужно проверить.
 * Конечно, можно просто убедиться в том, что у ссылки есть атрибут target="_blank".
 * Но в этом упражнении требуется именно кликнуть по ссылке, чтобы она открылась в новом окне,
 * потом переключиться в новое окно, закрыть его, вернуться обратно, и повторить эти действия для всех таких ссылок.
 */
public class Task14_OpenLinkTest extends TestBase {

    @Before
    public void openUrl() throws URISyntaxException {
        URIBuilder uriBuilder = loginAdmin();
        uriBuilder
                .addParameter("app", "countries")
                .addParameter("doc", "countries")
                .build();
        driver.get(uriBuilder.toString());
    }

    @Test
    public void testTask14() {
        driver.findElement(By.cssSelector("#content a.button")).click();
        List<WebElement> links = driver.findElements(By.cssSelector(".fa-external-link"));
        //идентификатор текущего окна
        String curWindow = driver.getWindowHandle();
        for (int i = 0; i < links.size(); i++) {
            //Запоминаем все открытые окна
            Set<String> allWondows = driver.getWindowHandles();
            //кликаем на ссылку
            links.get(i).click();
            //ждем открытия нового окна
            String newWindow = wait.until(anyWindowsOtherThan(allWondows));
            System.out.println("New window = " + newWindow);
            //переключаемся в новое окно
            driver.switchTo().window(newWindow);
            //закрываем его
            driver.close();
            //переходим в исходное
            driver.switchTo().window(curWindow);

            links = driver.findElements(By.cssSelector(".fa-external-link"));
        }
    }

    /**
     * Получить окно отличное от тех которые в списке
     *
     * @param oldWindows
     * @return
     */
    public ExpectedCondition<String> anyWindowsOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}
