package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.ReusableMethods;
import base.utils.WaitStatementUtils;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;
import org.testng.Assert;

/**
 * Page Object for Emerging Technology Page - covers WM-111: Validate Page Load, Link Visibility & Navigation
 */
public class EmergingTechnologyPage extends BasePage {
    // Locators for the main links/buttons as per provided test case and available locators
    private By additiveManufacturingConsultingServicesBtn = By.linkText("Additive Manufacturing Consulting Services");
    private By additiveManufacturingBtn = By.linkText("Additive Manufacturing");
    private By exoTechnologyBtn = By.linkText("Exo Technology");
    private By uasStandardsBtn = By.linkText("UAS Standards");
    private By roboticsAutomationBtn = By.linkText("Robotics & Automation");
    private By contactUsFooterBtn = By.xpath("//a[text()='Contact Us' and contains(@class,'btn-primary')]"); // Footer CTA
    
    // Constructor
    public EmergingTechnologyPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Comprehensive method to validate page load, link visibility, clickability, and navigation for all major links on the Emerging Technology page.
     * Steps:
     *  - Loads the page
     *  - Scrolls through all sections
     *  - Verifies each link is visible, clickable, and navigates correctly
     *  - Returns to the main page after each navigation
     *  - Verifies the Contact Us link in the footer navigates to the Contact Us page
     *
     * @param pageUrl The URL of the Emerging Technology page
     * @param expectedContactUsTitle The expected title of the Contact Us page for validation
     */
    public void validatePageLoadAndNavigation(String pageUrl, String expectedContactUsTitle) {
        driver.get(pageUrl);
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Validate Additive Manufacturing Consulting Services link
        validateLinkNavigation(additiveManufacturingConsultingServicesBtn, "/emerging-technology/additive-manufacturing-consulting-services");

        // Validate Additive Manufacturing link
        validateLinkNavigation(additiveManufacturingBtn, "/emerging-technology/additive-manufacturing");

        // Validate Exo Technology link
        validateLinkNavigation(exoTechnologyBtn, "/emerging-technology/exo-technology");

        // Validate UAS Standards link
        validateLinkNavigation(uasStandardsBtn, "/emerging-technology/uas-standards");

        // Validate Robotics & Automation link
        validateLinkNavigation(roboticsAutomationBtn, "/emerging-technology/robotics-automation");

        // Validate Contact Us link in the footer
        validateContactUsFooterNavigation(contactUsFooterBtn, expectedContactUsTitle);
    }

    // Helper method to validate link visibility, clickability, and navigation
    private void validateLinkNavigation(By locator, String expectedUrlFragment) {
        try {
            WebElement link = getElement(locator);
            ReusableMethods.scrollToElement(driver, locator);
            WaitStatementUtils.waitForElementToBeClickable(driver, link);
            Assert.assertTrue(link.isDisplayed(), "Link is not displayed: " + locator.toString());
            ScreenshotUtil.takeScreenshotForAllure(driver);
            String originalUrl = driver.getCurrentUrl();
            link.click();
            WCMSICommon.waitForSec(3);
            Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrlFragment), "Navigation failed for link: " + expectedUrlFragment);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            driver.navigate().back();
            WCMSICommon.waitForSec(2);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception during link navigation validation: " + locator.toString());
        }
    }

    // Helper method for Contact Us footer link (expected to go to Contact Us page)
    private void validateContactUsFooterNavigation(By locator, String expectedTitle) {
        try {
            WebElement link = getElement(locator);
            ReusableMethods.scrollToElement(driver, locator);
            WaitStatementUtils.waitForElementToBeClickable(driver, link);
            Assert.assertTrue(link.isDisplayed(), "Footer Contact Us link is not displayed.");
            ScreenshotUtil.takeScreenshotForAllure(driver);
            link.click();
            WCMSICommon.waitForSec(3);
            Assert.assertTrue(driver.getTitle().contains(expectedTitle), "Footer Contact Us did not navigate to expected page. Actual title: " + driver.getTitle());
            ScreenshotUtil.takeScreenshotForAllure(driver);
            driver.navigate().back();
            WCMSICommon.waitForSec(2);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception during Contact Us footer navigation validation.");
        }
    }
}
