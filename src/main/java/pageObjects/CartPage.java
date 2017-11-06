package pageObjects;

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

    @FindBy(xpath = "//div[contains(@class,'bCartItem')]")
    public List<WebElement> productsInCart;

    @FindBy(xpath = "//span[normalize-space(text()='Корзина пуста') and @class='jsInnerContentpage_title']")
    public WebElement labelOfEmptyCart;

    @FindBy(xpath = "//div[contains(@class,'jsRemoveAll')]")
    public WebElement removeAll;

    public void checkEmptyOfCart() {
        Assert.assertTrue("Не обнаружили фразы='Корзина пуста'", labelOfEmptyCart.isDisplayed());
        Assert.assertTrue("В корзине имеются товары", productsInCart.isEmpty());
    }

    public void removeProductsInCart() {
        removeAll.click();
    }

    public void checkProducts() {
        wait(15);
        ArrayList<String> productsInCart = getProductsInCart();
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

    public ArrayList<String> getProductsInCart() {
        ArrayList<String> productNamesInCart = new ArrayList<>();

        productsInCart.forEach((p -> productNamesInCart.add(p.findElement(By.xpath(".//div[contains(@class,'eCartItem_name')]//span")).getText())));
        return productNamesInCart;
    }
}
