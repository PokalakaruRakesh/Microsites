package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import pages.ui.EmergingTechnologyPage;
import tests.ui.base.BaseTest;

public class ValidatePageLoadPresenceOfAllLinksAndNavigation_WM_111 extends BaseTest {

    private String baseUrl;
    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/validate-page-load-presence-of-all-links-and-navigation-wm-111.json";
    private static final String TEST_CASE_ID = "WM-111";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for baseUrl
        JSONObject testData = JsonFileReader.getTestData(TEST_DATA_PATH, TEST_CASE_ID);
        if (testData != null && testData.containsKey("URL")) {
            baseUrl = (String) testData.get("URL");
        } else {
            // Fallback to default QA URL if not found in test data
            baseUrl = "https://qa-regional.astm.org/emerging-technology";
        }
    }

    @Test(description = "WM-111: Validate Page Load, Presence of All Links & Navigation on Emerging Technology page")
    @Description("Validate that the Emerging Technology page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateEmergingTechnologyPageLoadAndLinksNavigation_WM_111() {
        try {
            EmergingTechnologyPage emergingTechnologyPage = page.getInstance(EmergingTechnologyPage.class);
            // Use the comprehensive method from the Page Object
            emergingTechnologyPage.validatePageLoadAndAllLinksNavigation(baseUrl);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
