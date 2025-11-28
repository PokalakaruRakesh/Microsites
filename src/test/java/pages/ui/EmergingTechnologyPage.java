package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import java.util.Arrays;
import java.util.List;

public class EmergingTechnologyPage extends BasePage {
    public EmergingTechnologyPage(WebDriver driver) {
        super(driver);
    }

    // Locators for all required links/buttons on the Emerging Technology page
    private By additiveManufacturingConsultingServicesButton = By.xpath("//a[text()='Additive Manufacturing Consulting Services']");
    private By additiveManufacturingButton = By.xpath("//a[text()='Additive Manufacturing']");
    private By exoTechnologyButton = By.xpath("//a[text()='Exo Technology']");
    private By uasStandardsButton = By.xpath("//a[text()='UAS Standards']");
    private By roboticsAndAutomationButton = By.xpath("//a[text()='Robotics & Automation']");
    private By contactUsFooterButton = By.xpath("//div[contains(@class,'footer')]//a[text()='Contact Us']"); // Fallback for footer Contact Us
    private By contactUsButton = By.xpath("//a[text()='Contact Us']"); // For in-page Contact Us

    // List of all link locators for easy iteration
    private List<By> allLinkLocators = Arrays.asList(
        additiveManufacturingConsultingServicesButton,
        additiveManufacturingButton,
        exoTechnologyButton,
        uasStandardsButton,
        roboticsAndAutomationButton,
        contactUsButton // This covers both in-page and footer Contact Us
    );

    /**
     * Comprehensive method for WM-111:
     * Validates page load, presence of all links, and navigation for each link.
     * Throws AssertionError if any link is missing or navigation fails.
     */
    public void validatePageLoadAndAllLinksNavigation() {
        // Wait for the page to load by checking the presence of the main header
        WaitStatementUtils.explicitWaitForVisibility(driver, By.xpath("//h2[contains(text(),'Emerging Technology')]"), 15);
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("emerging technology"), "Page title does not contain 'Emerging Technology'");

        // Validate presence of all required links
        for (By locator : allLinkLocators) {
            Assert.assertTrue(isElementPresent(locator), "Link/button not found: " + locator.toString());
        }

        // Validate navigation for each link (open, check URL, go back)
        for (By locator : allLinkLocators) {
            String originalUrl = driver.getCurrentUrl();
            WebElement link = getElement(locator);
            String href = link.getAttribute("href");
            // Open link in same tab
            link.click();
            WaitStatementUtils.waitForPageLoad(driver, 15);
            // For internal navigation, URL should change accordingly
            if (href != null && href.startsWith("/")) {
                String expectedUrl = getExpectedUrlForHref(href);
                Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrl), "Navigation failed for link: " + href);
            } else if (href != null && href.startsWith("http")) {
                Assert.assertTrue(driver.getCurrentUrl().startsWith(href), "Navigation failed for external link: " + href);
            }
            // Go back to the main page for next link
            driver.navigate().back();
            WaitStatementUtils.waitForPageLoad(driver, 10);
        }
    }

    // Helper: Checks if element is present and displayed
    private boolean isElementPresent(By locator) {
        try {
            WebElement el = getElement(locator);
            return el != null && el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Helper: Returns the expected URL for a given href (relative to base domain)
    private String getExpectedUrlForHref(String href) {
        if (href.startsWith("/")) {
            // Remove trailing slash from base if present
            String base = driver.getCurrentUrl().replaceAll("/+$", "");
            // Remove leading slash from href
            String rel = href.replaceAll("^/+", "");
            // Only keep domain part of base
            String domain = base.replaceAll("(https?://[^/]+).*", "$1");
            return domain + "/" + rel;
        }
        return href;
    }
}
