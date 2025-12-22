package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import java.util.List;
import org.apache.log4j.Logger;
import tests.ui.base.BaseTest;

public class AdminOrdersPage extends BasePage {
    public Logger log = Logger.getLogger(BaseTest.class);

    // Locators for Orders Admin Page actions
    private By createNewOrderButton = By.id("add");
    private By goToArchiveButton = By.id("go_to_archive");
    private By createNewResubmitBatchButton = By.id("create_resubmit");
    private By viewResubmitBatchButton = By.id("view_resubmit");
    private By fromDateField = By.xpath("//input[@name='from_date']"); // TODO: Replace with actual locator if different
    private By toDateField = By.xpath("//input[@name='to_date']");   // TODO: Replace with actual locator if different
    private By findOrdersButton = By.xpath("//button[normalize-space()='Find Orders']"); // TODO: Replace with actual locator if different
    private By maeStatusRecords = By.xpath("//table//td[contains(text(),'MAE Status')]/following-sibling::td"); // TODO: Replace with actual locator
    private By ebsOrderStatusRecords = By.xpath("//table//td[contains(text(),'EBS Order Status')]/following-sibling::td"); // TODO: Replace with actual locator
    private By ebsBusinessEventsFailedRecords = By.xpath("//table//td[contains(text(),'EBS Business Events-failed')]/following-sibling::td"); // TODO: Replace with actual locator

    public AdminOrdersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Complete flow: Create New Re-Submit Batch, filter by date, find orders, and capture logging status counts.
     * @param fromDate String - date in format required by UI (e.g., "2024-06-01")
     * @param toDate String - date in format required by UI (e.g., "2024-06-03")
     * @return LoggingStatusCounts - POJO holding counts for MAE, EBS Order Status, and EBS Business Events-failed
     */
    public LoggingStatusCounts verifyDatabaseLoggingSettings(String fromDate, String toDate) {
        try {
            // Click on "Create New Re-Submit Batch"
            clickOnMethod(createNewResubmitBatchButton);

            // Enter date range
            waitForElementPresent(fromDateField);
            WebElement fromInput = getElement(fromDateField);
            fromInput.clear();
            fromInput.sendKeys(fromDate);

            waitForElementPresent(toDateField);
            WebElement toInput = getElement(toDateField);
            toInput.clear();
            toInput.sendKeys(toDate);

            ScreenshotUtil.takeScreenshotForAllure(driver);

            // Click on "Find Orders"
            clickOnMethod(findOrdersButton);

            // Wait for results to load (could be improved with a more specific wait)
            Thread.sleep(2000);

            // Get counts for MAE Status, EBS Order Status, EBS Business Events-failed
            int maeCount = getStatusCount(maeStatusRecords);
            int ebsOrderStatusCount = getStatusCount(ebsOrderStatusRecords);
            int ebsBusinessEventsFailedCount = getStatusCount(ebsBusinessEventsFailedRecords);

            ScreenshotUtil.takeScreenshotForAllure(driver);

            return new LoggingStatusCounts(maeCount, ebsOrderStatusCount, ebsBusinessEventsFailedCount);
        } catch (Exception e) {
            log.error("Error in verifyDatabaseLoggingSettings flow", e);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to verify database logging settings", e);
        }
    }

    private int getStatusCount(By statusLocator) {
        try {
            List<WebElement> elements = getElements(statusLocator);
            if (elements == null) return 0;
            return elements.size();
        } catch (Exception e) {
            log.warn("Could not find elements for locator: " + statusLocator, e);
            return 0;
        }
    }

    // POJO to hold logging status counts
    public static class LoggingStatusCounts {
        public final int maeStatusCount;
        public final int ebsOrderStatusCount;
        public final int ebsBusinessEventsFailedCount;

        public LoggingStatusCounts(int maeStatusCount, int ebsOrderStatusCount, int ebsBusinessEventsFailedCount) {
            this.maeStatusCount = maeStatusCount;
            this.ebsOrderStatusCount = ebsOrderStatusCount;
            this.ebsBusinessEventsFailedCount = ebsBusinessEventsFailedCount;
        }
    }

    // --- NEW FUNCTIONALITY ADDED BELOW (per new requirements) ---

    /**
     * Clicks the "Create New Order" button on the Orders Admin Page.
     */
    public void clickCreateNewOrderButton() {
        try {
            clickOnMethod(createNewOrderButton);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            log.error("Error clicking Create New Order button", e);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to click Create New Order button", e);
        }
    }

    /**
     * Clicks the "Go To Archive" button on the Orders Admin Page.
     */
    public void clickGoToArchiveButton() {
        try {
            clickOnMethod(goToArchiveButton);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            log.error("Error clicking Go To Archive button", e);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to click Go To Archive button", e);
        }
    }

    /**
     * Clicks the "View Re-Submit Batch" button on the Orders Admin Page.
     */
    public void clickViewResubmitBatchButton() {
        try {
            clickOnMethod(viewResubmitBatchButton);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            log.error("Error clicking View Re-Submit Batch button", e);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to click View Re-Submit Batch button", e);
        }
    }

