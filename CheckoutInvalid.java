package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CheckoutInvalid {

    WebDriver auto;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
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
    public void teardown() {
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
        auto.findElement(By.id("user-name")).clear();
        auto.findElement(By.id("user-name")).sendKeys(username);
        auto.findElement(By.id("password")).clear();
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
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
        sleepOneSecond();
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
        sleepOneSecond();
    }

    @Test(priority = 0)
    public void checkoutInvalidFieldsProblemUser() {
        login("problem_user");
        addAllProducts();
        goToCartAndCheckout();
        fillCheckoutFields("@@@", "####", "$$$$");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
        Assert.fail("Checkout with invalid fields should not be allowed for problem_user (test case invalid).");
    }

    @Test(priority = 1)
    public void checkoutInvalidFieldsPerformanceGlitchUser() {
        login("performance_glitch_user");
        addAllProducts();
        goToCartAndCheckout();
        fillCheckoutFields("!!", "11##", "   ");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
        Assert.fail("Checkout with invalid fields should not be allowed for performance_glitch_user (test case invalid).");
    }

    @Test(priority = 2)
    public void checkoutNoProductsValidFieldsErrorUser() {
        login("error_user");
        goToCartAndCheckout();
        fillCheckoutFields("Valid", "Name", "55555");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
        Assert.fail("Checkout with no products should not be allowed for error_user (test case invalid).");
    }

    @Test(priority = 3)
    public void checkoutNoProductsInvalidFieldsVisualUser() {
        login("visual_user");
        goToCartAndCheckout();
        fillCheckoutFields("   ", "%^&", "@@##");
        auto.findElement(By.id("continue")).click();
        sleepOneSecond();
        Assert.fail("Checkout with no products and invalid fields should not be allowed for visual_user (test case invalid).");
    }
}
