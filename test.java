package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class test {

    WebDriver auto;

    @BeforeTest
    public void setUpBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
            put("credentials_enable_service", false);
            put("profile.password_manager_enabled", false);
        }});
        try {
            Alert alert = auto.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored){}
        try {
            WebElement closeBtn = auto.findElement(By.cssSelector(".modal__close"));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
            }
        } catch (NoSuchElementException ignored){}
        auto = new ChromeDriver(options);
        auto.manage().window().maximize();
        auto.navigate().to("https://www.saucedemo.com/");
    }

    @BeforeMethod
    public void beforeEachTest() {
        auto.navigate().refresh();
        try {
            Alert alert = auto.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {}

        try {
            WebElement closeBtn = auto.findElement(By.cssSelector(".modal__close"));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
            }
        } catch (NoSuchElementException ignored) {}

        auto.findElement(By.id("user-name")).clear();
        auto.findElement(By.id("password")).clear();
    }


    @Test
    public void loginStandardUser() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();

        String currentUrl = auto.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html", "Login failed for standard_user");
    }

    @Test
    public void loginLockedOutUser() {
        auto.findElement(By.id("user-name")).sendKeys("locked_out_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not shown for locked_out_user");
    }

    @Test
    public void loginProblemUser() {
        auto.findElement(By.id("user-name")).sendKeys("problem_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();

        String currentUrl = auto.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html", "Login failed for problem_user");
    }

    @Test
    public void loginPerformanceGlitchUser() {
        auto.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();

        String currentUrl = auto.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html", "Login failed for performance_glitch_user");
    }

    @Test
    public void loginErrorUser() {
        auto.findElement(By.id("user-name")).sendKeys("error_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();

        String currentUrl = auto.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html", "Login failed for error_user");
    }

    @Test
    public void loginVisualUser() {
        auto.findElement(By.id("user-name")).sendKeys("visual_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();

        String currentUrl = auto.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html", "Login failed for visual_user");
    }

    @Test
    public void loginEmptyFields() {
        auto.findElement(By.id("user-name")).sendKeys("");
        auto.findElement(By.id("password")).sendKeys("");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not shown for empty fields");
    }

    @Test
    public void loginSpacesOnly() {
        auto.findElement(By.id("user-name")).sendKeys("    ");
        auto.findElement(By.id("password")).sendKeys("    ");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not shown for spaces only");
    }

    @Test
    public void loginSpecialCharsUsernameValidPassword() {
        auto.findElement(By.id("user-name")).sendKeys("!@#$%");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error not shown for special char username");
    }

    @Test
    public void loginValidUsernameInvalidPassword() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("%^&*(");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error not shown for invalid password");
    }

    @Test
    public void loginSpecialCharsBothFields() {
        auto.findElement(By.id("user-name")).sendKeys("#$%^");
        auto.findElement(By.id("password")).sendKeys("&*()");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error not shown for special chars in both fields");
    }

    @Test
    public void loginNumbersInBothFields() {
        auto.findElement(By.id("user-name")).sendKeys("12345");
        auto.findElement(By.id("password")).sendKeys("67890");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error not shown for numbers only login");
    }

    @Test
    public void loginValidUserPasswordWithSpaces() {
        auto.findElement(By.id("user-name")).sendKeys(" standard_user ");
        auto.findElement(By.id("password")).sendKeys(" secret_sauce ");
        auto.findElement(By.id("login-button")).click();

        WebElement errorMessage = auto.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error not shown for spaces around valid credentials");
    }
}



