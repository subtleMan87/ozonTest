package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Config;
import util.Variables;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends BasePage {

//    @FindBy(xpath = "//a[contains(@class,'mMulticart')]")
//    public WebElement cart;

    @FindBy(xpath = "//span[text()='Мой OZON']")
    public WebElement myOzon;

    @FindBy(xpath = "//div[contains(text(),'Вход')]")
    public WebElement entry;

    @FindBy(xpath = "//input[@name='login']")
    public WebElement loginField;

    @FindBy(xpath = "//input[@name='Password']")
    public WebElement passwordField;

    @FindBy(xpath = "//div[text()='Войти']")
    public WebElement enter;

    @FindBy(id = "SearchText")
    public WebElement searchText;

    @FindBy(xpath = "//div[@class='bFlatButton mSearchButton']")
    public WebElement startSearch;

    @FindBy(xpath = "//div[@class='mCart']")
    public WebElement cart;

    @FindBy(xpath = "//div[contains(@class,'jsRemoveAll')]")
    public WebElement removeAll;

//    @FindBy(xpath = "//div[text()='Выйти']")
    @FindBy(xpath = "//div[contains(text(),'Выйти')]")
    public WebElement logout;

    @FindBy(xpath = "//div[@itemprop='itemListElement']//div[text()=' В корзину ']")
    public List<WebElement> products;

    @FindBy(xpath = "//div[contains(@class,'bCartItem')]")
    public List<WebElement> productsInCart;

    @FindBy(xpath = "//div[contains(@class,'jsQuickPanelUserMenu')]")
    public WebElement userMenu;

    @FindBy(xpath = "//span[normalize-space(text()='Корзина пуста') and @class='jsInnerContentpage_title']")
    public WebElement labelOfEmptyCart;


//    bCartItem   jsChild_DOM_items_33369612
//
//    eCartItem_name

//    itemprop="itemListElement" getDriver().findElements(By.xpath("//div[@itemprop='itemListElement']"))
    //getDriver().findElements(By.xpath("//div[@itemprop='itemListElement']")).get(0).findElement(By.xpath("//div[contains(@class,'mAddToCart')]"))

    public MainPage() {
        super();
    }

    public void open() {
        getDriver().get(Config.get("ozon.url"));
        visibilityOf(cart, timeout);
    }

    public void goToLogin() {
        new Actions(getDriver()).moveToElement(myOzon).build().perform();
        visibilityOf(entry, timeout);
        entry.click();
    }

    public void login(String login, String pass) {
        loginField.sendKeys(login);
        passwordField.sendKeys(pass);
        wait(1);
        enter.click();
        wait(15);
    }

    public void search(String name) {
        searchText.sendKeys(name);
        startSearch.click();
    }

    public void addEvenProductsInBasket() {
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        ArrayList<String> productNames = new ArrayList<>();
        wait(1);
        for (WebElement product : products) {
            //TODO добавить проверку на нечетность
//            if (product.isDisplayed()) {
                je.executeScript("arguments[0].scrollIntoView(true);", product);
                je.executeScript("scrollBy(0,-200);");
                productNames.add(product
                        .findElement(By.xpath(".//../../..//a/div[@class='eOneTile_ItemName']")).getText());
                product.click();
//            }
        }

        variables.put("Товары в корзине", productNames);
    }

    public void goToCart() {
        cart.click();
        wait(5);

        ArrayList<String> productNamesInCart = new ArrayList<>();
        for (int i = 0; i < productsInCart.size(); i++) {
            productNamesInCart.add(productsInCart.get(i)
                    .findElement(By.xpath(".//div[contains(@class,'eCartItem_name')]//span")).getText());
        }

        removeAll.click();

        new Actions(getDriver()).moveToElement(userMenu).build().perform();
        visibilityOf(logout, timeout);
        logout.click();
    }

//    div class="bCartPage
    public void checkEmptyOfCart() {
        cart.click();
        Assert.assertTrue("Не обнаружили фразы='Корзина пуста'", labelOfEmptyCart.isDisplayed());
        Assert.assertTrue("В корзине имеются товары", productsInCart.isEmpty());
    }

}

