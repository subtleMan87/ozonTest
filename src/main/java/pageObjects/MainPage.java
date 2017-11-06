package pageObjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import util.Config;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends BasePage {

    private static final Logger log = Logger.getLogger(MainPage.class);

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
        log.info("Переход на главную страницу");

        getDriver().get(Config.get("ozon.url"));
        visibilityOf(cart, timeout);
    }

    public void goToLogin() {
        log.info("Переход на форму авторизации");

        new Actions(getDriver()).moveToElement(myOzon).build().perform();
        visibilityOf(entry, timeout);
        entry.click();
    }

    public void login(String login, String pass) {
        log.info("Производим логин в систему. Логин=" + login + "  Пароль=" + pass);

        loginField.sendKeys(login);
        passwordField.sendKeys(pass);
        wait(1);
        enter.click();
        invisibilityOf(By.xpath("//span[text()='Мой OZON']"),timeout);
    }

    public void search(String name) {
        log.info("Ищем товар=" + name);

        searchText.sendKeys(name);
        startSearch.click();
    }

    public void addEvenProductsToCart() {
        log.info("Добавление четных товаров в корзину=");

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

        Assert.assertFalse("Не обнаружено ни одного товара для добавления в корзину", productNames.isEmpty());
        variables.put("Товары в корзине", productNames);
    }

    public void logout() {
        log.info("Выход из системы");

        new Actions(getDriver()).moveToElement(userMenu).build().perform();
        visibilityOf(logout, timeout);
        logout.click();
        visibilityOf(myOzon, timeout);
    }

    public void goToCart() {
        log.info("Переход в корзину");

        cart.click();
    }

}

