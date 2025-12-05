package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.Step;

/**
 * Page Object for ASTM > Database Logging configuration page in Magento Admin.
 * Covers navigation and configuration of 5 database logging settings and saving the config.
 */
public class DatabaseLoggingPage {
    private WebDriver driver;

    // Locators for the 5 dropdowns (example: Enable Learner Logging)
    private By enableLearnerLoggingDropdown = By.id("database_logging_database_logging_enable_learner_logging");
    // TODO: Add 4 more dropdown locators as per actual page structure
    private By dropdown2 = By.xpath("<PLACEHOLDER_DROPDOWN2>"); // TODO: Replace with actual locator
    private By dropdown3 = By.xpath("<PLACEHOLDER_DROPDOWN3>"); // TODO: Replace with actual locator
    private By dropdown4 = By.xpath("<PLACEHOLDER_DROPDOWN4>"); // TODO: Replace with actual locator
    private By dropdown5 = By.xpath("<PLACEHOLDER_DROPDOWN5>"); // TODO: Replace with actual locator

    // Save Config button
    private By saveConfigButton = By.xpath("//button[@id='save' or @title='Save Config' or contains(text(),'Save Config')]"); // Adjust as per actual locator

    // Success message after saving config
    private By successMessage = By.cssSelector("div.message-success"); // Adjust as per actual locator

    public DatabaseLoggingPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Configure all 5 Database Logging settings to 'Yes' and save configuration")
    public void configureAllDatabaseLoggingSettingsToYesAndSave() {
        // Set all dropdowns to 'Yes'
        setDropdownToYes(enableLearnerLoggingDropdown);
        setDropdownToYes(dropdown2);
        setDropdownToYes(dropdown3);
        setDropdownToYes(dropdown4);
        setDropdownToYes(dropdown5);
        // Click Save Config
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(saveConfigButton));
        driver.findElement(saveConfigButton).click();
        // Wait for success message
        WaitStatementUtils.waitForElementPresent(driver, successMessage);
    }

    private void setDropdownToYes(By dropdownLocator) {
        WaitStatementUtils.waitForElementPresent(driver, dropdownLocator);
        WebElement dropdown = driver.findElement(dropdownLocator);
        Select select = new Select(dropdown);
        select.selectByVisibleText("Yes");
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WaitStatementUtils.waitForElementPresent(driver, successMessage);
            return driver.findElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAllConfigDropdownValues() {
        List<String> values = new ArrayList<>();
        values.add(getSelectedDropdownValue(enableLearnerLoggingDropdown));
        values.add(getSelectedDropdownValue(dropdown2));
        values.add(getSelectedDropdownValue(dropdown3));
        values.add(getSelectedDropdownValue(dropdown4));
        values.add(getSelectedDropdownValue(dropdown5));
        return values;
    }

    private String getSelectedDropdownValue(By dropdownLocator) {
        WaitStatementUtils.waitForElementPresent(driver, dropdownLocator);
        WebElement dropdown = driver.findElement(dropdownLocator);
        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }

    // Navigation to this page should be handled by a higher-level navigation page object (e.g., AdminMenuPage)
}
