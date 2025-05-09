package org.example;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class MainMenu {

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

    public void dismissPopups() {
        // Handle JS alerts
        try {
            wait.withTimeout(Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());
            Alert alert = auto.switchTo().alert();
            alert.accept();
        } catch (Exception ignored) {}

        // Handle modals using robust selectors
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

    @AfterMethod
    public void tearDown() {
        if (auto != null) {
            auto.quit();
        }
    }

    public void login(String username) {
        WebElement userField = auto.findElement(By.id("user-name"));
        userField.clear();
        userField.sendKeys(username);

        WebElement passField = auto.findElement(By.id("password"));
        passField.clear();
        passField.sendKeys("secret_sauce");

        auto.findElement(By.id("login-button")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void openMenu() {
        auto.findElement(By.id("react-burger-menu-btn")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void logout() {
        openMenu();
        auto.findElement(By.id("logout_sidebar_link")).click();
        sleepOneSecond();
        dismissPopups();
    }

    @Test(priority = 0)
    public void standardUserLogout() {
        auto.navigate().refresh();
        login("standard_user");
        logout();
        sleepOneSecond();
    }

    @Test(priority = 1)
    public void problemUserLogout() {
        auto.navigate().refresh();
        login("problem_user");
        logout();
        sleepOneSecond();
    }

    @Test(priority = 2)
    public void performanceGlitchUserLogout() {
        auto.navigate().refresh();
        login("performance_glitch_user");
        logout();
        sleepOneSecond();
    }

    @Test(priority = 3)
    public void errorUserLogout() {
        auto.navigate().refresh();
        login("error_user");
        logout();
        sleepOneSecond();
    }

    @Test(priority = 4)
    public void visualUserLogout() {
        auto.navigate().refresh();
        login("visual_user");
        logout();
        sleepOneSecond();
    }
}


