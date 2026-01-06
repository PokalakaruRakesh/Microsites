package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import org.testng.Assert;
import base.utils.JsonFileReader;
import tests.ui.base.BaseTest;
import pages.ui.HomePage_WM;

public class ValidateHomePage_WM_777 extends BaseTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/WM-777-validate-home-page.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-777
        testData = JsonFileReader.getTestData(TEST_DATA_PATH, "WM-777");
        // No URL provided in test data, so no navigation here
    }

    @Test(description = "WM-777: Validate that the Home page displays 'Helping Our World Work Better' header")
    @Description("Validate that the Home page displays the correct header and UI components as per WM-777.")
    @Severity(SeverityLevel.NORMAL)
    public void validateHomePageHeader_WM_777() {
        try {
            HomePage_WM homePage = page.getInstance(HomePage_WM.class);
            // Extract test data parameters explicitly
            String firstName = testData.get("FirstName") != null ? testData.get("FirstName").toString() : "TestFirst";
            String lastName = testData.get("LastName") != null ? testData.get("LastName").toString() : "TestLast";

            boolean isBannerDisplayed = homePage.validateHomePageFlow(firstName, lastName);
            Assert.assertTrue(isBannerDisplayed, "Expected 'Helping Our World Work Better' header is not displayed on the Home page.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during Home page validation: " + e.getMessage());
        }
    }
}
