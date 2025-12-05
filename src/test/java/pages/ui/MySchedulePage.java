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

public class MySchedulePage extends BasePage {
    private WebDriver driver;

    // Locators
    private By usernameInput = By.id("userName");
    private By passwordInput = By.id("encPass");
    private By signInButton = By.xpath("//button[text()='Sign In']");
    private By dropdownToggleButton = By.id("dropdown-menu-align-right");
    private By myCommitteesLink = By.linkText("MyCommittees");
    private By meetingsMinutesAgendasButton = By.xpath("//button[text()='Meetings, Minutes & Agendas']");
    private By createMyScheduleLink = By.linkText("Create MySchedule");
    private By createMyScheduleHeader = By.xpath("//h1[contains(text(),'Create MySchedule')]");
    private By testEventLink = By.xpath("//a[@data-testid='ASTMLink' and contains(text(),'UAT Test Event oct committee 2025')]");
    private By uncheckAllLabel = By.xpath("//label[@for='_000']");
    private By e27Label = By.xpath("//label[@for='_E27']");
    private By continueButton = By.cssSelector("button[data-testid='continue']");
    private By e27RadioButton = By.id("E27-0");
    private By includeMainCommitteeMeetingsLabel = By.xpath("//label[@for='E27-0' and contains(text(),'Include Main Committee meetings')]");
    private By continueSubmitButton = By.cssSelector("button[data-testid='continueSubmit']");
    private By eventHeader = By.xpath("//h4[contains(text(),'UAT Test Event oct committee 2025')]");
    private By exportToExcelButton = By.cssSelector("button[data-testid='create-schedule-btn']");

    public MySchedulePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Complete the Create MySchedule flow for INT-3760.
     * @param username The username to login
     * @param password The password to login
     */
    @Step("Complete Create MySchedule flow and trigger Excel export")
    public void completeCreateMyScheduleFlow(String username, String password) {
        try {
            // Login
            WaitStatementUtils.waitForElementToBeClickable(driver, usernameInput);
            driver.findElement(usernameInput).clear();
            driver.findElement(usernameInput).sendKeys(username);
            driver.findElement(passwordInput).clear();
            driver.findElement(passwordInput).sendKeys(password);
            driver.findElement(signInButton).click();
            WCMSICommon.waitForSec(3);

            // Open MyCommittees
            WaitStatementUtils.waitForElementToBeClickable(driver, dropdownToggleButton);
            driver.findElement(dropdownToggleButton).click();
            WaitStatementUtils.waitForElementToBeClickable(driver, myCommitteesLink);
            driver.findElement(myCommitteesLink).click();
            WCMSICommon.waitForSec(2);

            // Meetings, Minutes & Agendas
            WaitStatementUtils.waitForElementToBeClickable(driver, meetingsMinutesAgendasButton);
            driver.findElement(meetingsMinutesAgendasButton).click();
            WCMSICommon.waitForSec(2);

            // Create MySchedule
            WaitStatementUtils.waitForElementToBeClickable(driver, createMyScheduleLink);
            driver.findElement(createMyScheduleLink).click();
            WCMSICommon.waitForSec(2);

            // Verify Create MySchedule page
            Assert.assertTrue(driver.findElement(createMyScheduleHeader).isDisplayed(), "Create MySchedule header not found");

            // Click on event link
            WaitStatementUtils.waitForElementToBeClickable(driver, testEventLink);
            driver.findElement(testEventLink).click();
            WCMSICommon.waitForSec(2);

            // Uncheck All
            WaitStatementUtils.waitForElementToBeClickable(driver, uncheckAllLabel);
            driver.findElement(uncheckAllLabel).click();
            WCMSICommon.waitForSec(1);

            // Select E27
            WaitStatementUtils.waitForElementToBeClickable(driver, e27Label);
            driver.findElement(e27Label).click();
            WCMSICommon.waitForSec(1);

            // Continue
            WaitStatementUtils.waitForElementToBeClickable(driver, continueButton);
            driver.findElement(continueButton).click();
            WCMSICommon.waitForSec(2);

            // Select radio button for Include Main Committee meetings
            WaitStatementUtils.waitForElementToBeClickable(driver, e27RadioButton);
            driver.findElement(e27RadioButton).click();
            WCMSICommon.waitForSec(1);

            // Continue submit
            WaitStatementUtils.waitForElementToBeClickable(driver, continueSubmitButton);
            driver.findElement(continueSubmitButton).click();
            WCMSICommon.waitForSec(2);

            // Verify event header
            Assert.assertTrue(driver.findElement(eventHeader).isDisplayed(), "Event header not found");

            // Export to Excel
            WaitStatementUtils.waitForElementToBeClickable(driver, exportToExcelButton);
            driver.findElement(exportToExcelButton).click();
            WCMSICommon.waitForSec(5); // Wait for download to trigger
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to complete Create MySchedule flow: " + e.getMessage(), e);
        }
    }
}
