package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;
import org.testng.Assert;

public class RoboticsAndAutomationPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By roboticsAutomationTitle = By.xpath("//h2[text()='Robotics & Automation']");
    private By artificialIntelligenceLink = By.linkText("artificial intelligence");
    private By bookAMeetingButton = By.linkText("Book A Meeting With ASTM International");
    private By astmCompassButton = By.linkText("ASTM Compass");
    private By forMoreInformationTitle = By.xpath("//h2[text()='For More Information']");
    private By contactUsButton = By.linkText("Contact Us");
    private By emailLink = By.xpath("//a[@href='mailto:sales@astm.org']");

    // Constructor
    public RoboticsAndAutomationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Comprehensive method for WM-333: Validate Page Load, Presence of All Links & Navigation
     * Steps:
     * 1. Loads the Robotics & Automation page
     * 2. Verifies presence of all required links
     * 3. Clicks each link and validates navigation
     * 4. Returns to the original page after each navigation
     *
     * @param url The URL of the Robotics & Automation page
     */
    public void validateRoboticsAndAutomationPageLoadAndLinksNavigation(String url) {
        // Step 1: Launch the browser and navigate to the Robotics & Automation page
        driver.get(url);
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Step 2: Verify page title/header
        Assert.assertTrue(isElementDisplayed(roboticsAutomationTitle), "Robotics & Automation title not displayed");

        // Step 3: Verify presence of "artificial intelligence" link
        Assert.assertTrue(isElementDisplayed(artificialIntelligenceLink), "artificial intelligence link not displayed");
        // Step 4: Click "artificial intelligence" and verify navigation (opens in new tab)
        validateLinkOpensInNewTabAndReturn(artificialIntelligenceLink, "swiftoscialai.com");

        // Step 5: Verify presence of "Book A Meeting With ASTM International" link
        Assert.assertTrue(isElementDisplayed(bookAMeetingButton), "Book A Meeting With ASTM International link not displayed");
        // Step 6: Click "Book A Meeting With ASTM International" and verify navigation (opens in new tab)
        validateLinkOpensInNewTabAndReturn(bookAMeetingButton, "outlook.office365.com/book/MeetwithanASTMLatinAmericaRepresentativetoday@astm.org");

        // Step 7: Verify presence of "ASTM Compass" link
        Assert.assertTrue(isElementDisplayed(astmCompassButton), "ASTM Compass link not displayed");
        // Step 8: Click "ASTM Compass" and verify navigation (same tab)
        validateLinkNavigation(astmCompassButton, "/standards/compass");

        // Step 9: Verify "For More Information" section and links
        Assert.assertTrue(isElementDisplayed(forMoreInformationTitle), "For More Information title not displayed");
        Assert.assertTrue(isElementDisplayed(contactUsButton), "Contact Us button not displayed");
        Assert.assertTrue(isElementDisplayed(emailLink), "Email link not displayed");
    }

    // Helper: Check if element is displayed
    private boolean isElementDisplayed(By locator) {
        try {
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(locator), 10);
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Helper: Click link that opens in new tab, switch, validate URL, close tab, return
    private void validateLinkOpensInNewTabAndReturn(By locator, String expectedPartialUrl) {
        String originalWindow = driver.getWindowHandle();
        int beforeTabs = driver.getWindowHandles().size();
        driver.findElement(locator).click();
        WCMSICommon.waitForSec(3);
        // Switch to new tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedPartialUrl), "Navigation failed. Expected URL to contain: " + expectedPartialUrl + ", but got: " + currentUrl);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.close();
        driver.switchTo().window(originalWindow);
        WCMSICommon.waitForSec(1);
        Assert.assertEquals(driver.getWindowHandles().size(), beforeTabs, "Tab count did not return to original after closing new tab.");
    }

    // Helper: Click link that navigates in same tab and return
    private void validateLinkNavigation(By locator, String expectedPartialUrl) {
        driver.findElement(locator).click();
        WCMSICommon.waitForSec(3);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedPartialUrl), "Navigation failed. Expected URL to contain: " + expectedPartialUrl + ", but got: " + currentUrl);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.navigate().back();
        WCMSICommon.waitForSec(2);
    }
}
