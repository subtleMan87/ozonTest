package steps_definition;

/**
 * Created by Ser on 15.12.2015.
 */

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.BasePage;
import util.Config;
import util.Variables;

import java.io.File;

public class SetupSteps {

    @Before
    public void init() {
        Config.getInstance();
        PropertyConfigurator.configure("src\\test\\resources\\config\\log4j.properties");
        BasePage.initDriver();
    }

    @After
    public void close() {
        BasePage.closeDriver();
    }
}
