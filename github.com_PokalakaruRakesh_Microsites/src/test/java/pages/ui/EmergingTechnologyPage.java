package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;
import org.testng.Assert;
import java.util.List;
import java.util.Set;

/**
 * Page Object for Emerging Technology page.
 * Covers: Validate Page Load, Presence of All Links & Navigation (WM-222)
 * Follows existing project conventions and patterns.
 */
public class EmergingTechnologyPage extends BasePage {

    // Locators (as per provided locators and page analysis)
    private By headerSection = By.xpath("//h2[contains(text(),'Emerging Technology')]");
    private By contactUsButton = By.linkText("Contact Us");
    private By additiveManufacturingButton = By.linkText("Additive Manufacturing");
    private By additiveManufacturingConsultingButton = By.linkText("Additive Manufacturing Consulting Services");
    private By exoTechnologyButton = By.linkText("Exo Technology");
    private By uasStandardsButton = By.linkText("UAS Standards");
    private By roboticsAutomationButton = By.linkText("Robotics & Automation");
    private By footerContactUsButton = By.xpath("//div[contains(@class,'infoBanner_footerBanner__UzP8l')]//a[contains(text(),'Contact Us')]");
    private By footerEmailLink = By.xpath("//a[@href='mailto:sales@astm.org']");
    private By emergingTechSections = By.xpath("//h3");
    private By allPageLinks = By.xpath("//a[not(contains(@href,'#')) and not(contains(@href,'javascript'))]");

    public EmergingTechnologyPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Comprehensive method for WM-222:
     * Validates page load, presence of all main links, and navigation for each link.
     * - Scrolls through the page
     * - Verifies presence of key links
     * - Clicks each link and verifies navigation
     * - Specifically verifies Contact Us navigation
     */
    public void validateEmergingTechnologyPageLinksAndNavigation() {
        // Wait for header to be visible (page load)
        WaitStatementUtils.explicitWaitForVisibility(driver, getElement(headerSection), 10);
        Assert.assertTrue(getElement(headerSection).isDisplayed(), "Emerging Technology header not displayed");
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Scroll through sections and verify presence of key sections/links
        List<WebElement> sections = driver.findElements(emergingTechSections);
        Assert.assertTrue(sections.size() > 0, "No section headers found on Emerging Technology page");
        for (WebElement section : sections) {
            ReusableMethods.scrollIntoView(section, driver);
            Assert.assertTrue(section.isDisplayed(), "Section header not visible: " + section.getText());
        }
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Verify presence of all main navigation buttons/links
        Assert.assertTrue(isElementDisplayed(contactUsButton), "Contact Us button not displayed");
        Assert.assertTrue(isElementDisplayed(additiveManufacturingButton), "Additive Manufacturing button not displayed");
        Assert.assertTrue(isElementDisplayed(additiveManufacturingConsultingButton), "Additive Manufacturing Consulting Services button not displayed");
        Assert.assertTrue(isElementDisplayed(exoTechnologyButton), "Exo Technology button not displayed");
        Assert.assertTrue(isElementDisplayed(uasStandardsButton), "UAS Standards button not displayed");
        Assert.assertTrue(isElementDisplayed(roboticsAutomationButton), "Robotics & Automation button not displayed");
        Assert.assertTrue(isElementDisplayed(footerContactUsButton), "Footer Contact Us button not displayed");
        Assert.assertTrue(isElementDisplayed(footerEmailLink), "Footer email link not displayed");

        // Validate navigation for each main link (partial URL check)
        validateLinkNavigation(contactUsButton, "/contact-us");
        validateLinkNavigation(additiveManufacturingButton, "/emerging-technology/additive-manufacturing");
        validateLinkNavigation(additiveManufacturingConsultingButton, "/emerging-technology/additive-manufacturing-consulting-services");
        validateLinkNavigation(exoTechnologyButton, "/emerging-technology/exo-technology");
        validateLinkNavigation(uasStandardsButton, "/emerging-technology/uas-standards");
        validateLinkNavigation(roboticsAutomationButton, "/emerging-technology/robotics-automation");
        validateLinkNavigation(footerContactUsButton, "enterprise-solutions/salesforce"); // External link

        // Validate footer email link opens mailto
        WebElement emailLink = getElement(footerEmailLink);
        Assert.assertEquals(emailLink.getAttribute("href"), "mailto:sales@astm.org", "Footer email link incorrect");

        // Optionally: Validate all links on the page do not lead to 404 (basic check)
        List<WebElement> links = driver.findElements(allPageLinks);
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href == null || href.isEmpty() || href.startsWith("mailto:")) {
                continue;
            }
            // Only check links that open in the same tab
            if (!"_blank".equals(link.getAttribute("target"))) {
                try {
                    String originalUrl = driver.getCurrentUrl();
                    ReusableMethods.scrollIntoView(link, driver);
                    link.click();
                    WCMSICommon.waitForSec(2);
                    String newUrl = driver.getCurrentUrl();
                    String title = driver.getTitle();
                    Assert.assertFalse(title.contains("404") || newUrl.contains("404"), "Broken link: " + href);
                    driver.navigate().back();
                    WaitStatementUtils.explicitWaitForVisibility(driver, getElement(headerSection), 10);
                } catch (Exception e) {
                    ScreenshotUtil.takeScreenshotForAllure(driver);
                    Assert.fail("Exception while validating link: " + href + " - " + e.getMessage());
                }
            }
        }
    }

    // Helper: check if element is displayed
    private boolean isElementDisplayed(By locator) {
        try {
            WebElement el = getElement(locator);
            return el != null && el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Helper: click link and verify navigation by partial URL
    private void validateLinkNavigation(By locator, String expectedPartialUrl) {
        try {
            WebElement link = getElement(locator);
            String originalWindow = driver.getWindowHandle();
            Set<String> windowsBefore = driver.getWindowHandles();
            ReusableMethods.scrollIntoView(link, driver);
            WaitStatementUtils.waitForElementToBeClickable(driver, link, 5);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            link.click();
            WCMSICommon.waitForSec(2);
            Set<String> windowsAfter = driver.getWindowHandles();
            if (windowsAfter.size() > windowsBefore.size()) {
                // New tab opened
                windowsAfter.removeAll(windowsBefore);
                String newWindow = windowsAfter.iterator().next();
                driver.switchTo().window(newWindow);
                String url = driver.getCurrentUrl();
                Assert.assertTrue(url.contains(expectedPartialUrl), "Navigation failed for link: " + expectedPartialUrl);
                driver.close();
                driver.switchTo().window(originalWindow);
            } else {
                // Same tab
                String url = driver.getCurrentUrl();
                Assert.assertTrue(url.contains(expectedPartialUrl), "Navigation failed for link: " + expectedPartialUrl);
                driver.navigate().back();
                WaitStatementUtils.explicitWaitForVisibility(driver, getElement(headerSection), 10);
            }
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshotForAllure(driver);
            Assert.fail("Navigation validation failed for locator: " + locator.toString() + " - " + e.getMessage());
        }
    }
}
