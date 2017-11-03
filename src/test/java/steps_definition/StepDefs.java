package steps_definition;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import pageObjects.*;


public class StepDefs {

    @When("^Логинимся на сайт Ozon с логином (.*) и паролем (.*)$")
    public void auth(String login, String pass) throws Throwable {
        MainPage mainPage = new MainPage();
        mainPage.open();
        mainPage.goToLogin();
        mainPage.login(login,pass);

        //проверить аллюр
    }
}