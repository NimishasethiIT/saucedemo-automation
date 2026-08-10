package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static stepdefs.Hooks.driver;

public class StepDefs {


    @Given("I am on the Sauce Labs login page")
    public void iAmOnTheSauceLabsLoginPage() {

        driver.get("https://www.saucedemo.com/");
    }


    @Given("I log into the website as a regular user")
    public void regularUserLogin() {

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


    }


    @When("I add the {string} to my cart")
    public void addToCart(String productName) {
        String nameToUse = productName.trim().toLowerCase().replaceAll("\\s+", "-");
        driver.findElement(By.id(String.format("add-to-cart-%s", nameToUse))).click();

    }

    @Then("My cart shows {string} items added")
    public void itemsCountInCart(String numberOfItems) {
        String actual = driver.findElement(By.cssSelector("span.shopping_cart_badge")).getText();
        assertEquals(numberOfItems, actual);


    }


    @And("I remove the {string} from my cart")
    public void iRemoveTheFromMyCart(String productName) {
        String nametouse = productName.trim().toLowerCase().replaceAll("\\s+", "-");
        driver.findElement(By.id(String.format("remove-%s", nametouse))).click();


    }

    @And("Verify the {string} and {string}")
    public void verifyTheAnd(String product, String Expectedprice) {
        String actualPrice = driver.findElement(By.xpath("//div[text()='" + product + "']/following::div[@class='inventory_item_price'][1]")).getText();
        assertEquals(Expectedprice, actualPrice);


    }

    @When("I click the link of the  {string}")
    public void iClickTheLinkOfThe(String product) {
        driver.findElement(By.linkText(product)).click();

    }

    @And("Verify the product details page of the {string}")
    public void verifyTheProductDetailsPageOfThe(String Expectedproduct) {

        String actualName = driver.findElement(By.cssSelector("div.inventory_details_name.large_size")).getText();
        System.out.println(actualName);
        assertEquals(Expectedproduct, actualName);


    }

    @Then("My cart shows {string} items")
    public void myCartShowsItems(String Expectednumberofitems) {
        List<WebElement> cartBadges = driver.findElements(By.xpath("//a[@class='shopping_cart_link']/span[@class='shopping_cart_badge']"));
        String actualnumberofitems;
        if (cartBadges.isEmpty()) {
            actualnumberofitems = "0";

        } else {

            actualnumberofitems = cartBadges.get(0).getText();
            System.out.println(actualnumberofitems);
        }
        assertEquals(Expectednumberofitems, actualnumberofitems);


    }

    @Then("the button for the  {string} should display {string}")
    public void theButtonForTheShouldDisplay(String productname, String buttontext) {
        String nametouse = productname.trim().toLowerCase().replaceAll("\\s", "-");
        WebElement Remove = driver.findElement(By.id(String.format("remove-%s", nametouse)));
        String RemoveText = Remove.getText();
        assertEquals(RemoveText, buttontext);


    }

