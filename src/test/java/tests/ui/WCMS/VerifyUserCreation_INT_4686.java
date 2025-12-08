package tests.ui.WCMS;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.ui.MAEUserCreationPage;
import src.main.base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import tests.ui.base.BaseTest;

public class VerifyUserCreation_INT_4686 extends BaseTest {

    private MAEUserCreationPage maeUserCreationPage;
    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/verify-user-creation-int-4686-data.json";
    private static final String TEST_CASE_ID = "INT-4686";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // No specific URL provided in test data for navigation
        // If needed, navigation can be handled in login or in the page object
    }

    @Test(description = "INT-4686: Verify user creation with any roles")
    @Description("Verify if user is able to register user details with any roles and that the email is reflected under Manage users.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserCreation() {
        try {
            maeUserCreationPage = page.getInstance(MAEUserCreationPage.class);

            // Load test data for INT-4686
            JSONObject testData = JsonFileReader.getTestData(TEST_DATA_PATH, TEST_CASE_ID);
            JSONObject userData = (JSONObject) ((JSONObject) JsonFileReader.getTestData(TEST_DATA_PATH, "UserCreation")).get("ValidData");
            // Use the first valid data set for this test
            JSONObject validUser = (JSONObject) ((org.json.simple.JSONArray) userData).get(0);

            String email = (String) validUser.get("email");
            String firstName = (String) validUser.get("firstName");
            String lastName = (String) validUser.get("lastName");

            // Complete the add user flow (comprehensive method)
            maeUserCreationPage.completeAddUserFlow(email, firstName, lastName);

            // Assert that the email is present in Manage Users
            Assert.assertTrue(maeUserCreationPage.isUserEmailPresentInManageUsers(email),
                "Created user email was not found under Manage Users table!");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred in verifyUserCreation: " + e.getMessage());
        }
    }
}
