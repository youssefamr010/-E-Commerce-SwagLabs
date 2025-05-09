package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class MainMenuInvalid {

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

    public void openMenu() {
        auto.findElement(By.id("react-burger-menu-btn")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void clickAllItems() {
        openMenu();
        auto.findElement(By.id("inventory_sidebar_link")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void resetApp() {
        openMenu();
        auto.findElement(By.id("reset_sidebar_link")).click();
        sleepOneSecond();
        dismissPopups();
    }

    @Test(priority = 0)
    public void standardUserAllItems() {
        auto.navigate().refresh();
        login("standard_user");
        clickAllItems();
        sleepOneSecond();
        Assert.fail("All Items functionality is invalid for standard_user.");
    }

    @Test(priority = 1)
    public void standardUserResetApp() {
        auto.navigate().refresh();
        login("standard_user");
        resetApp();
        sleepOneSecond();
        Assert.fail("Reset App functionality is invalid for standard_user.");
    }

    @Test(priority = 2)
    public void problemUserAllItems() {
        auto.navigate().refresh();
        login("problem_user");
        clickAllItems();
        sleepOneSecond();
        Assert.fail("All Items functionality is invalid for problem_user.");
    }

    @Test(priority = 3)
    public void problemUserResetApp() {
        auto.navigate().refresh();
        login("problem_user");
        resetApp();
        sleepOneSecond();
        Assert.fail("Reset App functionality is invalid for problem_user.");
    }

    @Test(priority = 4)
    public void performanceGlitchUserAllItems() {
        auto.navigate().refresh();
        login("performance_glitch_user");
        clickAllItems();
        sleepOneSecond();
        Assert.fail("All Items functionality is invalid for performance_glitch_user.");
    }

    @Test(priority = 5)
    public void performanceGlitchUserResetApp() {
        auto.navigate().refresh();
        login("performance_glitch_user");
        resetApp();
        sleepOneSecond();
        Assert.fail("Reset App functionality is invalid for performance_glitch_user.");
    }

    @Test(priority = 6)
    public void errorUserAllItems() {
        auto.navigate().refresh();
        login("error_user");
        clickAllItems();
        sleepOneSecond();
        Assert.fail("All Items functionality is invalid for error_user.");
    }

    @Test(priority = 7)
    public void errorUserResetApp() {
        auto.navigate().refresh();
        login("error_user");
        resetApp();
        sleepOneSecond();
        Assert.fail("Reset App functionality is invalid for error_user.");
    }

    @Test(priority = 8)
    public void visualUserAllItems() {
        auto.navigate().refresh();
        login("visual_user");
        clickAllItems();
        sleepOneSecond();
        Assert.fail("All Items functionality is invalid for visual_user.");
    }

    @Test(priority = 9)
    public void visualUserResetApp() {
        auto.navigate().refresh();
        login("visual_user");
        resetApp();
        sleepOneSecond();
        Assert.fail("Reset App functionality is invalid for visual_user.");
    }

}
