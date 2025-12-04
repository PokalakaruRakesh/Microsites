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
}
