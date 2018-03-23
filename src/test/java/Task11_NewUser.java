import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.security.SecureRandom;
import java.util.Random;

/**
 * [x] Задание 11. Сделайте сценарий регистрации пользователя
 * Сделайте сценарий для регистрации нового пользователя в учебном приложении litecart (не в админке, а в клиентской
 * части магазина).
 * Сценарий должен состоять из следующих частей:
 * 1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало
 * с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария),
 * 2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
 * 3) повторный вход в только что созданную учётную запись,
 * 4) и ещё раз выход.
 * В качестве страны выбирайте United States, штат произвольный. При этом формат индекса -- пять цифр.
 */
public class Task11_NewUser extends TestBase {
    private static final String firstName = "FirstName";
    private static final String lastName = "LastName";
    public static final String address = "Address 1-66";
    public static final String postcode = "12345";
    public static final String city = "City";
    public static final String country = "United States";
    public static final String phone = "950011111";
    public static final String password = "password";

    @Before
    public void setUp() {
        driver.get(getUrlToString(litecartBuilder()));
    }

    @Test
    public void testTask11() throws InterruptedException {
        //1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало
        //с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария),
        driver.findElement(By.cssSelector("#box-account-login tr:nth-child(5) a")).click();
        WebElement createAccount = driver.findElement(By.cssSelector("#create-account"));
        setField(createAccount, "firstname", firstName);
        setField(createAccount, "lastname", lastName);
        setField(createAccount, "address1", address);
        setField(createAccount, "postcode", postcode);
        setField(createAccount, "city", city);
        WebElement selectElem = createAccount.findElement(By.tagName("select"));
        Select select = new Select(selectElem);
        select.selectByVisibleText(country);
        String email = createRandomEmail(15, "qwerty");
        setField(createAccount, "email", email);
        setField(createAccount, "phone", phone);
        setField(createAccount, "password", password);
        setField(createAccount, "confirmed_password", password);
        createAccount.findElement(By.cssSelector("button[name=create_account]")).click();
        logout();

        //3) повторный вход в только что созданную учётную запись
        WebElement login = driver.findElement(By.cssSelector("#box-account-login"));
        setField(login, "email", email);
        setField(login, "password", password);
        login.findElement(By.cssSelector("button[name=login]")).click();

        logout();
    }

    //2) выход (logout)
    private void logout() {
        driver.findElement(By.cssSelector("#box-account li:nth-child(4) a")).click();
    }

    private void setField(WebElement createAccount, String field, String city) {
        createAccount.findElement(By.cssSelector("input[name=" + field + "]")).sendKeys(city);
    }

    public String createRandomEmail(int codeLength, String id) {
        char[] chars = id.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString() + "@mail.ru";
        System.out.println("Email: " + output);
        return output;
    }
}
