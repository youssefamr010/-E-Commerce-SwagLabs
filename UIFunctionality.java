package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

public class UIFunctionality {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars", "--disable-notifications", "--disable-popup-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");

        dismissPopups();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void dismissPopups() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {}

        try {
            WebElement closeModal = driver.findElement(By.cssSelector(".modal__close"));
            if (closeModal.isDisplayed()) {
                closeModal.click();
            }
        } catch (NoSuchElementException ignored) {}
    }
    public void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    public void login(String username) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void verifyLogin() {
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Login verification failed!");
    }

    @Test(priority = 0)
    public void testStandardUserLogin() {
        login("standard_user");
        verifyLogin();
        sleepOneSecond();
        dismissPopups();
    }

    @Test(priority = 1)
    public void testProblemUserLogin() {
        login("problem_user");
        verifyLogin();
        sleepOneSecond();
        dismissPopups();
    }

    @Test(priority = 2)
    public void testPerformanceGlitchUserLogin() {
        login("performance_glitch_user");
        verifyLogin();
        sleepOneSecond();
        dismissPopups();
    }

    @Test(priority = 3)
    public void testErrorUserLogin() {
        login("error_user");
        verifyLogin();
        sleepOneSecond();
        dismissPopups();
    }

    @Test(priority = 4)
    public void testVisualUserLogin() {
        login("visual_user");
        verifyLogin();
        sleepOneSecond();
        dismissPopups();
    }
}








