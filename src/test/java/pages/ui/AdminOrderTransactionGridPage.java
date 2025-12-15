package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;

public class AdminOrderTransactionGridPage extends BasePage {
    private WebDriver driver;

    // Locators for Transaction Grid Columns
    private By requestTypeColumnHeader = By.xpath("//th[span[text()='Request Type']]");
    private By recordTypeColumnHeader = By.xpath("//th[span[text()='Record Type']]");
    private By dataGridRows = By.cssSelector("tr.data-row");
    private By requestTypeCells = By.xpath("//td[count(//th[span[text()='Request Type']]/preceding-sibling::th)+1]");
    private By recordTypeCells = By.xpath("//td[count(//th[span[text()='Record Type']]/preceding-sibling::th)+1]");

    // Placeholder locators for navigation/actions
    private By salesMenu = By.xpath("<PLACEHOLDER_SalesMenu>"); // TODO: Replace with actual locator
    private By ordersMenu = By.xpath("<PLACEHOLDER_OrdersMenu>"); // TODO: Replace with actual locator
    private By orderSearchField = By.xpath("<PLACEHOLDER_OrderSearchField>"); // TODO: Replace with actual locator
    private By orderSearchButton = By.xpath("<PLACEHOLDER_OrderSearchButton>"); // TODO: Replace with actual locator
    private By orderViewAction = By.xpath("<PLACEHOLDER_OrderViewAction>"); // TODO: Replace with actual locator
    private By transactionGridTab = By.xpath("<PLACEHOLDER_TransactionGridTab>"); // TODO: Replace with actual locator

    public AdminOrderTransactionGridPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Verify logging for different Request types in customer transaction grids")
    public boolean verifyDatabaseLoggingSettings(String orderNumber) {
        // Navigate to Sales > Orders
        driver.findElement(salesMenu).click();
        driver.findElement(ordersMenu).click();
        // Search for the order
        driver.findElement(orderSearchField).clear();
        driver.findElement(orderSearchField).sendKeys(orderNumber);
        driver.findElement(orderSearchButton).click();
        // Click on 'View' action for the order
        WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(orderViewAction), 10);
        driver.findElement(orderViewAction).click();
        // Click on 'Transaction Grid' tab
        WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(transactionGridTab), 10);
        driver.findElement(transactionGridTab).click();
        // Wait for grid to load
        WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(requestTypeColumnHeader), 10);

        // Get all rows in the grid
        List<WebElement> rows = driver.findElements(dataGridRows);
        boolean foundCustomer = false, foundOrder = false, foundLearner = false, foundError = false;
        for (WebElement row : rows) {
            // Get the Request Type and Record Type for each row
            List<WebElement> cells = row.findElements(By.cssSelector("td"));
            String requestType = "";
            String recordType = "";
            if (cells.size() > 0) {
                // Assuming column order is fixed, adjust indices as needed
                // Find the correct indices for Request Type and Record Type
                int requestTypeIdx = getColumnIndex("Request Type");
                int recordTypeIdx = getColumnIndex("Record Type");
                if (requestTypeIdx >= 0 && requestTypeIdx < cells.size()) {
                    requestType = cells.get(requestTypeIdx).getText().trim().toLowerCase();
                }
                if (recordTypeIdx >= 0 && recordTypeIdx < cells.size()) {
                    recordType = cells.get(recordTypeIdx).getText().trim().toLowerCase();
                }
            }
            if (requestType.contains("customer")) foundCustomer = true;
            if (requestType.contains("order")) foundOrder = true;
            if (requestType.contains("learner")) foundLearner = true;
            if (requestType.contains("error") || row.getAttribute("class").contains("error")) foundError = true;
        }
        return foundCustomer && foundOrder && foundLearner && foundError;
    }

    // Helper to get column index by header name
    private int getColumnIndex(String headerName) {
        List<WebElement> headers = driver.findElements(By.cssSelector("table.data-grid thead th"));
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(headerName)) {
                return i;
            }
        }
        return -1;
    }
}
