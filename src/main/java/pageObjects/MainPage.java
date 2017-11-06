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
import java.util.stream.Collectors;

public class MainPage extends BasePage {

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

    @FindBy(xpath = "//div[contains(text(),'Выйти')]")
    public WebElement logout;

    @FindBy(xpath = "//div[@itemprop='itemListElement']//div[text()=' В корзину ']")
    public List<WebElement> products;

    @FindBy(xpath = "//div[contains(@class,'jsQuickPanelUserMenu')]")
    public WebElement userMenu;

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

    public void addEvenProductsToCart() {
        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        ArrayList<String> productNames = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            if ((i+1) % 2 == 0) {
                je.executeScript("arguments[0].scrollIntoView(true);", products.get(i));
                je.executeScript("scrollBy(0,-200);");
                productNames.add(products.get(i)
                        .findElement(By.xpath(".//../../..//a/div[@class='eOneTile_ItemName']")).getText());
                products.get(i).click();
            }
        }

        variables.put("Товары в корзине", productNames);
    }

    public void logout() {
        new Actions(getDriver()).moveToElement(userMenu).build().perform();
        visibilityOf(logout, timeout);
        logout.click();
        visibilityOf(myOzon, timeout);
    }

//    div class="bCartPage
    public void goToCart() {
        cart.click();
    }

}

