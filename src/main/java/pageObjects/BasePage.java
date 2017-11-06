package pageObjects;

import exceptions.ScenarioLogicalException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;
import util.Config;
import util.Variables;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 17.10.2017.
 */
public class BasePage extends PageFactory {

    private static final Logger log = Logger.getLogger(BasePage.class);

    private static WebDriver driver;
    private static String path = "src/test/resources/drivers/";
    int timeout;
    Map<String,Object> variables;

    public BasePage() {
        PageFactory.initElements(driver, this);
        timeout=Integer.parseInt(Config.get("ozon.timeout"));
        variables = Variables.getInstance().getVariables();
    }

    public WebDriver getDriver(){
        return driver;
    }

    public static void initDriver() {
        log.info("Инициализация WebDriver");

        if (driver == null) {
            switch (Config.get("browser").toLowerCase(Locale.ENGLISH)) {
                case "chrome":
                    File chromeDriverFile = new File(path + "chromedriver.exe");
                    System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    String workingDir = System.getProperty("user.dir");
                    System.setProperty("webdriver.firefox.marionette", workingDir + "\\drv\\geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;
                default: throw new ScenarioLogicalException("Неподдерживаемый тип браузера=" + System.getProperty("browser"));
            }

            driver.manage().deleteAllCookies();
            driver.manage().timeouts().pageLoadTimeout(
                    Integer.parseInt(Config.get("ozon.timeout")), TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
    }

    public void visibilityOf(WebElement element, int seconds) {
        new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOf(element));
    }

    public void invisibilityOf(By locator, int seconds) {
        new WebDriverWait(driver, seconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void wait(int seconds) {
        log.info("Ожидание " + seconds + " секунд(ы)");

        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void closeDriver() {
        log.info("Закрытие WebDriver");

        if (driver != null) {
            driver.quit();
            driver=null;
        }
    }

    public void switchToNewWnd() {
        for(String winHandle : getDriver().getWindowHandles()){
            getDriver().switchTo().window(winHandle);
        }
    }


}
