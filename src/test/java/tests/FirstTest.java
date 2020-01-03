package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
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
import java.time.Duration;
import java.util.List;

public class FirstTest {

    private AppiumDriver<MobileElement> driver;
    private final String EMAIL = "testemail@test.ru";
    private final String PASSWORD = "!pas@w0rD";

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

    @Test(priority = 1)
    public void firstTest() throws InterruptedException {
        WebElement loginMenu = waitElementPresent("//*[@text = 'Login']", 10);
        loginMenu.click();
        WebElement signUpSection = waitElementPresent("//*[@text = 'Sign up']", 10);
        signUpSection.click();
        WebElement emailField = driver.findElementByXPath("//*[@text = 'Email']");
        emailField.click();
        emailField.sendKeys(EMAIL);
        WebElement passwordField = driver.findElementByXPath("//*[@text = 'Password']");
        passwordField.click();
        passwordField.sendKeys(PASSWORD);
        WebElement passwordConfirmField = driver.findElementByXPath("//*[@text = 'Confirm password']");
        passwordConfirmField.click();
        passwordConfirmField.sendKeys(PASSWORD);
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

    @Test(priority = 2)
    public void secondTest() throws InterruptedException {
        WebElement loginSection = driver.findElementByXPath("//*[@text = 'Login']");
        loginSection.click();
        WebElement emailField = driver.findElementByXPath("//*[@content-desc = 'input-email']");
        Assert.assertEquals(emailField.getText(), EMAIL);
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

    @Test (priority = 3)
    public void thirdTest() throws InterruptedException {
        WebElement emailField = driver.findElementByXPath("//*[@content-desc = 'input-email']");
        emailField.clear();
        emailField.sendKeys(EMAIL);
        WebElement passwordField = driver.findElementByXPath("//*[@content-desc = 'input-password']");
        passwordField.clear();
        passwordField.sendKeys(PASSWORD);
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

    @Test (priority = 4)
    public void fourthTest() {
        WebElement swipeSection = driver.findElementByXPath("//*[@content-desc = 'Swipe']");
        swipeSection.click();
        TouchAction action = new TouchAction(driver);
        WebElement swipeElement = waitElementPresent("//*[@content-desc = 'card']", 5);
        int l_x = swipeElement.getLocation().getX();
        int u_y = swipeElement.getLocation().getY();
        int r_x = l_x + swipeElement.getSize().getWidth();
        int l_y = u_y + swipeElement.getSize().getHeight();
        int m_y = (u_y + l_y)/2;
        while(driver.findElementsByXPath("//*[@text = 'EXTENDABLE']").size() == 0) {
            action
                    .press(PointOption.point(r_x, m_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(PointOption.point(l_x, m_y))
                    .release()
                    .perform();
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
