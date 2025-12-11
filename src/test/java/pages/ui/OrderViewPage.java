package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;

public class OrderViewPage extends BasePage {

    // Locators for Order View Tabs (from provided locators)
    private By orderViewTab = By.id("sales_order_view_tabs_order_info");
    private By orderInvoicesTab = By.id("sales_order_view_tabs_order_invoices");
    private By orderCreditMemosTab = By.id("sales_order_view_tabs_order_creditmemos");
    private By transactionsGridTab = By.id("sales_order_view_tabs_order_payloads");
    private By orderShipmentsTab = By.id("sales_order_view_tabs_order_shipments");
    private By orderHistoryTab = By.id("sales_order_view_tabs_order_history");

    // Placeholder for Transaction Grid content (as per expected result)
    private By transactionGridContent = By.id("sales_order_view_tabs_order_payloads_content"); // TODO: Replace with actual locator if needed

    public OrderViewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify Database Logging Settings under ASTM - Complete Order View Transaction Grid Verification Flow")
    public void verifyTransactionGridDisplayed() {
        // Assumes already navigated to Order View page
        // Click on Transactions Grid tab
        clickOnMethod(transactionsGridTab);
        // Wait for the Transaction Grid content to be visible
        WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(transactionGridContent), 20);
        // Optionally, assert grid is displayed (could be done in test class)
        if (!driver.findElement(transactionGridContent).isDisplayed()) {
            throw new AssertionError("Transaction grid is not displayed under Order View");
        }
    }
}
