package tests.ui.Microsites;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.HomePage_WM;

public class ValidateHomePage_WM_777 {

    private String testDataPath = "src/main/resources/Jsons/WM-777-validate-home-page.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        // Load test data for WM-777
        testData = JsonFileReader.getTestData(testDataPath, "WM-777");
    }

    @Test(description = "WM-777: Validate that the Home page displays the 'Helping Our World Work Better' header")
    @Description("Validate that the Home page flow works and the expected header is visible after completing the contact and navigation steps.")
    @Severity(SeverityLevel.NORMAL)
    public void validateHomePage_WM_777() {
        try {
            HomePage_WM homePageWM = page.getInstance(HomePage_WM.class);

            // Extract test data parameters (example: using static values or from JSON if available)
            String firstName = testData != null && testData.get("TestData") != null && ((JSONObject)testData.get("TestData")).get("firstName") != null
                ? ((JSONObject)testData.get("TestData")).get("firstName").toString()
                : "TestFirstName";
            String lastName = testData != null && testData.get("TestData") != null && ((JSONObject)testData.get("TestData")).get("lastName") != null
                ? ((JSONObject)testData.get("TestData")).get("lastName").toString()
                : "TestLastName";

            // Call the comprehensive method as per Page Object best practice
            homePageWM.completeValidateHomePageFlow(firstName, lastName);

            // Assertion: verify header is displayed
            Assert.assertTrue(homePageWM.isHelpingOurWorldWorkBetterHeaderDisplayed(),
                "Expected header 'Helping Our World Work Better' is not displayed on the Home page.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during ValidateHomePage_WM_777 test: " + e.getMessage());
        }
    }
}
