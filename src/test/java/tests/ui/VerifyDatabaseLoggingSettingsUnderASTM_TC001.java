package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.AdminOrdersPage;
import pages.ui.AdminOrdersPage.LoggingStatusCounts;
import tests.ui.base.BaseTest;

public class VerifyDatabaseLoggingSettingsUnderASTM_TC001 extends BaseTest {

    private String adminUrl;
    private String adminUsername;
    private String adminPassword;
    private String testDataPath = "src/main/resources/testResource/verify-database-logging-settings-under-astm-data.json";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data from JSON
        JSONObject testData = JsonFileReader.getTestData(testDataPath, "testCaseId", "TC-001");
        if (testData != null) {
            JSONObject loginData = (JSONObject) testData.get("testData");
            if (loginData != null) {
                adminUrl = (String) loginData.getOrDefault("URL", "https://stage.astm.org/admin");
                adminUsername = (String) loginData.getOrDefault("Username", "nganugapenta@astm.org");
                adminPassword = (String) loginData.getOrDefault("Password", "Test@1234!");
            } else {
                adminUrl = "https://stage.astm.org/admin";
                adminUsername = "nganugapenta@astm.org";
                adminPassword = "Test@1234!";
            }
        } else {
            adminUrl = "https://stage.astm.org/admin";
            adminUsername = "nganugapenta@astm.org";
            adminPassword = "Test@1234!";
        }
    }

    @Test(description = "TC-001: Verify Database Logging Settings under ASTM")
    @Description("Verify logging is present for different Request types (Customers, Order, Learner) and error logs in customer transaction grids. Validate MAE Status, EBS Order Status, EBS Business Events-failed records are displayed and store the count.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDatabaseLoggingSettingsUnderASTM_TC001() {
        try {
            // Step 1: Launch Admin URL and login
            driver.get(adminUrl);
            // Assuming a LoginPage exists and is reused in BaseTest or elsewhere
            loginPage.login(adminUsername, adminPassword);

            // Step 2: Navigate to Orders page (assuming navigation is handled after login)
            AdminOrdersPage adminOrdersPage = page.getInstance(AdminOrdersPage.class);

            // Step 3: Prepare date range (e.g., from = yesterday, to = today)
            java.time.LocalDate today = java.time.LocalDate.now();
            java.time.LocalDate from = today.minusDays(1);
            String fromDate = from.toString();
            String toDate = today.toString();

            // Step 4: Call comprehensive method to verify logging settings
            LoggingStatusCounts statusCounts = adminOrdersPage.verifyDatabaseLoggingSettings(fromDate, toDate);

            // Step 5: Load expected counts from test data
            JSONObject testData = JsonFileReader.getTestData(testDataPath, "testCaseId", "TC-001");
            JSONObject expectedResults = (JSONObject) testData.get("expectedResults");
            JSONObject maeStatus = (JSONObject) expectedResults.get("maeStatus");
            JSONObject ebsOrderStatus = (JSONObject) expectedResults.get("ebsOrderStatus");
            JSONObject ebsBusinessEventsFailed = (JSONObject) expectedResults.get("ebsBusinessEventsFailed");

            int expectedMaeCount = ((Long) maeStatus.get("count")).intValue();
            int expectedEbsOrderStatusCount = ((Long) ebsOrderStatus.get("count")).intValue();
            int expectedEbsBusinessEventsFailedCount = ((Long) ebsBusinessEventsFailed.get("count")).intValue();

            // Step 6: Assertions
            Assert.assertTrue(statusCounts.maeStatusCount >= 0, "MAE Status count should be non-negative");
            Assert.assertTrue(statusCounts.ebsOrderStatusCount >= 0, "EBS Order Status count should be non-negative");
            Assert.assertTrue(statusCounts.ebsBusinessEventsFailedCount >= 0, "EBS Business Events-failed count should be non-negative");

            // Optionally, assert against expected counts (if static)
            Assert.assertEquals(statusCounts.maeStatusCount, expectedMaeCount, "MAE Status count mismatch");
            Assert.assertEquals(statusCounts.ebsOrderStatusCount, expectedEbsOrderStatusCount, "EBS Order Status count mismatch");
            Assert.assertEquals(statusCounts.ebsBusinessEventsFailedCount, expectedEbsBusinessEventsFailedCount, "EBS Business Events-failed count mismatch");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
