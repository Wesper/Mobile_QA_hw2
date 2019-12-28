package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver<MobileElement> driver;
    private final String email = "testemail@test.ru";
    private final String password = "!pas@w0rD";

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_5X_API_29_x86");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.wdiodemoapp");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        capabilities.setCapability(MobileCapabilityType.APP, "/Users/artembelanko/IdeaProjects/" +
                "otus.configuration.and.run.tests/src/test/resources/apk/demo.apk");

        URL appiumUrl = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(appiumUrl, capabilities);

    }

    @Test
    public void firstTest() throws InterruptedException {
        WebElement loginMenu = waitElementPresent("//*[@text = 'Login']", 10);
        loginMenu.click();
        WebElement signUpSection = waitElementPresent("//*[@text = 'Sign up']", 10);
        signUpSection.click();
        WebElement emailField = driver.findElementByXPath("//*[@text = 'Email']");
        emailField.click();
        emailField.sendKeys(email);
        WebElement passwordField = driver.findElementByXPath("//*[@text = 'Password']");
        passwordField.click();
        passwordField.sendKeys(password);
        WebElement passwordConfirmField = driver.findElementByXPath("//*[@text = 'Confirm password']");
        passwordConfirmField.click();
        passwordConfirmField.sendKeys(password);
        driver.hideKeyboard();
        WebElement loginButton = driver.findElementByXPath("//*[@text = 'SIGN UP']");
        loginButton.click();
        WebElement alertTitle = waitElementPresent(By.id("android:id/alertTitle"), 10);
        Assert.assertEquals(alertTitle.getText(), "Signed Up!");
        String alertText = driver.findElementById("android:id/message").getText();
        Assert.assertEquals(alertText, "You successfully signed up!");
        WebElement alertButton = driver.findElementById("android:id/button1");
        alertButton.click();
        Thread.sleep(1000);
        List<MobileElement> alertHiden = driver.findElementsByXPath("//*[@text = 'Sign up']");
        if (alertHiden.size() == 0){
            Assert.fail("Successfull alert not hiden");
        }
    }

    @Test
    public void secondTest() throws InterruptedException {
        WebElement loginSection = driver.findElementByXPath("//*[@text = 'Login']");
        loginSection.click();
        WebElement emailField = driver.findElementByXPath("//*[@content-desc = 'input-email']");
        Assert.assertEquals(emailField.getText(), email);
        WebElement passwordField = driver.findElementByXPath("//*[@content-desc = 'input-password']");
        Assert.assertNotNull(passwordField.getText());
        WebElement loginButton = driver.findElementByXPath("//*[@content-desc = 'button-LOGIN']");
        loginButton.click();
        WebElement alertTitle = waitElementPresent(By.id("android:id/alertTitle"), 10);
        Assert.assertEquals(alertTitle.getText(), "Success");
        String alertText = driver.findElementById("android:id/message").getText();
        Assert.assertEquals(alertText, "You are logged in!");
        WebElement alertButton = driver.findElementById("android:id/button1");
        alertButton.click();
        Thread.sleep(1000);
        List<MobileElement> alertHiden = driver.findElementsByClassName("android.widget.FrameLayout");
        if (alertHiden.size() == 0){
            Assert.fail("Successfull alert not hiden");
        }
    }

    @Test
    public void thirdTest() throws InterruptedException {
        WebElement emailField = driver.findElementByXPath("//*[@content-desc = 'input-email']");
        emailField.clear();
        emailField.sendKeys(email);
        WebElement passwordField = driver.findElementByXPath("//*[@content-desc = 'input-password']");
        passwordField.clear();
        passwordField.sendKeys(password);
        WebElement loginButton = driver.findElementByXPath("//*[@content-desc = 'button-LOGIN']");
        loginButton.click();
        WebElement alertTitle = waitElementPresent(By.id("android:id/alertTitle"), 10);
        Assert.assertEquals(alertTitle.getText(), "Success");
        String alertText = driver.findElementById("android:id/message").getText();
        Assert.assertEquals(alertText, "You are logged in!");
        WebElement alertButton = driver.findElementById("android:id/button1");
        alertButton.click();
        Thread.sleep(1000);
        List<MobileElement> alertHiden = driver.findElementsByXPath("//*[@content-desc = 'input-email']");
        if (alertHiden.size() == 0){
            Assert.fail("Successfull alert not hiden");
        }
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

    private WebElement waitElementPresent(String locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        By by = By.xpath(locator);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitElementPresent(By by, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
}
