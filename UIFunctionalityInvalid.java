package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

public class UIFunctionalityInvalid {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars", "--disable-notifications", "--disable-popup-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");

        dismissPopups();
    }

    @AfterMethod
    public void teardown() {
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
    public void performLogin(String userName, String passWord) {
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(userName);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(passWord);

        driver.findElement(By.id("login-button")).click();
        sleepOneSecond();
    }

    public void validateLoginSuccess() {
        // If inventory page URL, success. Otherwise, fail.
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Login was unsuccessful");
    }

    @Test(priority = 0)
    public void testLockedOutUserLogin() {
        performLogin("locked_out_user", "secret_sauce");
        validateLoginSuccess(); // Should FAIL (expected)
        sleepOneSecond();
    }

    @Test(priority = 1)
    public void testEmptyFieldsLogin() {
        performLogin("", "");
        validateLoginSuccess();
        sleepOneSecond();
    }

    @Test(priority = 2)
    public void testSpacesOnlyLogin() {
        performLogin("    ", "    ");
        validateLoginSuccess();
        sleepOneSecond();
    }

    @Test(priority = 3)
    public void testSpecialCharactersInUsername() {
        performLogin("!@#$%", "secret_sauce");
        validateLoginSuccess();
        sleepOneSecond();
    }

    @Test(priority = 4)
    public void testValidUsernameInvalidPassword() {
        performLogin("standard_user", "%^&*(");
        validateLoginSuccess();
        sleepOneSecond();
    }

    @Test(priority = 5)
    public void testSpecialCharactersInBothFields() {
        performLogin("#$%^", "&*()");
        validateLoginSuccess();
        sleepOneSecond();
    }

    @Test(priority = 6)
    public void testNumbersInBothFields() {
        performLogin("12345", "67890");
        validateLoginSuccess();
        sleepOneSecond();
    }

    @Test(priority = 7)
    public void testValidUserPasswordWithSpaces() {
        performLogin(" standard_user ", " s ec re t_sau c e ");
        validateLoginSuccess();
        sleepOneSecond();
    }
}





