package selenium.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.pages.BoxProductBlock;
import selenium.pages.CartBlock;
import selenium.pages.CartPage;
import selenium.pages.MainPage;

public class Application {

    private WebDriver driver;
    private MainPage mainPage;
    private CartBlock cartBlock;
    private BoxProductBlock boxProductBlock;
    private CartPage cartPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        cartBlock = new CartBlock(driver);
        boxProductBlock = new BoxProductBlock(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    /**
     * Добавить товар в корзину 3 раза
     */
    public void addItemToTrash() {
        for (int i = 0; i < 3; i++) {
            mainPage.open().clickFirstProduct();
            int quantity = cartBlock.getCartQuantity();
            boxProductBlock.selectSmallSize();
            cartBlock.addInCart().waitCartQuantityChange(quantity);
        }
    }

    /**
     * Очистить корзину
     */
    public void deleteItemFromTrash() {
        cartBlock.openCart();
        cartPage.deleteThreeItems();
    }
}
