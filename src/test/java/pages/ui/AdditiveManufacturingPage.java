package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;

public class AdditiveManufacturingPage extends BasePage {
    private WebDriver driver;

    // Locators (from provided test case context and HTML)
    private By headerAdditiveManufacturing = By.xpath("//h2[contains(text(), 'Additive Manufacturing Standards')]");
    private By linkStandards = By.linkText("standards");
    private By btnLearnMoreASTMCompass = By.linkText("learn more about astm compass");
    private By btnLearnMoreTraining = By.linkText("learn more about training");
    private By linkIndustry = By.linkText("industry");
    private By linkLearnMoreASTMSpecBuilder = By.linkText("Learn More About ASTM SpecBuilder®");
    private By btnICAMConference = By.linkText("icam conference");
    private By btnContactUsFooter = By.linkText("contact us");

    public AdditiveManufacturingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Comprehensive method to validate Additive Manufacturing page load, presence of all links, and navigation for WM-444.
     * This method performs the full user flow as described in the test case.
     *
     * @param baseUrl The URL of the Additive Manufacturing page (e.g., "https://qa-regional.astm.org/emerging-technology/additive-manufacturing")
     */
    public void validatePageLoadLinksAndNavigation(String baseUrl) {
        // Navigate to Additive Manufacturing page
        driver.get(baseUrl);
        WaitStatementUtils.explicitWaitForVisibility(driver, headerAdditiveManufacturing, 10);
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // 1. Verify presence of "standards" link and click
        Assert.assertTrue(driver.findElement(linkStandards).isDisplayed(), "'standards' link not displayed");
        driver.findElement(linkStandards).click();
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.navigate().back();
        WaitStatementUtils.explicitWaitForVisibility(driver, headerAdditiveManufacturing, 10);

        // 2. Verify presence of "learn more about astm compass" button and click
        Assert.assertTrue(driver.findElement(btnLearnMoreASTMCompass).isDisplayed(), "'learn more about astm compass' button not displayed");
        driver.findElement(btnLearnMoreASTMCompass).click();
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.navigate().back();
        WaitStatementUtils.explicitWaitForVisibility(driver, headerAdditiveManufacturing, 10);

        // 3. Verify presence of "learn more about training" button and click
        Assert.assertTrue(driver.findElement(btnLearnMoreTraining).isDisplayed(), "'learn more about training' button not displayed");
        driver.findElement(btnLearnMoreTraining).click();
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.navigate().back();
        WaitStatementUtils.explicitWaitForVisibility(driver, headerAdditiveManufacturing, 10);

        // 4. Verify presence of "industry" link inside ICAM section and click
        Assert.assertTrue(driver.findElement(linkIndustry).isDisplayed(), "'industry' link not displayed");
        driver.findElement(linkIndustry).click();
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.navigate().back();
        WaitStatementUtils.explicitWaitForVisibility(driver, headerAdditiveManufacturing, 10);

        // 5. Verify presence of "Learn More About ASTM SpecBuilder®" link and click
        Assert.assertTrue(driver.findElement(linkLearnMoreASTMSpecBuilder).isDisplayed(), "'Learn More About ASTM SpecBuilder®' link not displayed");
        driver.findElement(linkLearnMoreASTMSpecBuilder).click();
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.navigate().back();
        WaitStatementUtils.explicitWaitForVisibility(driver, headerAdditiveManufacturing, 10);

        // 6. Verify presence of "icam conference" button and click
        Assert.assertTrue(driver.findElement(btnICAMConference).isDisplayed(), "'icam conference' button not displayed");
        driver.findElement(btnICAMConference).click();
        WCMSICommon.waitForSec(2);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        driver.navigate().back();
        WaitStatementUtils.explicitWaitForVisibility(driver, headerAdditiveManufacturing, 10);

        // 7. Verify presence of "contact us" button in footer and click
        Assert.assertTrue(driver.findElement(btnContactUsFooter).isDisplayed(), "'contact us' button in footer not displayed");
        String originalWindow = driver.getWindowHandle();
        driver.findElement(btnContactUsFooter).click();
        WCMSICommon.waitForSec(2);
        // Switch to the new tab if opened
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        String expectedUrl = "https://www.astm.org/standards-and-solutions/enterprise-solutions/salesforce";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("/standards-and-solutions/enterprise-solutions/salesforce"), "User did not navigate to expected Salesforce page. Actual: " + actualUrl);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        // Close the new tab and switch back
        if (!driver.getWindowHandle().equals(originalWindow)) {
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }
}