    /**
     * Enters the given from and to dates in the Orders Admin Page date fields.
     * @param fromDate String - date in format required by UI (e.g., "2024-06-01")
     * @param toDate String - date in format required by UI (e.g., "2024-06-03")
     */
    public void enterOrderDateRange(String fromDate, String toDate) {
        try {
            waitForElementPresent(fromDateField);
            WebElement fromInput = getElement(fromDateField);
            fromInput.clear();
            fromInput.sendKeys(fromDate);

            waitForElementPresent(toDateField);
            WebElement toInput = getElement(toDateField);
            toInput.clear();
            toInput.sendKeys(toDate);

            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            log.error("Error entering order date range", e);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to enter order date range", e);
        }
    }

    /**
     * Clicks the "Find Orders" button on the Orders Admin Page.
     */
    public void clickFindOrdersButton() {
        try {
            clickOnMethod(findOrdersButton);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            log.error("Error clicking Find Orders button", e);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to click Find Orders button", e);
        }
    }

    /**
     * Returns the count of MAE Status records currently displayed.
     * @return int - count of MAE Status records
     */
    public int getMaeStatusRecordCount() {
        return getStatusCount(maeStatusRecords);
    }

    /**
     * Returns the count of EBS Order Status records currently displayed.
     * @return int - count of EBS Order Status records
     */
    public int getEbsOrderStatusRecordCount() {
        return getStatusCount(ebsOrderStatusRecords);
    }

    /**
     * Returns the count of EBS Business Events-failed records currently displayed.
     * @return int - count of EBS Business Events-failed records
     */
    public int getEbsBusinessEventsFailedRecordCount() {
        return getStatusCount(ebsBusinessEventsFailedRecords);
    }

    /**
     * Performs the complete flow for verifying database logging settings under ASTM as per TC-001.
     * Launches the Orders Admin page, clicks on 'Create New Re-Submit Batch',
     * enters the date range, finds orders, gets relevant status counts, and handles resubmission if required.
     *
     * @param fromDate String - date in format required by UI (e.g., "2024-06-01")
     * @param toDate String - date in format required by UI (e.g., "2024-06-03")
     * @return LoggingStatusCounts - POJO holding counts for MAE, EBS Order Status, and EBS Business Events-failed
     */
    public LoggingStatusCounts completeVerifyDatabaseLoggingSettingsFlow(String fromDate, String toDate) {
        try {
            // Click on "Create New Re-Submit Batch"
            clickOnMethod(createNewResubmitBatchButton);

            // Enter date range
            waitForElementPresent(fromDateField);
            WebElement fromInput = getElement(fromDateField);
            fromInput.clear();
            fromInput.sendKeys(fromDate);

            waitForElementPresent(toDateField);
            WebElement toInput = getElement(toDateField);
            toInput.clear();
            toInput.sendKeys(toDate);

            ScreenshotUtil.takeScreenshotForAllure(driver);

            // Click on "Find Orders"
            clickOnMethod(findOrdersButton);

            // Wait for results to load
            Thread.sleep(2000);

            // Get counts for MAE Status, EBS Order Status, EBS Business Events-failed
            int maeCount = getStatusCount(maeStatusRecords);
            int ebsOrderStatusCount = getStatusCount(ebsOrderStatusRecords);
            int ebsBusinessEventsFailedCount = getStatusCount(ebsBusinessEventsFailedRecords);

            ScreenshotUtil.takeScreenshotForAllure(driver);

            // If MAE status record count is not zero, click on Re-submit Mae orders button
            if (maeCount > 0) {
                // Placeholder locator for 'Re-submit Mae orders' button
                By reSubmitMaeOrdersButton = By.xpath("<PLACEHOLDER_ReSubmitMaeOrdersButton>"); // TODO: Replace with actual locator
                clickOnMethod(reSubmitMaeOrdersButton);

                // Verify the pop-up text
                By reSubmitPopupText = By.xpath("<PLACEHOLDER_ReSubmitPopupText>"); // TODO: Replace with actual locator
                waitForElementPresent(reSubmitPopupText);
                String popupText = getElement(reSubmitPopupText).getText();
                if (!popupText.contains("You have selected to Re-Submit 1 failed orders that are currently in MAE Status. These orders will be added to the queue with \"MAE Status\" Recovery Point.")) {
                    log.error("Resubmit MAE orders popup text did not match expected message.");
                    throw new AssertionError("Popup text mismatch");
                }

                // Click on "continue" button
                By continueButton = By.xpath("<PLACEHOLDER_ContinueButton>"); // TODO: Replace with actual locator
                clickOnMethod(continueButton);

                // Click on "ok" on alert pop-up
                driver.switchTo().alert().accept();
            }

            // Click on "up-arrow" for batch id column
            By batchIdUpArrow = By.xpath("<PLACEHOLDER_BatchIdUpArrow>"); // TODO: Replace with actual locator
            clickOnMethod(batchIdUpArrow);

            ScreenshotUtil.takeScreenshotForAllure(driver);

            return new LoggingStatusCounts(maeCount, ebsOrderStatusCount, ebsBusinessEventsFailedCount);
        } catch (Exception e) {
            log.error("Error in completeVerifyDatabaseLoggingSettingsFlow", e);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to complete verify database logging settings flow", e);
        }
    }
}