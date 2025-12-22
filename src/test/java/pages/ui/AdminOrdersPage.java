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
}
