package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import pages.ui.DatabaseLoggingConfigPage;
import pages.ui.LoginPage;
import org.json.simple.JSONObject;

public class VerifyDatabaseLoggingSettingsUnderASTM_TC001 {

    private String testDataPath = "src/main/resources/Jsons/VerifyDatabaseLoggingSettingsUnderASTM.json";
    private JSONObject testData;
    private String baseUrl;
    private String username;
    private String password;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data
        testData = JsonFileReader.getTestData(testDataPath, "VerifyDatabaseLoggingSettingsUnderASTM");
        JSONObject loginData = (JSONObject) testData.get("Login");
        baseUrl = "https://stage.astm.org/admin"; // As per test data
        username = (String) loginData.get("Username");
        password = (String) loginData.get("Password");
        // Open the application URL
        base.utils.ReusableMethods.openUrl(baseUrl);
    }

    @Test(description = "TC-001: Verify Database Logging Settings under ASTM")
    @Description("Verify Admin can login and access SYSTEM → Configuration → ASTM → Database Logging, and that the page loads with exactly 5 configurable settings. Configuration should get saved successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDatabaseLoggingSettingsUnderASTM_TC001() {
        try {
            // Login
            LoginPage loginPage = page.getInstance(LoginPage.class);
            loginPage.login(username, password);

            // Navigate to Database Logging Configuration page (assume navigation is part of the PO method or handled by test setup)
            DatabaseLoggingConfigPage dbLoggingPage = page.getInstance(DatabaseLoggingConfigPage.class);

            // Verify 5 configurable settings are present
            Assert.assertTrue(dbLoggingPage.verifyFiveConfigurableSettingsPresent(), "Database Logging page does not display exactly 5 configurable settings.");

            // Set all dropdowns to 'Yes' and save
            dbLoggingPage.configureAllDatabaseLoggingSettingsToYesAndSave();

            // Verify configuration saved successfully
            Assert.assertTrue(dbLoggingPage.isConfigurationSavedSuccessfully(), "Configuration did not save successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred in verifyDatabaseLoggingSettingsUnderASTM_TC001: " + e.getMessage());
        }
    }
}
