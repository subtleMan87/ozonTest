package pageObjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 05.11.2017.
 */
public class CartPage extends BasePage {

    private static final Logger log = Logger.getLogger(CartPage.class);

    @FindBy(xpath = "//div[contains(@class,'bCartItem')]")
    public List<WebElement> productsInCart;

    @FindBy(xpath = "//span[normalize-space(text()='Корзина пуста') and @class='jsInnerContentpage_title']")
    public WebElement labelOfEmptyCart;

    @FindBy(xpath = "//div[contains(@class,'jsRemoveAll')]")
    public WebElement removeAll;

    @FindBy(xpath = "//div[@class='eCartPage_head']")
    public WebElement cartElement;

    public CartPage () {
        super();
        visibilityOf(cartElement, timeout);
    }

    public void checkEmptyOfCart() {
        log.info("Проверка удаления товаров в корзине");

        Assert.assertTrue("Не обнаружили фразы='Корзина пуста'", labelOfEmptyCart.isDisplayed());
        Assert.assertTrue("В корзине имеются товары", productsInCart.isEmpty());
    }

    public void removeProductsInCart() {
        log.info("Удаление товаров в корзине");

        removeAll.click();
    }

    public void checkProducts() {
        log.info("Проверка добавленных товаров в корзине");

        //Часто после прогрузки страницы, корзина не полностью заполнена товарами, приходится подождать
        wait(15);
        ArrayList<String> productsInCart = getProductsInCart();
        Assert.assertFalse("В корзине отсуствуют товары", productsInCart.isEmpty());

        ArrayList<String> exceptedProducts = (ArrayList<String>) variables.get("Товары в корзине");
        for (String exceptedProduct : exceptedProducts) {
            boolean isFind=false;
            for (String productInCart : productsInCart) {
                if (exceptedProduct.equals(productInCart)) {
                    isFind=true;
                    break;
                }
            }
            Assert.assertTrue("Добавленный товар='" + exceptedProduct + "' отсутствует в корзине", isFind);
        }
    }

    private ArrayList<String> getProductsInCart() {
        log.info("Взятие наименований товаров из корзины");

        ArrayList<String> productNamesInCart = new ArrayList<>();

        productsInCart.forEach((p -> productNamesInCart.add(
                p.findElement(By.xpath(".//div[contains(@class,'eCartItem_name')]//span")).getText())));
        return productNamesInCart;
    }
}
