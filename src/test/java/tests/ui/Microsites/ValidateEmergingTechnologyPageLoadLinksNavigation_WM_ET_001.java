package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import tests.ui.base.BaseTest;
import pages.ui.EmergingTechnologyPage;

public class ValidateEmergingTechnologyPageLoadLinksNavigation_WM_ET_001 extends BaseTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/WM-ET-001-validate-page-load-links-navigation.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-ET-001
        testData = JsonFileReader.getTestData(TEST_DATA_PATH, "WM-ET-001");
        // Extract page URL from test data and navigate
        if (testData != null && testData.containsKey("TestData")) {
            JSONObject testDataObj = (JSONObject) testData.get("TestData");
            String pageUrl = (String) testDataObj.get("pageUrl");
            if (pageUrl != null && !pageUrl.isEmpty()) {
                driver.get(pageUrl);
            }
        }
    }

    @Test(description = "WM-ET-001: Validate Page Load, Presence of All Links & Navigation on Emerging Technology page")
    @Description("Validate that the Emerging Technology page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateEmergingTechnologyPageLoadLinksNavigation_WM_ET_001() {
        try {
            EmergingTechnologyPage emergingTechnologyPage = page.getInstance(EmergingTechnologyPage.class);
            // Call the comprehensive method to validate page load, presence of all links, and navigation
            emergingTechnologyPage.validateEmergingTechnologyPageLinksAndNavigation();
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
