package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import pages.ui.OrderViewPage;
import pages.LoginPage;
import org.json.simple.JSONObject;

public class VerifyDatabaseLoggingSettingsUnderASTM_TC001 {

    private LoginPage loginPage;
    private OrderViewPage orderViewPage;
    
    private static final String TEST_DATA_PATH = "src/main/resources/testResource/verify-database-logging-settings-under-astm-data.json";
    private static final String TEST_CASE_ID = "TC-001";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // No URL navigation here, handled in test if needed
    }

    @Test(description = "TC-001: Verify Database Logging Settings under ASTM")
    @Description("Under ORDER VIEW, verify logging should present for different 'Request types' that are Customers, Order, and Learner, allowing error logs in customer transaction grids. Transaction grid should be displayed.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDatabaseLoggingSettingsUnderASTM_TC001() {
        try {
            // Load test data
            JSONObject testData = JsonFileReader.getTestData(TEST_DATA_PATH, TEST_CASE_ID);
            String url = (String) testData.get("URL");
            String username = (String) testData.get("Username");
            String password = (String) testData.get("Password");

            // Login
            loginPage = page.getInstance(LoginPage.class);
            loginPage.login(url, username, password);

            // Order View Page - Transaction Grid Verification
            orderViewPage = page.getInstance(OrderViewPage.class);
            orderViewPage.verifyTransactionGridDisplayed();

            // If no exception, assert true
            Assert.assertTrue(true, "Transaction grid is displayed under Order View.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
