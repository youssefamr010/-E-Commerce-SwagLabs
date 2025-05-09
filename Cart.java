package org.example;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class Cart {
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
        sleepOneSecond();
        dismissPopups();
    }

    @AfterMethod
    public void tearDown() {
        if (auto != null) {
            auto.quit();
        }
    }

    public void dismissPopups() {
        // Handle JS alerts
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
        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        auto.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        auto.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        sleepOneSecond();
    }

    public void goToCart() {
        auto.findElement(By.className("shopping_cart_link")).click();
        sleepOneSecond();
    }

    public void clickContinueShopping() {
        auto.findElement(By.id("continue-shopping")).click();
        sleepOneSecond();
    }

    @Test(priority = 0)
    public void cartAfterAddingStandardUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("standard_user");
        addAllProducts();
        goToCart();
    }

    @Test(priority = 1)
    public void cartAfterAddingProblemUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("problem_user");
        addAllProducts();
        // Fail the test as problem_user cannot add all products.
        Assert.fail("Problem User cannot add all products: test should be marked as failed (invalid).");
        goToCart();
    }

    @Test(priority = 2)
    public void cartAfterAddingPerformanceGlitchUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("performance_glitch_user");
        addAllProducts();
        goToCart();
    }

    @Test(priority = 3)
    public void cartAfterAddingErrorUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("error_user");
        addAllProducts();
        Assert.fail("Error User cannot add all products: test should be marked as failed (invalid).");
        goToCart();
    }

    @Test(priority = 4)
    public void cartAfterAddingVisualUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("visual_user");
        addAllProducts();
        goToCart();
    }

    @Test(priority = 5)
    public void continueShoppingStandardUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("standard_user");
        addAllProducts();
        goToCart();
        clickContinueShopping();
    }

    @Test(priority = 6)
    public void continueShoppingProblemUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("problem_user");
        addAllProducts();
        Assert.fail("Problem User cannot add all products: test should be marked as failed (invalid).");
        goToCart();
        clickContinueShopping();
    }

    @Test(priority = 7)
    public void continueShoppingPerformanceGlitchUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("performance_glitch_user");
        addAllProducts();
        goToCart();
        clickContinueShopping();
    }

    @Test(priority = 8)
    public void continueShoppingErrorUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("error_user");
        addAllProducts();

        Assert.fail("Error User cannot add all products: test should be marked as failed (invalid).");
        goToCart();
        clickContinueShopping();
    }

    @Test(priority = 9)
    public void continueShoppingVisualUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("visual_user");
        addAllProducts();
        goToCart();
        clickContinueShopping();
    }
}
