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
import java.util.List;

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
    public void testSearchForElementsAndCancel() {
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find element to init search on first page", 5);

        String textToSearch = "USA";

        waitForElementAndSendKeys(By.xpath("//*[contains (@resource-id, 'search_src_text')]"),
                                    "Can't find search input", textToSearch, 5);

        List<WebElement> foundArticles = waitForElementsPresent(By.xpath(
                "//*[contains (@resource-id, 'org.wikipedia:id/page_list_item_title') and " +
                        "contains(@text, '" + textToSearch+ "')]"),
                "No elements found");

        Assert.assertTrue("There are less than 2 elements", foundArticles.size() > 1);

        waitForElementAndClick(By.xpath("//*[contains (@resource-id, 'org.wikipedia:id/search_close_btn')]"),
                "Can't find cancel by X element",
                5);

        waitForElementNotVisible(By.xpath("//*[contains (@resource-id, 'org.wikipedia:id/page_list_item_title') and " +
                                                 "contains(@text, '" + textToSearch+ "')]"),
                "Searched elements still here",
                5);
    }

    @Test
    public void  testCancelSearch(){
        waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can't find element to init search on first page", 5);

        WebElement element_search_to_enter_text = waitForElementPresent(
                By.xpath("//*[contains (@resource-id, 'search_src_text')]"),
                "Can't find search input");
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutSec){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSec);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage){
        return waitForElementPresent(by, errorMessage, 5);
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage, long timeoutSec){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSec);
        wait.withMessage(errorMessage + "\n");
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElements(by);
    }

    private List<WebElement> waitForElementsPresent(By by, String errorMessage){
        return waitForElementsPresent(by, errorMessage, 30);
    }


    private WebElement waitForElementAndClick(By by, String error_message, long timeoutSec){
        WebElement element = waitForElementPresent(by, error_message, timeoutSec);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String error_message, String value, long timeoutSec){
        WebElement element = waitForElementPresent(by, error_message, timeoutSec);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotVisible(By by, String error_message, long timeoutSec){
        WebDriverWait wait = new WebDriverWait(driver, timeoutSec);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    @After
    public void tearDown() {
        driver.quit();
    }


}
