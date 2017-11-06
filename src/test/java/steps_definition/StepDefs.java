package steps_definition;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import pageObjects.*;


public class StepDefs {

    @When("^Логинимся на сайт Ozon с логином (.*) и паролем (.*)$")
    public void auth(String login, String pass) {
        MainPage mainPage = new MainPage();
        mainPage.open();
        mainPage.goToLogin();
        mainPage.login(login,pass);
    }

    @Then("^Добавим все четные товары по поиску (.*) и проверим в корзине$")
    public void addEvenProducts(String name) {
        MainPage mainPage = new MainPage();
        mainPage.search(name);
        mainPage.addEvenProductsToCart();
    }

    @Then("^Проверим товары в корзине$")
    public void checkProductsInCart() {
        MainPage mainPage = new MainPage();
        mainPage.goToCart();

        CartPage cartPage = new CartPage();
        cartPage.checkProducts();
    }

    @Then("^Очистим корзину$")
    public void clearCart() {
        CartPage cartPage = new CartPage();
        cartPage.removeProductsInCart();
    }

    @Then("^Разлогинимся$")
    public void logout() {
        MainPage mainPage = new MainPage();
        mainPage.logout();
    }

    @Then("^Проверяем, что корзина пуста$")
    public void checkEmptyOfCart() {
        MainPage mainPage = new MainPage();
        mainPage.goToCart();

        CartPage cartPage = new CartPage();
        cartPage.checkEmptyOfCart();
        //TODO проверить аллюр
    }
}