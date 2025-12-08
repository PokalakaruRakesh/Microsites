package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;

import io.qameta.allure.Step;

public class MAEUserCreationPage extends BasePage {

    // Locators
    private By usernameInput = By.id("userName");
    private By passwordInput = By.id("encPass");
    private By signInButton = By.xpath("//button[contains(text(), 'Sign In')]");
    private By maeSelectButton = By.xpath("//input[@data-testid='MAE-button']");
    private By selectTenantButton(String tenantName) {
        return By.xpath("//span[text()='" + tenantName + "']/following-sibling::input[@value='Select']");
    }
    private By accountIdDropdown = By.xpath("//select[@id='types']");
    private By searchInput = By.xpath("//input[@class='form-control search-input' and @placeholder='Search here']");
    private By searchButton = By.xpath("//button[contains(@class,'search-button')]");
    private By accountRowByMAEId(String maeId) {
        return By.xpath("//td[@class='max_text' and text()='" + maeId + "']");
    }
    private By accountAdminTab = By.xpath("//p[contains(@data-testid,'tab-0') and contains(@class,'tab') and .//span[text()='Account Administration']]");
    private By userManagementTab = By.xpath("//p[contains(@data-testid,'tab-2') and contains(@class,'tab') and .//span[text()='User Management']]");

    public MAEUserCreationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Complete user creation flow: login, select MAE, select tenant, search account, verify landing on Account Administration/User Management tabs
     */
    @Step("Verify user is able to register user details with any roles and land on Account Administration, User Management tab")
    public void completeUserCreationFlow(String url, String username, String password, String tenantName, String maeAccountId) {
        try {
            driver.get(url);
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(usernameInput), 20);
            driver.findElement(usernameInput).clear();
            driver.findElement(usernameInput).sendKeys(username);
            driver.findElement(passwordInput).clear();
            driver.findElement(passwordInput).sendKeys(password);
            WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(signInButton));
            ScreenshotUtil.takeScreenshotForAllure(driver);
            driver.findElement(signInButton).click();

            // Wait for MAE selection page
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(maeSelectButton), 20);
            driver.findElement(maeSelectButton).click();

            // Wait for tenant selection
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(selectTenantButton(tenantName)), 20);
            driver.findElement(selectTenantButton(tenantName)).click();

            // Wait for account search dropdown
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(accountIdDropdown), 20);
            driver.findElement(accountIdDropdown).click();
            // Select 'MAE Account ID' from dropdown
            ReusableMethods.selectDropdownByVisibleText(driver.findElement(accountIdDropdown), "MAE Account ID");

            // Enter search value
            driver.findElement(searchInput).clear();
            driver.findElement(searchInput).sendKeys(maeAccountId);
            driver.findElement(searchButton).click();

            // Wait for account row
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(accountRowByMAEId(maeAccountId)), 20);
            driver.findElement(accountRowByMAEId(maeAccountId)).click();

            // Wait for Account Administration and User Management tabs
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(accountAdminTab), 20);
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(userManagementTab), 20);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to complete user creation flow: " + e.getMessage(), e);
        }
    }
}
