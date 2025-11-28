package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.ReusableMethods;
import base.utils.WaitStatementUtils;
import com.astm.commonFunctions.WCMSICommon;
import org.testng.Assert;
import java.util.Arrays;
import java.util.List;
import io.qameta.allure.Step;

public class EmergingTechnologyPage extends BasePage {
    public EmergingTechnologyPage(WebDriver driver) {
        super(driver);
    }

    // Locators for the main Emerging Technology page links
    private By additiveManufacturingConsultingServicesBtn = By.xpath("//a[contains(@class, 'astm-btn') and text()='Additive Manufacturing Consulting Services']");
    private By additiveManufacturingBtn = By.xpath("//a[contains(@class, 'astm-btn') and text()='Additive Manufacturing']");
    private By exoTechnologyBtn = By.xpath("//a[contains(@class, 'astm-btn') and text()='Exo Technology']");
    private By uasStandardsBtn = By.xpath("//a[contains(@class, 'astm-btn') and text()='UAS Standards']");
    private By roboticsAutomationBtn = By.xpath("//a[contains(@class, 'astm-btn') and text()='Robotics & Automation']");
    // Footer Contact Us link (yellow button in footer)
    private By footerContactUsBtn = By.xpath("//a[contains(@class, 'infoBanner_btnYellow') and text()='Contact Us']");

    // List of all main link locators for iteration
    private List<By> mainLinkLocators = Arrays.asList(
        additiveManufacturingConsultingServicesBtn,
        additiveManufacturingBtn,
        exoTechnologyBtn,
        uasStandardsBtn,
        roboticsAutomationBtn
    );

    /**
     * Comprehensive method for WM-111: Validate page load, link visibility, and navigation for Emerging Technology page.
     * This method loads the page, verifies all main links are visible and clickable, and checks navigation for each.
     * Finally, verifies the footer Contact Us link redirects to the Contact Us page.
     *
     * @param url The URL of the Emerging Technology page (e.g., "https://qa-regional.astm.org/emerging-technology")
     * @param expectedContactUsTitle The expected title of the Contact Us page (for assertion)
     */
    @Step("Validate Emerging Technology page load, link visibility, and navigation")
    public void validateEmergingTechnologyPageLinksAndNavigation(String url, String expectedContactUsTitle) {
        driver.get(url);
        WCMSICommon.waitForSec(2);
        Assert.assertTrue(driver.getCurrentUrl().contains("emerging-technology"), "Emerging Technology page did not load correctly");

        // Validate all main links: visible, clickable, and navigates to correct destination
        for (By linkLocator : mainLinkLocators) {
            // Scroll to link and check visibility
            ReusableMethods.scrollToElement(driver, linkLocator);
            WaitStatementUtils.explicitWaitForVisibility(driver, getElement(linkLocator), 10);
            Assert.assertTrue(getElement(linkLocator).isDisplayed(), "Link is not displayed: " + linkLocator.toString());
            String href = getElement(linkLocator).getAttribute("href");
            Assert.assertNotNull(href, "Link does not have href: " + linkLocator.toString());
            // Click and validate navigation (should open in same tab)
            getElement(linkLocator).click();
            WCMSICommon.waitForSec(3);
            Assert.assertTrue(driver.getCurrentUrl().contains(href.replace("/", "").replace("https://qa-regional.astm.org", "")),
                "Navigation failed for link: " + href);
            driver.navigate().back();
            WCMSICommon.waitForSec(2);
        }

        // Validate footer Contact Us link
        ReusableMethods.scrollToElement(driver, footerContactUsBtn);
        WaitStatementUtils.explicitWaitForVisibility(driver, getElement(footerContactUsBtn), 10);
        Assert.assertTrue(getElement(footerContactUsBtn).isDisplayed(), "Footer Contact Us link is not displayed");
        String contactUsHref = getElement(footerContactUsBtn).getAttribute("href");
        Assert.assertTrue(contactUsHref.contains("contact"), "Footer Contact Us link does not point to Contact Us page");
        getElement(footerContactUsBtn).click();
        WCMSICommon.waitForSec(3);
        Assert.assertTrue(driver.getTitle().contains(expectedContactUsTitle), "Did not navigate to Contact Us page");
    }
}
