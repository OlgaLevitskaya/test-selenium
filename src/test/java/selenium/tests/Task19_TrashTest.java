package selenium.tests;

import org.junit.Test;

/**
 * Переделайте созданный в задании 13 сценарий для добавления товаров в корзину и удаления товаров из корзины,
 * чтобы он использовал многослойную архитектуру.
 * А именно, выделите вспомогательные классы для работы с главной страницей (откуда выбирается товар),
 * для работы со страницей товара (откуда происходит добавление товара в корзину),
 * со страницей корзины (откуда происходит удаление), и реализуйте сценарий, который не напрямую обращается
 * к операциям Selenium, а оперирует вышеперечисленными объектами-страницами.
 */
public class Task19_TrashTest extends TestBase {

    @Test
    public void testTask13() {
        app.addItemToTrash();
        app.deleteItemFromTrash();
    }
}
