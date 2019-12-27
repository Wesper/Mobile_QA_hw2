package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver<MobileElement> driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "10.0");
        capabilities.setCapability("deviceName", "Nexus_5X_API_29_x86");
        capabilities.setCapability("appPackage", "");
        capabilities.setCapability("appActivity", "");
        capabilities.setCapability("app", "/Users/artembelanko/IdeaProjects/" +
                "otus.configuration.and.run.tests/src/test/resources/apk/sample.apk");

        URL appiumUrl = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(appiumUrl, capabilities);

    }

    @Test
    public void firstTest(){
        System.out.println("First test run!");
    }

    @Test
    public void secondTest(){
        MobileElement element = driver.findElementByXPath("//*[@text = 'BASIC']");
        element.click();
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
