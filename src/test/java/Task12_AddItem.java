import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

/**
 * [x] Задание 12. Сделайте сценарий добавления товара
 * Сделайте сценарий для добавления нового товара (продукта) в учебном приложении litecart (в админке).
 * Для добавления товара нужно открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product", заполнить
 * поля с информацией о товаре и сохранить.
 * Достаточно заполнить только информацию на вкладках General, Information и Prices. Скидки (Campains) на вкладке Prices
 * можно не добавлять.
 * Переключение между вкладками происходит не мгновенно, поэтому после переключения можно сделать небольшую паузу
 * (о том, как делать более правильные ожидания, будет рассказано в следующих занятиях).
 * Картинку с изображением товара нужно уложить в репозиторий вместе с кодом. При этом указывать в коде полный
 * абсолютный путь к файлу плохо, на другой машине работать не будет. Надо средствами языка программирования
 * преобразовать относительный путь в абсолютный.
 * После сохранения товара нужно убедиться, что он появился в каталоге (в админке).
 * Клиентскую часть магазина можно не проверять.
 * <p>
 * если в поле есть маска -- надо перед вводом текста перейти в начало
 * dateField.sendKeys(Keys.HOME + "01.01.2001");
 */
public class Task12_AddItem extends TestBase {

    @Before
    public void setUp() {
        loginAdmin();
    }

    @Test
    public void testTask12() {
        openCatalog();
        int sizeRows = driver.findElements(By.cssSelector(".row")).size();

        //клик по кнопке Add New Product
        driver.findElement(By.cssSelector("#content > div > a:nth-child(2)")).click();

        //Заполнение General
        WebElement currentTab = driver.findElement(By.cssSelector("#tab-general"));
        currentTab.findElement(By.cssSelector("input[name=status")).click();
        setField(currentTab, "name[en]", "Rabbit");
        setField(currentTab, "code", "12345");
        //Categories
        // List<WebElement> categoriesList = currentTab.findElements(By.cssSelector("#tab-general > table > tbody > tr:nth-child(4) tr"));
        //  categoriesList.get(1).findElement(By.cssSelector("input[name='categories[]']")).click();

        //Gender
        List<WebElement> genderList = currentTab.findElements(By.cssSelector("#tab-general > table > tbody > tr:nth-child(7) tr"));
        genderList.get(3).findElement(By.cssSelector("input[name='product_groups[]']")).click();

        currentTab.findElement(By.cssSelector("input[name=quantity]")).clear();
        setField(currentTab, "quantity", Keys.END + "10");

        File file = new File("src/test/resources/rabbit.jpg");
        setField(currentTab, "new_images[]", file.getAbsolutePath());
        setField(currentTab, "date_valid_from", "23.03.2018");
        setField(currentTab, "date_valid_to", "10.12.2018");

        //Заполнение Information
        List<WebElement> tabList = driver.findElements(By.cssSelector(".index li"));
        tabList.get(1).findElement(By.cssSelector("a")).click();
        List<WebElement> table = driver.findElements(By.cssSelector("#tab-information tr>td"));
        Select select = new Select(table.get(0).findElement(By.cssSelector("select")));
        select.selectByVisibleText("ACME Corp.");
        currentTab = driver.findElement(By.cssSelector("#tab-information"));
        setField(currentTab, "keywords", "keywords");
        setField(currentTab, "short_description[en]", "Short Description");
        currentTab.findElement(By.cssSelector(".trumbowyg-editor"))
                .sendKeys(Keys.HOME + "Rabbits are small mammals in the family Leporidae of the order Lagomorpha " + Keys.ENTER
                        + "(along with the hare and the pika). Oryctolagus cuniculus includes the European rabbit " + Keys.ENTER
                        + "species and its descendants, the over 200 breeds of domestic rabbit." + Keys.ENTER);
        setField(currentTab, "head_title[en]", "Hesd Title");
        setField(currentTab, "meta_description[en]", "Meta Description");

        //Заполнение Prices
        tabList.get(3).findElement(By.cssSelector("a")).click();
        WebElement tablePrice = driver.findElement(By.cssSelector("#tab-prices > table:nth-child(2)"));
        tablePrice.findElement(By.cssSelector("input[name=purchase_price]")).clear();
        setField(tablePrice, "purchase_price", Keys.END + "10,99");
        select = new Select(tablePrice.findElement(By.cssSelector("select")));
        select.selectByVisibleText("Euros");
        List<WebElement> priceTr = driver.findElements(By.cssSelector("#tab-prices > table:nth-child(4) > tbody > tr"));
        setField(priceTr.get(1).findElement(By.cssSelector("td:nth-child(1)")), "prices[USD]", "5,5");
        setField(priceTr.get(2).findElement(By.cssSelector("td:nth-child(1)")), "prices[EUR]", "2,5");

        //save
        driver.findElement(By.cssSelector("button[name=save]")).click();

        //Проверка что запись добавилась по количеству строк
        openCatalog();
        Assert.assertTrue("Строка с новым товаром не добавилась",
                sizeRows + 1 == driver.findElements(By.cssSelector(".row")).size());
    }

    private void openCatalog() {
        List<WebElement> catalog = driver.findElements(By.cssSelector("#box-apps-menu li"));
        Assert.assertFalse("Каталог в админке не получен!", catalog.isEmpty());
        catalog.get(1).findElement(By.cssSelector("a")).click();
    }

    private void setField(WebElement createAccount, String field, String s) {
        createAccount.findElement(By.cssSelector("input[name='" + field + "']")).sendKeys(s);
    }
}
