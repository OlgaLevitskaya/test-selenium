import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * [x] Задание 13. Сделайте сценарий работы с корзиной
 * Сделайте сценарий для добавления товаров в корзину и удаления товаров из корзины.
 * 1) открыть главную страницу
 * 2) открыть первый товар из списка
 * 2) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
 * 3) подождать, пока счётчик товаров в корзине обновится
 * 4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
 * 5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
 * 6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
 */
public class Task13_TrashTest extends TestBase {

    @Test
    public void testTask13() {
        //добавляем 3 предмета в корзину
        addItemToTrash();
        //открыть корзину
        driver.findElement(By.cssSelector("#cart > a.link")).click();
        //удаляем предметы из корзины
        deleteItem();
    }

    private void deleteItem() {
        By locator = By.cssSelector(".dataTable tr:nth-child(2)");
        for (int i = 0; i < 3; i++) {
            WebElement tableCh2 = driver.findElement(locator);
            wait.until(visibilityOf(driver.findElement(By.cssSelector("button[name=remove_cart_item]")))).click();
            // ждём элемента таблицы
            wait.until(stalenessOf(tableCh2));
            if (i < 2) {
                // ждём загрузки следующей таблицы
                wait.until(visibilityOfElementLocated(locator));
            } else {
                //все товары  удалены - таблицы нет
                isElementsNotPresent(driver, By.cssSelector(".dataTable"));
            }
        }
    }

    /**
     * Добавить товар в корзину 3 раза
     */
    private void addItemToTrash() {
        for (int i = 0; i < 3; i++) {
            driver.get(getUrlToString(litecartBuilder()));

            //2) открыть первый товар из списка
            driver.findElements(By.cssSelector("#box-most-popular li")).get(0).click();

            int quantity = Integer.parseInt(driver.findElement(By.cssSelector("#cart .quantity")).getText());
            if (isElementsPresent(driver, By.cssSelector("select[name='options[Size]']"))) {
                Select select = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
                select.selectByVisibleText("Small");
            }
            //2) добавить его в корзину
            driver.findElement(By.cssSelector(".quantity button[name=add_cart_product]")).click();

            //3) подождать, пока счётчик товаров в корзине обновится
            wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart .quantity"), String.valueOf(quantity + 1)));
        }
    }
}
