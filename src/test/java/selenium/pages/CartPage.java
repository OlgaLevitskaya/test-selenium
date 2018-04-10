package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CartPage extends Page {
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private By lChild2() {
        return By.cssSelector(".dataTable tr:nth-child(2)");
    }

    private By lDataTable() {
        return By.cssSelector(".dataTable");
    }

    @FindBy(name = "remove_cart_item")
    WebElement button_remove;

    public void deleteThreeItems() {
        for (int i = 0; i < 3; i++) {
            WebElement tableCh2 = driver.findElement(lChild2());
            removeCartItem();
            // ждём элемента таблицы
            wait.until(stalenessOf(tableCh2));
            if (i < 2) {
                // ждём загрузки следующей таблицы
                wait.until(visibilityOfElementLocated(lChild2()));
            } else {
                //все товары  удалены - таблицы нет
                isElementsNotPresent(driver, lDataTable());
            }
        }
    }

    public CartPage removeCartItem() {
        wait.until(visibilityOf(button_remove)).click();
        return this;
    }
}
