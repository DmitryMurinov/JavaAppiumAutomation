import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/Study/MobileAutomation/Lesson1/JavaAppiumAutomation" +
                "/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void testSearchPlaceholder() {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find element to init search on first page", 5);

        WebElement element_search_to_enter_text = waitForElementPresent(
                                                By.xpath("//*[contains (@resource-id, 'search_src_text')]"),
                                            "Can't find search input");

        Assert.assertEquals("Unexpected search placeholder text: ",
                                                        "Search…", element_search_to_enter_text.getText());
    }

    private WebElement waitElementForPresent(By by, String errorMessage, long timeoutSec){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSec);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage){
        return waitElementForPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutSec){
        WebElement element = waitElementForPresent(by, error_message, timeoutSec);
        element.click();
        return element;
    }

    @After
    public void tearDown() {
        driver.quit();
    }


}
