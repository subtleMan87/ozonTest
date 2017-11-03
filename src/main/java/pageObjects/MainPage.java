package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import util.Config;

import java.util.List;

public class MainPage extends BasePage {

    @FindBy(xpath = "//a[contains(@class,'mMulticart')]")
    public WebElement cart;

    @FindBy(xpath = "//span[text()='Мой OZON']")
    public WebElement myOzon;

    @FindBy(xpath = "//div[contains(text(),'Вход')]")
    public WebElement entry;

    @FindBy(xpath = "//input[name='login']")
    public WebElement loginField;

    @FindBy(xpath = "//input[name='Password']")
    public WebElement passwordField;

    @FindBy(xpath = "//div[text()='Войти']")
    public WebElement enter;

    public MainPage() {
        super();
    }

    public void open() {
        getDriver().get(Config.get("ozon.url"));
        visibilityOf(cart, timeout);
    }

    public void goToLogin() {
//        myOzon.click();
        new Actions(getDriver()).moveToElement(myOzon);
        wait(5);
        entry.click();
    }

    public void login(String login, String pass) {
        loginField.sendKeys(login);
        enter.click();
    }

}

