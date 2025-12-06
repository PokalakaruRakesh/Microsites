package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;

import java.util.Arrays;
import java.util.List;

import io.qameta.allure.Step;

public class DatabaseLoggingConfigPage extends BasePage {
    private WebDriver driver;

    // Locators for the 5 dropdowns
    private By allowErrorsDropdown = By.id("database_logging_database_logging_allow_errors");
    private By enableAutoResolveDropdown = By.id("database_logging_database_logging_enable_auto_resolve");
    private By enableCustomerLoggingDropdown = By.id("database_logging_database_logging_enable_customer_logging");
    private By enableOrderLoggingDropdown = By.id("database_logging_database_logging_enable_order_logging");
    private By enableLearnerLoggingDropdown = By.id("database_logging_database_logging_enable_learner_logging");

    // Locator for Save Config button
    private By saveConfigButton = By.xpath("//button[@title='Save Config' or text()='Save Config']"); // TODO: Confirm actual locator

    // Locator for success message after saving config
    private By successMessage = By.cssSelector("div.message-success.success.message"); // TODO: Confirm actual locator

    public DatabaseLoggingConfigPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Configure all Database Logging settings to 'Yes' and save")
    public void configureAllDatabaseLoggingSettingsToYesAndSave() {
        List<By> dropdowns = Arrays.asList(
                allowErrorsDropdown,
                enableAutoResolveDropdown,
                enableCustomerLoggingDropdown,
                enableOrderLoggingDropdown,
                enableLearnerLoggingDropdown
        );
        for (By dropdown : dropdowns) {
            WaitStatementUtils.waitForElementToBeClickable(driver, getElement(dropdown));
            WebElement dropdownElement = getElement(dropdown);
            dropdownElement.click();
            // Select 'Yes' option (value='1')
            dropdownElement.findElement(By.xpath(".//option[@value='1']")).click();
            ScreenshotUtil.takeScreenshotForAllure(driver);
        }
        // Click Save Config
        WaitStatementUtils.waitForElementToBeClickable(driver, getElement(saveConfigButton));
        getElement(saveConfigButton).click();
        ScreenshotUtil.takeScreenshotForAllure(driver);
        // Wait for success message
        WaitStatementUtils.explicitWaitForVisibility(driver, getElement(successMessage), 20);
    }

    @Step("Verify Database Logging page displays exactly 5 configurable settings")
    public boolean verifyFiveConfigurableSettingsPresent() {
        List<By> dropdowns = Arrays.asList(
                allowErrorsDropdown,
                enableAutoResolveDropdown,
                enableCustomerLoggingDropdown,
                enableOrderLoggingDropdown,
                enableLearnerLoggingDropdown
        );
        int count = 0;
        for (By dropdown : dropdowns) {
            if (driver.findElements(dropdown).size() > 0) {
                count++;
            }
        }
        return count == 5;
    }

    @Step("Verify configuration saved successfully")
    public boolean isConfigurationSavedSuccessfully() {
        try {
            WaitStatementUtils.explicitWaitForVisibility(driver, getElement(successMessage), 20);
            return getElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
