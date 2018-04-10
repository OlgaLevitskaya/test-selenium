package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BoxProductBlock extends Page {
    public BoxProductBlock(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "box-product")
    WebElement boxProduct;

    public void selectSmallSize() {
        By selector = By.cssSelector("select[name='options[Size]']");
        if (isElementsPresent(driver, selector)) {
            Select select = new Select(driver.findElement(selector));
            select.selectByVisibleText("Small");
        }
    }
}
