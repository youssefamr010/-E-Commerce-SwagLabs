package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class MainPageinvalid {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars", "--disable-notifications", "--disable-popup-blocking");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // robust wait
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
        handleAlertOrModalIfPresent();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    public void handleAlertOrModalIfPresent() {

        try {
            wait.withTimeout(Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (TimeoutException | NoAlertPresentException ignored) {
        }

        try {
            WebElement closeModal = wait
                    .withTimeout(Duration.ofSeconds(2))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal__close, [data-test=modal-close], .modal-close, .modal-close-button")));
            if (closeModal.isDisplayed() && closeModal.isEnabled()) {
                closeModal.click();
            }
        } catch (TimeoutException | NoSuchElementException ignored) {
        }
    }
    public void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    public void login(String username) {
        handleAlertOrModalIfPresent();

        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        driver.findElement(By.id("login-button")).click();

        handleAlertOrModalIfPresent();
    }

    public void openSideMenu() {
        handleAlertOrModalIfPresent();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        handleAlertOrModalIfPresent();
        sleepOneSecond();
    }

    public void clickAllItems() {
        openSideMenu();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("inventory_sidebar_link"))).click();
        handleAlertOrModalIfPresent();

        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html", "All Items failed: Not navigated to inventory page.");
        sleepOneSecond();
    }

    public void clickResetApp() {
        openSideMenu();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link"))).click();
        handleAlertOrModalIfPresent();

        boolean cartIsEmpty;
        try {
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            cartIsEmpty = cartBadge.isDisplayed();
        } catch (NoSuchElementException e) {
            cartIsEmpty = false;
        }
        Assert.assertFalse(cartIsEmpty, "Reset App failed: Cart is not empty after reset.");
        sleepOneSecond();
    }



    public void addAllProducts() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        sleepOneSecond();
    }

    public void removeAllProducts() {
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("remove-sauce-labs-fleece-jacket")).click();
        driver.findElement(By.id("remove-sauce-labs-onesie")).click();
        driver.findElement(By.id("remove-test.allthethings()-t-shirt-(red)")).click();
        sleepOneSecond();
    }



    @Test(priority = 0)
    public void mainMenuStandardUser_AllItems() {
        login("standard_user");
        clickAllItems();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Standard User AllItems)");
    }

    @Test(priority = 1)
    public void mainMenuStandardUser_ResetApp() {
        login("standard_user");
        clickResetApp();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Standard User ResetApp)");
    }

    @Test(priority = 2)
    public void mainMenuProblemUser_AllItems() {
        login("problem_user");
        clickAllItems();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Problem User AllItems)");
    }

    @Test(priority = 3)
    public void mainMenuProblemUser_ResetApp() {
        login("problem_user");
        clickResetApp();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Problem User ResetApp)");
    }

    @Test(priority = 4)
    public void mainMenuPerformanceGlitchUser_AllItems() {
        login("performance_glitch_user");
        clickAllItems();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Performance Glitch User AllItems)");
    }

    @Test(priority = 5)
    public void mainMenuPerformanceGlitchUser_ResetApp() {
        login("performance_glitch_user");
        clickResetApp();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Performance Glitch User ResetApp)");
    }

    @Test(priority = 6)
    public void mainMenuErrorUser_AllItems() {
        login("error_user");
        clickAllItems();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Error User AllItems)");
    }

    @Test(priority = 7)
    public void mainMenuErrorUser_ResetApp() {
        login("error_user");
        clickResetApp();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Error User ResetApp)");
    }

    @Test(priority = 8)
    public void mainMenuVisualUser_AllItems() {
        login("visual_user");
        clickAllItems();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Visual User AllItems)");
    }

    @Test(priority = 9)
    public void mainMenuVisualUser_ResetApp() {
        login("visual_user");
        clickResetApp();
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
        Assert.fail("Add/Remove Product functionality is invalid for all users! (Visual User ResetApp)");
    }
}








