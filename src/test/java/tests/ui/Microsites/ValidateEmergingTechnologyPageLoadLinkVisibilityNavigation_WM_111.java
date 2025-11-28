package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.EmergingTechnologyPage;
import tests.ui.base.BaseTest;

public class ValidateEmergingTechnologyPageLoadLinkVisibilityNavigation_WM_111 extends BaseTest {

    private String testDataPath = "src/main/resources/Jsons/validate-page-load-link-visibility-navigation-wm-111.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-111
        testData = JsonFileReader.getTestData(testDataPath, "WM-111");
    }

    @Test(description = "WM-111: Validate Page Load, Link Visibility & Navigation on Emerging Technology page")
    @Description("Verify that the Emerging Technology page loads correctly, all links are displayed, and each link navigates to the correct destination, including Contact Us.")
    @Severity(SeverityLevel.NORMAL)
    public void validateEmergingTechnologyPageLoadLinkVisibilityNavigation_WM_111() {
        try {
            EmergingTechnologyPage emergingTechnologyPage = page.getInstance(EmergingTechnologyPage.class);
            // Extract parameters from test data
            JSONObject pageData = (JSONObject) testData.get("Page");
            String url = (String) pageData.get("URL");
            String expectedContactUsTitle = "Contact Us"; // Could be refined if needed

            // Call comprehensive Page Object method
            emergingTechnologyPage.validateEmergingTechnologyPageLinksAndNavigation(url, expectedContactUsTitle);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
