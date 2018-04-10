package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartBlock extends Page {
    public CartBlock(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#cart .quantity")
    private WebElement quantity;

    @FindBy(name = "add_cart_product")
    private WebElement addCart;

    @FindBy(id = "cart")
    private WebElement cart;

    public int getCartQuantity() {
        return Integer.parseInt(quantity.getText());
    }

    //подождать, пока счётчик товаров в корзине обновится
    public CartBlock waitCartQuantityChange(int startQuantity) {
        wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart .quantity"),
                String.valueOf(startQuantity + 1)));
        return this;
    }

    public CartBlock addInCart() {
        addCart.click();
        return this;
    }

    public void openCart() {
        cart.click();
    }
}
