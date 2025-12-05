package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import java.util.List;

/**
 * Page Object for Additive Manufacturing Page (Test Case: WM-444)
 * Handles: Page load, link presence, and navigation validation
 */
public class AdditiveManufacturingPage {
    private WebDriver driver;

    // Locators (use actual locators where available, placeholders otherwise)
    private By standardsLink = By.xpath("//a[contains(text(),'standards')]"); // TODO: Replace with actual locator if different
    private By learnMoreAboutASTMCompassButton = By.xpath("//a[contains(text(),'learn more about astm compass') or contains(text(),'Learn More About ASTM Compass')]"); // TODO: Replace with actual locator if different
    private By learnMoreAboutTrainingButton = By.xpath("//a[contains(text(),'learn more about training') or contains(text(),'Learn More About Training')]"); // TODO: Replace with actual locator if different
    private By industryLinkInICAMSection = By.xpath("//a[contains(text(),'industry')]"); // TODO: Replace with actual locator if different
    private By learnMoreAboutASTMSpecBuilderLink = By.xpath("//a[contains(text(),'Learn More About ASTM SpecBuilder')]"); // TODO: Replace with actual locator if different
    private By icamConferenceButton = By.xpath("//a[contains(text(),'icam conference') or contains(text(),'ICAM Conference')]"); // TODO: Replace with actual locator if different
    private By contactUsButtonFooter = By.xpath("//a[@href='https://www.astm.org/standards-and-solutions/enterprise-solutions/salesforce' and contains(text(),'contact us')]"); // Provided locator

    // Constructor
    public AdditiveManufacturingPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Comprehensive method to validate Additive Manufacturing page load, all links presence and navigation.
     * Steps:
     *  - Loads the page
     *  - Verifies presence of all required links/buttons
     *  - Clicks each, verifies navigation, and returns
     *  - Verifies final navigation to Contact Us
     *
     * @param url The Additive Manufacturing page URL
     * @param expectedContactUsUrl The expected Contact Us destination URL
     */
    public void validatePageLoadLinksAndNavigation(String url, String expectedContactUsUrl) {
        driver.get(url);
        WaitStatementUtils.waitForPageToLoad(driver, 15);

        // Scroll to bottom to ensure all elements are loaded
        ReusableMethods.scrollToBottom(driver);

        // 1. Verify presence of "standards" link
        Assert.assertTrue(isElementPresent(standardsLink), "Standards link is not present");
        // Click and verify navigation, then return
        clickAndReturn(standardsLink);

        // 2. Verify presence of "learn more about astm compass" button
        Assert.assertTrue(isElementPresent(learnMoreAboutASTMCompassButton), "Learn More About ASTM Compass button is not present");
        clickAndReturn(learnMoreAboutASTMCompassButton);

        // 3. Verify presence of "learn more about training" button
        Assert.assertTrue(isElementPresent(learnMoreAboutTrainingButton), "Learn More About Training button is not present");
        clickAndReturn(learnMoreAboutTrainingButton);

        // 4. Verify presence of "industry" link inside ICAM section
        Assert.assertTrue(isElementPresent(industryLinkInICAMSection), "Industry link in ICAM section is not present");
        clickAndReturn(industryLinkInICAMSection);

        // 5. Verify presence of "Learn More About ASTM SpecBuilder®" link
        Assert.assertTrue(isElementPresent(learnMoreAboutASTMSpecBuilderLink), "Learn More About ASTM SpecBuilder link is not present");
        clickAndReturn(learnMoreAboutASTMSpecBuilderLink);

        // 6. Verify presence of "icam conference" button
        Assert.assertTrue(isElementPresent(icamConferenceButton), "ICAM Conference button is not present");
        clickAndReturn(icamConferenceButton);

        // 7. Verify presence of "contact us" button in footer and click
        Assert.assertTrue(isElementPresent(contactUsButtonFooter), "Contact Us button in footer is not present");
        String originalWindow = driver.getWindowHandle();
        driver.findElement(contactUsButtonFooter).click();
        WaitStatementUtils.waitForNewWindowAndSwitchToIt(driver, originalWindow, 10);
        WaitStatementUtils.waitForPageToLoad(driver, 10);
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedContactUsUrl), "Contact Us navigation failed. Expected URL containing: " + expectedContactUsUrl + ", but got: " + actualUrl);
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    // Helper: Checks if element is present
    private boolean isElementPresent(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return elements != null && !elements.isEmpty() && elements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Helper: Click link/button, wait for navigation, then return to original page
    private void clickAndReturn(By locator) {
        String originalWindow = driver.getWindowHandle();
        WebElement element = driver.findElement(locator);
        String href = element.getAttribute("href");
        element.click();
        // If link opens in new tab
        if (href != null && !href.isEmpty() && !driver.getCurrentUrl().equals(href)) {
            WaitStatementUtils.waitForNewWindowAndSwitchToIt(driver, originalWindow, 10);
            WaitStatementUtils.waitForPageToLoad(driver, 10);
            driver.close();
            driver.switchTo().window(originalWindow);
        } else {
            WaitStatementUtils.waitForPageToLoad(driver, 10);
            driver.navigate().back();
            WaitStatementUtils.waitForPageToLoad(driver, 10);
        }
    }
}
