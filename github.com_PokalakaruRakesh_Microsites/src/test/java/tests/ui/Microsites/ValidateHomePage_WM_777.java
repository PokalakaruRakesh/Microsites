package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import pages.ui.HomePage_WM;
import org.json.simple.JSONObject;

public class ValidateHomePage_WM_777 {

    private String testDataPath = "src/main/resources/Jsons/WM-777-validate-home-page.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Load test data for WM-777
        testData = JsonFileReader.getTestData(testDataPath, "WM-777");
        // No URL provided, so no navigation here
    }

    @Test(description = "WM-777: Validate that the Home page displays the correct header after contact flow")
    @Description("Validate that the Home page displays 'Helping Our World Work Better' header after completing contact flow and navigation.")
    @Severity(SeverityLevel.NORMAL)
    public void validateHomePageHeader_WM_777() {
        try {
            HomePage_WM homePage = page.getInstance(HomePage_WM.class);

            // Extract required test data fields
            String firstName = testData.containsKey("firstName") ? (String) testData.get("firstName") : "TestFirstName";
            String lastName = testData.containsKey("lastName") ? (String) testData.get("lastName") : "TestLastName";

            // Complete the user flow using the comprehensive Page Object method
            homePage.completeHomePageValidationFlow(firstName, lastName);

            // Assertion: Verify the expected header is displayed
            Assert.assertTrue(homePage.isHelpingOurWorldWorkBetterHeaderDisplayed(), "Expected header 'Helping Our World Work Better' is not displayed on the Home page.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during Home page validation for WM-777: " + e.getMessage());
        }
    }
}
