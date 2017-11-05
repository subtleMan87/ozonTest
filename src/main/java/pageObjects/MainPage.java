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

    @FindBy(xpath = "//div[text()='Выйти']")
    public WebElement logout;

//    @FindBy(xpath = "//div[@itemprop='itemListElement']")
    @FindBy(xpath = "//div[@itemprop='itemListElement']//div[text()=' В корзину ']")
//    @FindBy(xpath = "//div[text()='В корзину']")
    public List<WebElement> products;

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
        wait(1);
        passwordField.sendKeys(pass);
        enter.click();
        wait(5);
    }

    public void search(String name) {
        searchText.sendKeys(name);
        startSearch.click();
    }

    public void addEvenProductsInBasket() {
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        String productNames="";
        wait(1);
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
//            getDriver().findElements(By.xpath("//div[text()=' В корзину ']"))
            WebDriverWait wait = new WebDriverWait(getDriver(), 0);
            WebElement addToCart;
//            addToCart = product.findElement(By.xpath("//div[contains(@class,'bFlatButton mTitle mAddToCart js_add')]"));
//            addToCart = product.findElement(By.xpath("//div[text()=' В корзину ']"));
            if (product.isDisplayed()) {
                try {
//                    wait.until(ExpectedConditions.elementToBeClickable(product));
                    je.executeScript("arguments[0].scrollIntoView(true);",product);
                    je.executeScript("scrollBy(0,-200);");
                    productNames += product.findElement(By.xpath(".//../../..//a/div[@class='eOneTile_ItemName']")).getText() + ";";
                    product.click();
                    System.out.println(i + "ok");
                } catch (TimeoutException e) {
                    System.out.println(i + "not clickable");
                }
//
            } else {
                System.out.println(i + "not disp");
            }


//            product.findElement(By.xpath("//div[@class,'bFlatButton mTitle mAddToCart js_add']")).click();
            if (i == 2) {
//                break;
            }
        }

        variables.put("Товары в корзине",productNames);
    }

}

