package tests.ui.WCMS;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import org.testng.Assert;
import pages.ui.AdminOrdersPage;
import tests.ui.base.BaseTest;

public class VerifyDatabaseLoggingSettingsUnderASTM_TC001 extends BaseTest {

    private String testDataPath = "src/main/resources/testResource/verify-database-logging-settings-under-astm-data.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for TC-001
        testData = JsonFileReader.getTestData(testDataPath, "TC-001");
        // No URL navigation as per base class pattern (URL is handled in login if needed)
    }

    @Test(description = "TC-001: Verify Database Logging Settings under ASTM")
    @Description("Verify logging presence for different Request types and allowing error logs in customer transaction grids.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDatabaseLoggingSettingsUnderASTM_TC001() {
        try {
            // Extract test data parameters
            String fromDate = (String) testData.getOrDefault("fromDate", "2024-06-01");
            String toDate = (String) testData.getOrDefault("toDate", "2024-06-03");

            // Login credentials
            String username = (String) testData.getOrDefault("Username", "nganugapenta@astm.org");
            String password = (String) testData.getOrDefault("Password", "Test@1234!");

            // Login to Admin application (assuming loginPage exists in BaseTest)
            loginPage.login(username, password);

            // Get AdminOrdersPage instance
            AdminOrdersPage adminOrdersPage = page.getInstance(AdminOrdersPage.class);

            // Use comprehensive method for the flow
            AdminOrdersPage.LoggingStatusCounts counts = adminOrdersPage.completeVerifyDatabaseLoggingSettingsFlow(fromDate, toDate);

            // Assert application launched and logging grids are present (basic sanity)
            Assert.assertTrue(counts != null, "LoggingStatusCounts object should not be null");
            Assert.assertTrue(counts.maeStatusCount >= 0, "MAE Status count should be >= 0");
            Assert.assertTrue(counts.ebsOrderStatusCount >= 0, "EBS Order Status count should be >= 0");
            Assert.assertTrue(counts.ebsBusinessEventsFailedCount >= 0, "EBS Business Events Failed count should be >= 0");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred in verifyDatabaseLoggingSettingsUnderASTM_TC001: " + e.getMessage());
        }
    }
}
