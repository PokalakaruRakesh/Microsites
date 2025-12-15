package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.ui.AdminOrderTransactionGridPage;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import tests.ui.base.BaseTest;

public class VerifyDatabaseLoggingSettingsUnderASTM_TC001 extends BaseTest {

    private AdminOrderTransactionGridPage adminOrderTransactionGridPage;
    private String testDataPath = "src/main/resources/testResource/verify-database-logging-settings-under-astm-data.json";
    private String adminUrl;
    private String username;
    private String password;
    private String orderNumber;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for login and order number
        JSONObject testData = JsonFileReader.getTestData(testDataPath, "testCaseId", "TC-001");
        if (testData != null) {
            adminUrl = (String) testData.getOrDefault("URL", "https://stage.astm.org/admin");
            username = (String) testData.getOrDefault("Username", "nganugapenta@astm.org");
            password = (String) testData.getOrDefault("Password", "Test@1234!");
            // Use a default order number for demo; in real test, fetch from test data or environment
            orderNumber = "979594";
        } else {
            adminUrl = "https://stage.astm.org/admin";
            username = "nganugapenta@astm.org";
            password = "Test@1234!";
            orderNumber = "979594";
        }
        // Open admin URL and login
        driver.get(adminUrl);
        // Assume loginPage is available in the framework; otherwise, implement login here
        // loginPage = page.getInstance(LoginPage.class);
        // loginPage.login(username, password);
        // For this example, login step is assumed to be handled globally or by BaseTest
    }

    @Test(description = "TC-001: Verify Database Logging Settings under ASTM")
    @Description("Verify logging for different Request types in customer transaction grids. Expected: customer, order, learner, error should be displayed.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDatabaseLoggingSettingsUnderASTM_TC001() {
        try {
            adminOrderTransactionGridPage = page.getInstance(AdminOrderTransactionGridPage.class);
            boolean result = adminOrderTransactionGridPage.verifyDatabaseLoggingSettings(orderNumber);
            Assert.assertTrue(result, "customer, order, learner, error should be displayed in transaction grid!");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during test execution: " + e.getMessage());
        }
    }
}
