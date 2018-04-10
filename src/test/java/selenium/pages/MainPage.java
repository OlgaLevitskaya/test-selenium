package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends Page {

    @FindBy(css = "#box-most-popular li")
    private List<WebElement> mostPopularProducts;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage open() {
        driver.get("http://localhost/litecart/public_html");
        return this;
    }

    public void clickFirstProduct() {
        mostPopularProducts.get(0).click();
    }
}
