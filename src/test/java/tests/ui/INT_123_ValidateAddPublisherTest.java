package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.ManagePublishersPage;
import pages.ui.LoginPage;

public class INT_123_ValidateAddPublisherTest {

    private String testDataPath = "src/main/resources/Jsons/validate-user-login-manage-publishers-add-publisher-data.json";
    private JSONObject testData;
    private JSONObject loginData;
    private JSONObject publisherData;
    private String baseUrl;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for INT-123
        testData = JsonFileReader.getTestData(testDataPath, "INT-123");
        loginData = (JSONObject) ((JSONObject) testData.get("Login")).get("validCredentials");
        publisherData = (JSONObject) ((JSONObject) testData.get("NewPublisher")).get("validPublisher");
        // Base URL is not present in test data JSON, so use the value from the task context
        baseUrl = "https://stage-pim.astm.org/";
    }

    @Test(description = "INT-123: Validate that the user can successfully log in to the PIM UI, access the Manage Publishers section, and add a new publisher by providing all required information and saving the record.")
    @Description("Validate that the user can successfully log in to the PIM UI, access the Manage Publishers section, and add a new publisher by providing all required information and saving the record.")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddPublisherAndSave() {
        try {
            // Initialize page objects
            LoginPage loginPage = page.getInstance(LoginPage.class);
            ManagePublishersPage managePublishersPage = page.getInstance(ManagePublishersPage.class);

            // Step 1: Launch the browser and open the PIM UI
            driver.get(baseUrl);

            // Step 2: Login
            String username = (String) loginData.get("username");
            String password = (String) loginData.get("password");
            loginPage.login(username, password);

            // Step 3: Add a new publisher using comprehensive page object method
            String name = (String) publisherData.get("name");
            String code = (String) publisherData.get("code");
            String publisherType = "Third-Party Content Publisher"; // As per test data

            boolean isSaved = managePublishersPage.addNewPublisherAndSave(name, code, publisherType);
            Assert.assertTrue(isSaved, "User should be able to click on save button and add publisher successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during test execution: " + e.getMessage());
        }
    }
}
