package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;

public class Checkout {
    WebDriver auto;
    WebDriverWait wait;

    @BeforeMethod
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        auto = new ChromeDriver(options);
        wait = new WebDriverWait(auto, Duration.ofSeconds(8));
        auto.manage().window().maximize();
        auto.navigate().to("https://www.saucedemo.com/");
        dismissPopups();
    }

    @AfterMethod
    public void tearDown() {
        if (auto != null) {
            auto.quit();
        }
    }

    public void dismissPopups() {

        try {
            wait.withTimeout(Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());
            Alert alert = auto.switchTo().alert();
            alert.accept();
        } catch (Exception ignored) {}


        try {
            WebElement closeBtn = wait.withTimeout(Duration.ofSeconds(2)).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".modal__close, [data-test=modal-close], .modal-close, .modal-close-button"))
            );
            if (closeBtn.isDisplayed() && closeBtn.isEnabled()) {
                closeBtn.click();
            }
        } catch (Exception ignored) {}
    }
    public void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    public void login(String username) {
        dismissPopups();
        WebElement userField = auto.findElement(By.id("user-name"));
        userField.clear();
        userField.sendKeys(username);

        WebElement passwordField = auto.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");

        auto.findElement(By.id("login-button")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void addAllProducts() {
        dismissPopups();
        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        auto.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
    }

    public void goToCartAndCheckout() {
        dismissPopups();
        auto.findElement(By.className("shopping_cart_link")).click();
        auto.findElement(By.id("checkout")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void fillCheckoutFields(String firstName, String lastName, String postalCode) {
        dismissPopups();
        auto.findElement(By.id("first-name")).sendKeys(firstName);
        auto.findElement(By.id("last-name")).sendKeys(lastName);
        auto.findElement(By.id("postal-code")).sendKeys(postalCode);
    }

    // -------- Tests Start Here --------

    @Test(priority = 0)
    public void emptyFieldsCancelStandardUser() {
        login("standard_user");
        addAllProducts();
        goToCartAndCheckout();
        auto.findElement(By.id("cancel")).click();
        sleepOneSecond();
    }

    @Test(priority = 1)
    public void emptyFieldsCancelProblemUser() {
        login("problem_user");
        addAllProducts();
        goToCartAndCheckout();
        auto.findElement(By.id("cancel")).click();
        sleepOneSecond();
    }

    @Test(priority = 2)
    public void emptyFieldsCancelPerformanceGlitchUser() {
        login("performance_glitch_user");
        addAllProducts();
        goToCartAndCheckout();
        auto.findElement(By.id("cancel")).click();
        sleepOneSecond();
    }

    @Test(priority = 3)
    public void emptyFieldsCancelErrorUser() {
        login("error_user");
        addAllProducts();
        goToCartAndCheckout();
        auto.findElement(By.id("cancel")).click();
        sleepOneSecond();
    }

    @Test(priority = 4)
    public void emptyFieldsCancelVisualUser() {
        login("visual_user");
        addAllProducts();
        goToCartAndCheckout();
        auto.findElement(By.id("cancel")).click();
        sleepOneSecond();
    }

    @Test(priority = 5)
    public void validInputsContinueStandardUser() {
        login("standard_user");
        addAllProducts();
        goToCartAndCheckout();
        fillCheckoutFields("Ali", "Khalid", "12345");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
    }

    @Test(priority = 6)
    public void validInputsContinueProblemUser() {
        login("problem_user");
        addAllProducts();
        goToCartAndCheckout();
        fillCheckoutFields("Joly", "Smith", "54321");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
    }

    @Test(priority = 7)
    public void validInputsContinuePerformanceGlitchUser() {
        login("performance_glitch_user");
        addAllProducts();
        goToCartAndCheckout();
        fillCheckoutFields("Youssef", "Amr", "11111");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
    }

    @Test(priority = 8)
    public void validInputsContinueErrorUser() {
        login("error_user");
        addAllProducts();
        goToCartAndCheckout();
        fillCheckoutFields("Mohamed", "Gamal", "22222");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
    }

    @Test(priority = 9)
    public void validInputsContinueVisualUser() {
        login("visual_user");
        addAllProducts();
        goToCartAndCheckout();
        fillCheckoutFields("Ismeil", "Tamer", "33333");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
    }
}







