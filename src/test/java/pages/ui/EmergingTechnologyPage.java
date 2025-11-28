package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;

/**
 * Page Object for Emerging Technology page.
 * Covers: Validate Page Load, Presence of All Links & Navigation (WM-111)
 */
public class EmergingTechnologyPage extends BasePage {
    // Locators for all main links and footer Contact Us on the Emerging Technology page
    private final By additiveManufacturingConsultingServicesLink = By.linkText("Additive Manufacturing Consulting Services");
    private final By additiveManufacturingLink = By.linkText("Additive Manufacturing");
    private final By exoTechnologyLink = By.linkText("Exo Technology");
    private final By uasStandardsLink = By.linkText("UAS Standards");
    private final By roboticsAutomationLink = By.linkText("Robotics & Automation");
    private final By contactUsLink = By.linkText("Contact Us"); // Main page Contact Us
    private final By footerContactUsLink = By.xpath("//div[contains(@class,'infoBanner_infoCard__6QxxK')]//a[text()='Contact Us']"); // Footer Contact Us

    public EmergingTechnologyPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Comprehensive method for WM-111: Validate page load, all links presence, and navigation.
     * Steps:
     *  - Loads the page
     *  - Verifies all main links are displayed and clickable
     *  - Clicks each link and verifies navigation (by URL or title)
     *  - Verifies footer Contact Us link
     *
     * @param baseUrl The Emerging Technology page URL
     */
    public void validatePageLoadAndAllLinksNavigation(String baseUrl) {
        driver.get(baseUrl);
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Verify and click Additive Manufacturing Consulting Services link
        verifyAndNavigate(additiveManufacturingConsultingServicesLink, "/emerging-technology/additive-manufacturing-consulting-services");

        // Go back to the main page
        driver.navigate().back();
        WCMSICommon.waitForSec(2);

        // Verify and click Additive Manufacturing link
        verifyAndNavigate(additiveManufacturingLink, "/emerging-technology/additive-manufacturing");
        driver.navigate().back();
        WCMSICommon.waitForSec(2);

        // Verify and click Exo Technology link
        verifyAndNavigate(exoTechnologyLink, "/emerging-technology/exo-technology");
        driver.navigate().back();
        WCMSICommon.waitForSec(2);

        // Verify and click UAS Standards link
        verifyAndNavigate(uasStandardsLink, "/emerging-technology/uas-standards");
        driver.navigate().back();
        WCMSICommon.waitForSec(2);

        // Verify and click Robotics & Automation link
        verifyAndNavigate(roboticsAutomationLink, "/emerging-technology/robotics-automation");
        driver.navigate().back();
        WCMSICommon.waitForSec(2);

        // Verify and click Contact Us link (main page section)
        verifyAndNavigate(contactUsLink, "/contact-us");
        driver.navigate().back();
        WCMSICommon.waitForSec(2);

        // Verify and click Contact Us link in footer (external URL)
        verifyAndNavigate(footerContactUsLink, "https://www.astm.org/standards-and-solutions/enterprise-solutions/salesforce");
        // No need to navigate back as this opens in a new tab/window
    }

    // Helper method to verify link is displayed, clickable, and navigates to expected URL (relative or absolute)
    private void verifyAndNavigate(By linkLocator, String expectedUrlOrPath) {
        WaitStatementUtils.explicitWaitForVisibility(driver, getElement(linkLocator), 10);
        WebElement link = getElement(linkLocator);
        ReusableMethods.scrollToElement(driver, linkLocator);
        if (!link.isDisplayed() || !link.isEnabled()) {
            throw new AssertionError("Link not displayed or not enabled: " + linkLocator.toString());
        }
        String originalWindow = driver.getWindowHandle();
        link.click();
        WCMSICommon.waitForSec(3);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        // If link opens in new tab/window (footer Contact Us), handle window switch
        if (expectedUrlOrPath.startsWith("http")) {
            for (String winHandle : driver.getWindowHandles()) {
                if (!winHandle.equals(originalWindow)) {
                    driver.switchTo().window(winHandle);
                    break;
                }
            }
            String actualUrl = driver.getCurrentUrl();
            if (!actualUrl.startsWith(expectedUrlOrPath)) {
                throw new AssertionError("Navigation failed. Expected: " + expectedUrlOrPath + ", Actual: " + actualUrl);
            }
            driver.close();
            driver.switchTo().window(originalWindow);
        } else {
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains(expectedUrlOrPath)) {
                throw new AssertionError("Navigation failed. Expected path: " + expectedUrlOrPath + ", Actual: " + currentUrl);
            }
        }
    }
}
