package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import tests.ui.base.BaseTest;
import pages.ui.EmergingTechnologyExoTechnologyPage;

public class ValidatePageLoadPresenceOfAllLinksAndNavigation_WM_222 extends BaseTest {

    private String testDataPath = "src/main/resources/Jsons/ValidatePageLoadPresenceOfAllLinksAndNavigation_WM-222.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-222
        testData = JsonFileReader.getTestData(testDataPath, "WM-222");
    }

    @Test(description = "WM-222: Validate Page Load, Presence of All Links & Navigation on Emerging Technology Exo Technology page")
    @Description("Validate that the Emerging Technology Exo Technology page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked. Specifically, verify navigation to the Contact Us page.")
    @Severity(SeverityLevel.NORMAL)
    public void validatePageLoadPresenceOfAllLinksAndNavigation_WM_222() {
        try {
            EmergingTechnologyExoTechnologyPage exoPage = page.getInstance(EmergingTechnologyExoTechnologyPage.class);
            // Extract required parameters from test data
            String exoTechnologyUrl = (String) ((JSONObject) testData.get("TestData")).getOrDefault("Step 1 Data", "https://qa-regional.astm.org/emerging-technology/exo-technology");
            String contactUsExpectedTitle = "Contact Us";
            // Call the comprehensive page object method
            exoPage.validatePageLoadLinksAndNavigation(exoTechnologyUrl, contactUsExpectedTitle);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
