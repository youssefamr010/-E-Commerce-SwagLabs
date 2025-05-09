package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;

public class MainPage {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars", "--disable-notifications", "--disable-popup-blocking");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        dismissPopups();
    }

    public void dismissPopups() {

        try {
            wait.withTimeout(Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception ignored) {}

        // Handle modals with more robust selector set
        try {
            WebElement closeModal = wait.withTimeout(Duration.ofSeconds(2)).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".modal__close, [data-test=modal-close], .modal-close, .modal-close-button"))
            );
            if (closeModal.isDisplayed() && closeModal.isEnabled()) {
                closeModal.click();
            }
        } catch (Exception ignored) {}
    }
    public void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void login(String username) {
        dismissPopups();

        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        driver.findElement(By.id("login-button")).click();

        sleepOneSecond();
        dismissLoginError();
        dismissPopups();
    }

    public void dismissLoginError() {
        try {
            WebElement errorBtn = driver.findElement(By.className("error-button"));
            if (errorBtn.isDisplayed() && errorBtn.isEnabled()) {
                errorBtn.click();
            }
        } catch (NoSuchElementException ignored) {
        }
    }

    public void addAllProducts() {
        dismissPopups();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
        driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void removeAllProducts() {
        dismissPopups();
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
        driver.findElement(By.id("remove-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("remove-sauce-labs-fleece-jacket")).click();
        driver.findElement(By.id("remove-sauce-labs-onesie")).click();
        driver.findElement(By.id("remove-test.allthethings()-t-shirt-(red)")).click();
        sleepOneSecond();
        dismissPopups();
    }

    public void logout() {
        dismissPopups();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        sleepOneSecond();
        dismissPopups();
    }


    @Test(priority = 0)
    public void addProductsStandardUser() {
        login("standard_user");
        addAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 1)
    public void removeProductsStandardUser() {
        login("standard_user");
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 14)
    public void logoutStandardUser() {
        login("standard_user");
        addAllProducts();
        logout();
        sleepOneSecond();
    }



    @Test(priority = 2)
    public void addProductsProblemUser() {
        login("problem_user");
        addAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 3)
    public void removeProductsProblemUser() {
        login("problem_user");
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 13)
    public void logoutProblemUser() {
        login("problem_user");
        addAllProducts();
        logout();
        sleepOneSecond();
    }

    // ===== Performance Glitch User Tests =====

    @Test(priority = 4)
    public void addProductsPerformanceGlitchUser() {
        login("performance_glitch_user");
        addAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 5)
    public void removeProductsPerformanceGlitchUser() {
        login("performance_glitch_user");
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 12)
    public void logoutPerformanceGlitchUser() {
        login("performance_glitch_user");
        addAllProducts();
        logout();
        sleepOneSecond();
    }

    // ===== Error User Tests =====

    @Test(priority = 6)
    public void addProductsErrorUser() {
        login("error_user");
        addAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 7)
    public void removeProductsErrorUser() {
        login("error_user");
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 11)
    public void logoutErrorUser() {
        login("error_user");
        addAllProducts();
        logout();
        sleepOneSecond();
    }

    // ===== Visual User Tests =====

    @Test(priority = 8)
    public void addProductsVisualUser() {
        login("visual_user");
        addAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 9)
    public void removeProductsVisualUser() {
        login("visual_user");
        addAllProducts();
        removeAllProducts();
        sleepOneSecond();
    }

    @Test(priority = 10)
    public void logoutVisualUser() {
        login("visual_user");
        addAllProducts();
        logout();
        sleepOneSecond();
    }
}