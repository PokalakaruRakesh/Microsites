package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;
import io.qameta.allure.Step;
import org.testng.Assert;

public class EmergingTechnologyExoTechnologyPage extends BasePage {
    private WebDriver driver;

    // Locators from test case context
    private By exoTechnologyTitle = By.xpath("//h2[contains(@class,'infoBanner_title__') and contains(text(),'Exo Technology')]");
    private By exoTechnologyStandardsImage = By.xpath("//img[contains(@alt,'xoesqueletos-normas-mejores-practicas.jpg')]");
    private By impactingExoTechnologyDevelopmentImage = By.xpath("//img[contains(@alt,'robotica-ingenieria-lineamientos.jpg')]");
    private By internationalExoTechnologyCenterOfExcellenceImage = By.xpath("//img[contains(@alt,'exoesqueletos-requisitos-fabricacion.jpg')]");
    private By contactUsButton = By.xpath("//a[contains(@class,'btn astm-btn btn-primary') and text()='Contact Us']");
    private By emailLink = By.xpath("//a[contains(@href,'mailto:sales@astm.org')]");

    // Constructor
    public EmergingTechnologyExoTechnologyPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Comprehensive method to validate page load, presence of all links, and navigation as per WM-222
     * @param expectedUrl The URL to load for the Exo Technology page
     * @param contactUsExpectedTitle The expected title after navigating to Contact Us
     */
    @Step("Validate Exo Technology page load, presence of all links, and Contact Us navigation")
    public void validatePageLoadLinksAndNavigation(String expectedUrl, String contactUsExpectedTitle) {
        try {
            // Step 1: Navigate to the Exo Technology page
            driver.get(expectedUrl);
            WaitStatementUtils.explicitWaitForVisibility(driver, exoTechnologyTitle, 10);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            Assert.assertTrue(driver.findElement(exoTechnologyTitle).isDisplayed(), "Exo Technology title not displayed");

            // Step 2: Scroll through the page and verify presence of all key images/sections
            ReusableMethods.scrollToElement(driver, exoTechnologyStandardsImage);
            Assert.assertTrue(driver.findElement(exoTechnologyStandardsImage).isDisplayed(), "Exo Technology Standards image not displayed");
            ReusableMethods.scrollToElement(driver, impactingExoTechnologyDevelopmentImage);
            Assert.assertTrue(driver.findElement(impactingExoTechnologyDevelopmentImage).isDisplayed(), "Impacting Exo Technology Development image not displayed");
            ReusableMethods.scrollToElement(driver, internationalExoTechnologyCenterOfExcellenceImage);
            Assert.assertTrue(driver.findElement(internationalExoTechnologyCenterOfExcellenceImage).isDisplayed(), "International Exo Technology Center of Excellence image not displayed");
            ScreenshotUtil.takeScreenshotForAllure(driver);

            // Step 3: Verify presence of "Contact Us" link in footer
            ReusableMethods.scrollToElement(driver, contactUsButton);
            Assert.assertTrue(driver.findElement(contactUsButton).isDisplayed(), "Contact Us button not displayed in footer");
            Assert.assertEquals(driver.findElement(contactUsButton).getText().trim(), "Contact Us", "Contact Us button text mismatch");
            ScreenshotUtil.takeScreenshotForAllure(driver);

            // Step 4: Click "Contact Us" and verify navigation
            driver.findElement(contactUsButton).click();
            WCMSICommon.waitForSec(4);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            Assert.assertTrue(driver.getTitle().contains(contactUsExpectedTitle), "Did not navigate to Contact Us page. Actual title: " + driver.getTitle());
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshotForAllure(driver);
            Assert.fail("Validation failed for Exo Technology page load, links, and navigation: " + e.getMessage());
        }
    }
}
