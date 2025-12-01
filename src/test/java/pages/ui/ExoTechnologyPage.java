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

public class ExoTechnologyPage extends BasePage {

    public ExoTechnologyPage(WebDriver driver) {
        super(driver);
    }

    // Locators (from provided test case context and extracted HTML)
    private By headerBanner = By.cssSelector(".infoBanner_headerBanner__1iIRP");
    private By exoTechnologyTitle = By.cssSelector("h2.infoBanner_title__9abIf");
    private By exoTechnologyStandardsImage = By.cssSelector("img[alt='xoesqueletos-normas-mejores-practicas.jpg']");
    private By impactingExoTechImage = By.cssSelector("img[alt='robotica-ingenieria-lineamientos.jpg']");
    private By internationalExoTechImage = By.cssSelector("img[alt='exoesqueletos-requisitos-fabricacion.jpg']");
    private By contactUsButton = By.linkText("Contact Us");
    private By footerEmailLink = By.cssSelector("a[href='mailto:sales@astm.org']");

    // Comprehensive method for WM-222: Validate Page Load, Presence of All Links & Navigation
    public void validatePageLoadLinksAndNavigation() {
        // Step 1: Validate page loads and header/banner is visible
        Assert.assertTrue(isElementDisplayed(headerBanner), "Header banner is not displayed");
        Assert.assertTrue(isElementDisplayed(exoTechnologyTitle), "Exo Technology title is not displayed");
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Step 2: Scroll through the page and validate presence of key images/sections
        Assert.assertTrue(isElementDisplayed(exoTechnologyStandardsImage), "Exo Technology Standards image not present");
        Assert.assertTrue(isElementDisplayed(impactingExoTechImage), "ASTM Impacting Exo-Technology Development image not present");
        Assert.assertTrue(isElementDisplayed(internationalExoTechImage), "International Exo Technology Center image not present");
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Step 3: Validate presence of Contact Us link in footer
        Assert.assertTrue(isElementDisplayed(contactUsButton), "Contact Us button is not displayed in footer");
        Assert.assertTrue(isElementDisplayed(footerEmailLink), "Footer email link is not displayed");
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Step 4: Click Contact Us and validate navigation
        String originalWindow = driver.getWindowHandle();
        WebElement contactUs = getElement(contactUsButton);
        ReusableMethods.scrollIntoView(contactUs, driver);
        WaitStatementUtils.waitForElementToBeClickable(driver, contactUs, 5);
        contactUs.click();
        WCMSICommon.waitForSec(2);
        // Switch to new tab if opened
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("salesforce"), "Contact Us navigation failed. Current URL: " + currentUrl);
        ScreenshotUtil.takeScreenshotForAllure(driver);
        // Close new tab if opened
        if (!driver.getWindowHandle().equals(originalWindow)) {
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    // Helper method for element visibility
    private boolean isElementDisplayed(By locator) {
        try {
            WebElement el = getElement(locator);
            return el != null && el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