    @When("I sort the products by {string}")
    public void iSortTheProductsBy(String value) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement Dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product_sort_container")));
        Dropdown.click();
        Select sortDropdown = new Select(Dropdown);
        sortDropdown.selectByContainsVisibleText(value);


    }

    @Then("the products should be displayed in descending price order")
    public void theProductsShouldBeDisplayedInDescendingPriceOrder() {

        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        for (int i = 0; i < prices.size() - 1; i++) {
            Double currentprice = Double.parseDouble(prices.get(i).getText().replace("$", ""));
            double nextprice = Double.parseDouble(prices.get(i + 1).getText().replace("$", ""));
            assertTrue(currentprice >= nextprice, "Prices are sorted in descendingorder");

        }


    }

    @Then("{string} should be displayed on the login page")
    public void shouldBeDisplayedOnTheLoginPage(String ExpectedLogo) {
        String actualLogo = driver.findElement(By.className("login_logo")).getText();
        assertEquals(ExpectedLogo, actualLogo);
    }

    @And("accepted usernames should be displayed")
    public void acceptedUsernamesShouldBeDisplayed() {
        String users = driver.findElement(By.id("login_credentials")).getText();
        users.contains("Accepted usernames are");
        users.contains("standard_user");
        users.contains("locked_out_user");
        users.contains("problem_user");


    }

    @And("password for all users should be displayed as {string}")
    public void passwordForAllUsersShouldBeDisplayedAs(String expectedPassword) {
        String actualPassword = driver.findElement(By.className("login_password")).getText();
        String Password = actualPassword.replace("Password for all users:", "").trim();

        assertEquals(expectedPassword, Password);
    }


    @And("username , password and Login text fields should be displayed")
    public void usernamePasswordAndLoginTextFieldsShouldBeDisplayed() {
        WebElement actualusername = driver.findElement(By.id("user-name"));
        WebElement actualpassword = driver.findElement(By.id("password"));
        WebElement actualLogin = driver.findElement(By.id("login-button"));
        assertTrue(actualusername.isDisplayed());
        assertTrue(actualpassword.isDisplayed());
        assertTrue(actualLogin.isDisplayed());

    }

    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();


    }

    @Then("login result should be {string} with error message {string}")
    public void loginResultShouldBeWithErrorMessage(String expectedResult, String expectedErrormessage) {
        if (expectedResult.equalsIgnoreCase("success")) {
            assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        } else {

            String actualErrormessage = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
            assertEquals(expectedErrormessage, actualErrormessage);
        }


    }


    @Then("{string} page should be displayed")
    public void pageShouldBeDisplayed(String expectedpage) {

        String actualpage = driver.findElement(By.className("title")).getText();
        assertEquals(expectedpage, actualpage);

    }

    @When("I add all products to the cart")
    public void iAddAllProductsToTheCart() {

        List<WebElement> addtoCartButton = driver.findElements(By.xpath("//button[starts-with(@data-test,'add-to-cart')]"));
        for (WebElement button : addtoCartButton) {
            button.click();
        }

    }


    @When("I click the shopping cart")
    public void iClickTheShoppingCart() {

        driver.findElement(By.className("shopping_cart_link")).click();
    }

    @And("all added products should be displayed in the cart")
    public void allAddedProductsShouldBeDisplayedInTheCart() {
        int actualproductadded = Integer.parseInt(driver.findElement(By.xpath("//a[@class='shopping_cart_link']/span")).getText());

        List<WebElement> ExpectedProductadded = driver.findElements(By.xpath("//div[@data-test='inventory-item-name']"));

        assertEquals(actualproductadded, ExpectedProductadded.size());


    }

    @When("I click the {string} button")
    public void iClickTheButton(String buttonName) {
        String buttonTouse = buttonName.trim().toLowerCase().replaceAll("\\s+", "-");
        driver.findElement(By.xpath("//*[@data-test='" + buttonTouse + "']")).click();


    }

    @When("I enter first name {string} last name {string} and postal code {string}")
    public void iEnterFirstNameLastNameAndPostalCode(String Fname, String Lname, String Postcode) {

        driver.findElement(By.id("first-name")).sendKeys(Fname);
        driver.findElement(By.id("last-name")).sendKeys(Lname);
        driver.findElement(By.id("postal-code")).sendKeys(Postcode);

    }

    @And("all selected products should be displayed in checkout overview")
    public void allSelectedProductsShouldBeDisplayedInCheckoutOverview() {

        int cartCount = Integer.parseInt(
                driver.findElement(
                        By.xpath("//a[@class='shopping_cart_link']/span")
                ).getText()
        );
        List<WebElement> overviewProducts = driver.findElements(By.xpath("//div[@data-test='inventory_item']"));


        assertEquals(cartCount, overviewProducts.size());


    }

    @And("order confirmation should be {string}")
    public void orderConfirmationShouldBe(String expectedmsg) {
        String actualmsg = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText();
        assertEquals(expectedmsg, actualmsg);


    }

    @And("order dispatch message should be displayed")
    public void orderDispatchMessageShouldBeDisplayed() {

        String actualmsg = driver.findElement(By.xpath("//div[@class='complete-text']")).getText();
        System.out.println(actualmsg);
    }

    @And("{string} button should be displayed")
    public void buttonShouldBeDisplayed(String buttonName) {
        String value = buttonName.trim().toLowerCase().replaceAll("\\s", "-");
        driver.findElement(By.xpath("//button[contains(@data-test,'" + value + "')]")).isDisplayed();
    }

    @Then("order PDF should be generated")
    public void orderPDFShouldBeGenerated() {

        String downloadPath = System.getProperty("user.home") + "\\Downloads";
        File DownloadFolder = new File(downloadPath);


    }

    @When("I click the hamburger menu")
    public void iClickTheHamburgerMenu() {
         driver.findElement(By.id("react-burger-menu-btn"));
    }




    @Then("footer should display {string}")
    public void footerShouldDisplay(String arg0) {
    }




    @Then("{string} social media link should be displayed")
    public void socialMediaLinkShouldBeDisplayed(String link) {

        WebElement socialmedia=  driver.findElement(By.linkText(link));

        assertTrue(socialmedia.isDisplayed());
        socialmedia.click();
    }
}