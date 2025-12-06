package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;

/**
 * Page Object for Magento Admin Cache Management Page
 * Covers the full flow for: Verify Cache Management Settings under ASTM (TC-002)
 */
public class CacheManagementPage extends BasePage {
    // Locators
    private By usernameField = By.id("username"); // TODO: Replace with actual locator if different
    private By passwordField = By.id("login"); // TODO: Replace with actual locator if different
    private By loginButton = By.cssSelector("button.action-login"); // TODO: Replace with actual locator if different
    private By systemMenu = By.xpath("//span[text()='System']"); // TODO: Replace with actual locator
    private By cacheManagementMenu = By.xpath("//span[text()='Cache Management']"); // TODO: Replace with actual locator
    private By flushMagentoCacheButton = By.id("flush_magento");
    private By successMessage = By.cssSelector("div[data-ui-id='messages-message-success']");

    public CacheManagementPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Complete flow: Launch Magento Admin, login, navigate to Cache Management, flush cache, verify success
     * @param url Admin login URL
     * @param username Admin username
     * @param password Admin password
     * @return true if cache flush is successful, false otherwise
     */
    public boolean flushMagentoCache(String url, String username, String password) {
        try {
            driver.get(url);
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(usernameField), 20);
            driver.findElement(usernameField).clear();
            driver.findElement(usernameField).sendKeys(username);
            driver.findElement(passwordField).clear();
            driver.findElement(passwordField).sendKeys(password);
            WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(loginButton), 20);
            driver.findElement(loginButton).click();

            // Wait for admin dashboard to load (could be improved with a more precise locator)
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(systemMenu), 30);
            // Navigate to System > Cache Management
            driver.findElement(systemMenu).click();
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(cacheManagementMenu), 10);
            driver.findElement(cacheManagementMenu).click();

            // Wait for Cache Management page to load
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(flushMagentoCacheButton), 20);
            ReusableMethods.scrollIntoView(driver.findElement(flushMagentoCacheButton), driver);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            driver.findElement(flushMagentoCacheButton).click();

            // Wait for and verify success message
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(successMessage), 20);
            String msg = driver.findElement(successMessage).getText();
            ScreenshotUtil.takeScreenshotForAllure(driver);
            return msg != null && msg.contains("cache storage has been flushed");
        } catch (Exception e) {
            e.printStackTrace();
            ScreenshotUtil.takeScreenshotForAllure(driver);
            return false;
        }
    }
}
