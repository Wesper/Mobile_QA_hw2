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

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_5X_API_29_x86");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "org.wikipedia");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".main.MainActivity");
        capabilities.setCapability(MobileCapabilityType.APP, "/Users/artembelanko/IdeaProjects/" +
                "otus.configuration.and.run.tests/src/test/resources/apk/wiki.apk");

        URL appiumUrl = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(appiumUrl, capabilities);

    }

    @Test
    public void firstTest(){
        List<MobileElement> skip = driver.findElementsById("org.wikipedia:id/fragment_onboarding_skip_button");
        if(skip.size() > 0){
            skip.get(0).click();
        }
        WebElement search = waitElementPresent(By.id("org.wikipedia:id/search_container"), 5);
        search.click();
        waitElementPresent(By.id("org.wikipedia:id/search_src_text"), 5);
        MobileElement back = driver.findElementByClassName("android.widget.ImageButton");
        back.click();
    }

    @Test
    public void secondTest(){
        WebElement search = waitElementPresent(By.id("org.wikipedia:id/search_container"),100);
        search.click();
        WebElement input = waitElementPresent(By.id("org.wikipedia:id/search_src_text"),10);
        input.sendKeys("Java");
        waitElementPresent("//*[@text = 'Island of Indonesia']", 10);
    }

    @Test
    public void thirdTest(){
        WebElement input = driver.findElementById("org.wikipedia:id/search_src_text");
        input.clear();
        input.sendKeys("Python");
        WebElement python = waitElementPresent("//*[@text = 'Python' and @resource-id = 'org.wikipedia:id/page_list_item_title']", 10);
        python.click();
        String textDescription = waitElementPresent(
                "//*[@resource-id = 'pagelib_edit_section_title_description']",
                10)
                .getText();
        Assert.assertEquals(textDescription,
                "Disambiguation page providing links to topics " +
                        "that could be referred to by the same search term");
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
