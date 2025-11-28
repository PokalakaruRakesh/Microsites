package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;
import org.testng.Assert;

public class EmergingTechnologyPage extends BasePage {
    public EmergingTechnologyPage(WebDriver driver) {
        super(driver);
    }

    // Locators for main links/buttons on the Emerging Technology page
    private By additiveManufacturingConsultingServicesButton = By.xpath("//a[text()='Additive Manufacturing Consulting Services']");
    private By additiveManufacturingButton = By.xpath("//a[text()='Additive Manufacturing']");
    private By exoTechnologyButton = By.xpath("//a[text()='Exo Technology']");
    private By uasStandardsButton = By.xpath("//a[text()='UAS Standards']");
    private By roboticsAutomationButton = By.xpath("//a[text()='Robotics & Automation']");
    private By contactUsFooterButton = By.xpath("//a[text()='Contact Us' and contains(@class, 'btn')]");

    // Comprehensive method to validate page load, presence of all links, and navigation
    public void validateEmergingTechnologyPageLinksAndNavigation() {
        try {
            // Wait for the page to load by checking the presence of the main header
            WaitStatementUtils.explicitWaitForVisibility(driver, getElement(By.xpath("//h2[contains(text(),'Emerging Technology')]")), 10);
            ScreenshotUtil.takeScreenshotForAllure(driver);

            // Validate "Additive Manufacturing Consulting Services" link
            Assert.assertTrue(isElementDisplayed(additiveManufacturingConsultingServicesButton), "Additive Manufacturing Consulting Services link not displayed");
            validateLinkNavigation(additiveManufacturingConsultingServicesButton, "/emerging-technology/additive-manufacturing-consulting-services");

            // Validate "Additive Manufacturing" link
            Assert.assertTrue(isElementDisplayed(additiveManufacturingButton), "Additive Manufacturing link not displayed");
            validateLinkNavigation(additiveManufacturingButton, "/emerging-technology/additive-manufacturing");

            // Validate "Exo Technology" link
            Assert.assertTrue(isElementDisplayed(exoTechnologyButton), "Exo Technology link not displayed");
            validateLinkNavigation(exoTechnologyButton, "/emerging-technology/exo-technology");

            // Validate "UAS Standards" link
            Assert.assertTrue(isElementDisplayed(uasStandardsButton), "UAS Standards link not displayed");
            validateLinkNavigation(uasStandardsButton, "/emerging-technology/uas-standards");

            // Validate "Robotics & Automation" link
            Assert.assertTrue(isElementDisplayed(roboticsAutomationButton), "Robotics & Automation link not displayed");
            validateLinkNavigation(roboticsAutomationButton, "/emerging-technology/robotics-automation");

            // Validate "Contact Us" link in footer
            Assert.assertTrue(isElementDisplayed(contactUsFooterButton), "Contact Us link in footer not displayed");
            validateLinkNavigation(contactUsFooterButton, "/contact-us");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception in validating Emerging Technology page links and navigation: " + e.getMessage());
        }
    }

    // Helper method to check if an element is displayed
    private boolean isElementDisplayed(By locator) {
        try {
            WebElement element = getElement(locator);
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Helper method to validate navigation for a link (click and verify URL)
    private void validateLinkNavigation(By locator, String expectedPartialUrl) {
        try {
            WebElement linkElement = getElement(locator);
            ReusableMethods.scrollToElement(driver, locator);
            String originalUrl = driver.getCurrentUrl();
            WaitStatementUtils.waitForElementToBeClickable(driver, linkElement);
            linkElement.click();
            WCMSICommon.waitForSec(3);
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains(expectedPartialUrl), "Navigation failed for link. Expected partial URL: " + expectedPartialUrl + ", but got: " + currentUrl);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            driver.navigate().back();
            WCMSICommon.waitForSec(2);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception during link navigation validation: " + e.getMessage());
        }
    }
}
