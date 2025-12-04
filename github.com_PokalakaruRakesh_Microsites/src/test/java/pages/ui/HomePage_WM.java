package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;

public class HomePage_WM {
    private WebDriver driver;

    // Locators
    private By contactUsLink = By.linkText("contact us");
    private By firstNameInput = By.id("first_name");
    private By lastNameInput = By.id("last_name");
    private By submitMessageButton = By.xpath("//button[@type='submit']");
    private By homeNavigationLink = By.linkText("Home");
    private By meetASTMRepresentativeButton = By.xpath("//a[@href='https://www.astm.org' and contains(@class, 'ctaButton')]");
    private By helpingOurWorldWorkBetterHeader = By.xpath("//p[@data-testid='banner-text' and contains(text(),'Helping Our World Work Better')]");

    public HomePage_WM(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Complete Home Page Validation flow for WM-777")
    public void completeHomePageValidationFlow(String firstName, String lastName) {
        // Click on Contact Us link
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(contactUsLink), 10);
        driver.findElement(contactUsLink).click();

        // Enter First Name
        WaitStatementUtils.waitForElementToBeVisible(driver, driver.findElement(firstNameInput), 10);
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);

        // Enter Last Name
        WaitStatementUtils.waitForElementToBeVisible(driver, driver.findElement(lastNameInput), 10);
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);

        // Click on Submit Message
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(submitMessageButton), 10);
        driver.findElement(submitMessageButton).click();

        // Click on Home navigation link
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(homeNavigationLink), 10);
        driver.findElement(homeNavigationLink).click();

        // Click on Meet ASTM Representative button
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(meetASTMRepresentativeButton), 10);
        driver.findElement(meetASTMRepresentativeButton).click();
    }

    public boolean isHelpingOurWorldWorkBetterHeaderDisplayed() {
        try {
            WaitStatementUtils.waitForElementToBeVisible(driver, driver.findElement(helpingOurWorldWorkBetterHeader), 10);
            return driver.findElement(helpingOurWorldWorkBetterHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Comprehensive method for the ValidateHome page scenario (WM-777).
     * This method covers: launching the homepage, clicking contact, filling first/last name, submitting, returning home,
     * clicking 'meet with an ASTM representative', and verifying the 'Helping Our World Work Better' header.
     *
     * @param firstName   The first name to enter in the contact form
     * @param lastName    The last name to enter in the contact form
     */
    public void completeValidateHomePageFlow(String firstName, String lastName) {
        // Step 1: Launch the browser and navigate to the homepage
        driver.get("https://qa-regional.astm.org/");

        // Step 2: Click on the Contact button
        By contactButton = By.xpath("//a[contains(text(),'Contact')]"); // TODO: Replace with actual locator if available
        waitForElementPresent(contactButton);
        driver.findElement(contactButton).click();

        // Step 3: Enter the First name in the field
        By firstNameInput = By.id("first_name");
        waitForElementPresent(firstNameInput);
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);

        // Step 4: Enter the Last name in the field
        By lastNameInput = By.id("last_name");
        waitForElementPresent(lastNameInput);
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);

        // Step 5: Click on Submit message
        By submitButton = By.xpath("//button[@type='submit']");
        waitForElementPresent(submitButton);
        driver.findElement(submitButton).click();

        // Step 6: Click on Home page (assuming logo or home link)
        By homePageLink = By.xpath("//a[contains(@href, '/') and (contains(text(),'Home') or contains(@aria-label,'Home'))]"); // TODO: Replace with actual locator if available
        waitForElementPresent(homePageLink);
        driver.findElement(homePageLink).click();

        // Step 7: Click on 'meet with an ASTM representative' button
        By meetWithRepButton = By.xpath("//button[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'meet with an astm representative')]"); // TODO: Replace with actual locator if available
        waitForElementPresent(meetWithRepButton);
        driver.findElement(meetWithRepButton).click();

        // Step 8: Verify 'Helping Our World Work Better' header is displayed
        By helpingHeader = By.xpath("//*[contains(text(),'Helping Our World Work Better')]");
        waitForElementPresent(helpingHeader);
        boolean isHeaderDisplayed = driver.findElement(helpingHeader).isDisplayed();
        if (!isHeaderDisplayed) {
            throw new AssertionError("Expected header 'Helping Our World Work Better' is not displayed on the Home page.");
        }
    }

    // Utility method to wait for element presence (added for new method)
    private void waitForElementPresent(By locator) {
        WaitStatementUtils.waitForElementToBeVisible(driver, driver.findElement(locator), 10);
    }
}