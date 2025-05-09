package org.example;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CartInvalid {

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

    public void goToCart() {
        auto.findElement(By.className("shopping_cart_link")).click();
        sleepOneSecond();
    }

    @Test(priority = 0)
    public void cartWithoutAddingStandardUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("standard_user");
        goToCart();
        Assert.fail("Cart without adding any products should be invalid for standard_user.");
    }

    @Test(priority = 1)
    public void cartWithoutAddingProblemUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("problem_user");
        goToCart();
        Assert.fail("Cart without adding any products should be invalid for problem_user.");
    }

    @Test(priority = 2)
    public void cartWithoutAddingPerformanceGlitchUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("performance_glitch_user");
        goToCart();
        Assert.fail("Cart without adding any products should be invalid for performance_glitch_user.");
    }

    @Test(priority = 3)
    public void cartWithoutAddingErrorUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("error_user");
        goToCart();
        Assert.fail("Cart without adding any products should be invalid for error_user.");
    }

    @Test(priority = 4)
    public void cartWithoutAddingVisualUser() {
        auto.navigate().refresh();
        sleepOneSecond();
        login("visual_user");
        goToCart();
        Assert.fail("Cart without adding any products should be invalid for visual_user.");
    }
}
