package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;

public class CertifiedProductsPage extends BasePage {
    public CertifiedProductsPage(WebDriver driver) {
        super(driver);
    }

    // Locators for SEI forms and related links on Certified Products Page
    private By manufacturersContactListLink = By.linkText("Manufacturers contact list");
    private By nocsaeLegacyProductListLink = By.linkText("Product List");
    private By listOfStandardsLink = By.linkText("List of Standards");
    private By contactSEILink = By.linkText("Contact SEI");

    // Comprehensive method to verify SEI forms are accessible from Certified Products Page
    public void verifySEIFormsAreAccessible() {
        try {
            // Verify Manufacturers Contact List link is present and clickable
            WaitStatementUtils.explicitWaitForVisibility(driver, getElement(manufacturersContactListLink), 10);
            Assert.assertTrue(getElement(manufacturersContactListLink).isDisplayed(), "Manufacturers Contact List link is not displayed");
            ScreenshotUtil.takeScreenshotForAllure(driver);
            getElement(manufacturersContactListLink).click();
            WCMSICommon.waitForSec(2);
            driver.navigate().back();
            WCMSICommon.waitForSec(2);

            // Verify NOCSAE Legacy Product List link is present and clickable
            WaitStatementUtils.explicitWaitForVisibility(driver, getElement(nocsaeLegacyProductListLink), 10);
            Assert.assertTrue(getElement(nocsaeLegacyProductListLink).isDisplayed(), "NOCSAE Legacy Product List link is not displayed");
            ScreenshotUtil.takeScreenshotForAllure(driver);
            getElement(nocsaeLegacyProductListLink).click();
            WCMSICommon.waitForSec(2);
            driver.navigate().back();
            WCMSICommon.waitForSec(2);

            // Verify List of Standards link is present and clickable
            WaitStatementUtils.explicitWaitForVisibility(driver, getElement(listOfStandardsLink), 10);
            Assert.assertTrue(getElement(listOfStandardsLink).isDisplayed(), "List of Standards link is not displayed");
            ScreenshotUtil.takeScreenshotForAllure(driver);
            getElement(listOfStandardsLink).click();
            WCMSICommon.waitForSec(2);
            // If PDF opens in new tab, handle tab switch and close
            String originalWindow = driver.getWindowHandle();
            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            ScreenshotUtil.takeScreenshotForAllure(driver);
            driver.close();
            driver.switchTo().window(originalWindow);
            WCMSICommon.waitForSec(2);

            // Verify Contact SEI link is present and clickable
            WaitStatementUtils.explicitWaitForVisibility(driver, getElement(contactSEILink), 10);
            Assert.assertTrue(getElement(contactSEILink).isDisplayed(), "Contact SEI link is not displayed");
            ScreenshotUtil.takeScreenshotForAllure(driver);
            getElement(contactSEILink).click();
            WCMSICommon.waitForSec(2);
            driver.navigate().back();
            WCMSICommon.waitForSec(2);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("SEI forms or links are not accessible as expected: " + e.getMessage());
        }
    }
}
