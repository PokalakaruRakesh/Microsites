package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.ui.EmergingTechnologyPage;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import tests.ui.base.BaseTest;

public class ValidatePageLoadLinkVisibilityNavigation_WM_111 extends BaseTest {

    private String testDataPath = "src/main/resources/Jsons/ValidatePageLoad-LinkVisibility-Navigation-WM-111.json";
    private String testCaseId = "WM-111";
    private String pageUrl;
    private String expectedContactUsTitle;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        try {
            JSONObject testData = JsonFileReader.getTestData(testDataPath, testCaseId);
            // Extract explicit values as per Rule A
            // Step 1 Data: "https://qa-regional.astm.org/emerging-technology"
            pageUrl = (String) ((JSONObject) testData.get("TestData")).getOrDefault("Step 1 Data", "https://qa-regional.astm.org/emerging-technology");
            // For Contact Us page, expected header/title
            expectedContactUsTitle = "Contact Us";
        } catch (Exception e) {
            e.printStackTrace();
            pageUrl = "https://qa-regional.astm.org/emerging-technology";
            expectedContactUsTitle = "Contact Us";
        }
    }

    @Test(description = "WM-111: Validate Page Load, Link Visibility & Navigation on Emerging Technology page")
    @Description("Verify that the Emerging Technology page loads correctly, all links are displayed, and each link navigates to the correct destination, including Contact Us page.")
    @Severity(SeverityLevel.NORMAL)
    public void validateEmergingTechnologyPageLoadAndNavigation_WM_111() {
        try {
            EmergingTechnologyPage emergingTechnologyPage = page.getInstance(EmergingTechnologyPage.class);
            // Call comprehensive method as per Page Object grouping rule
            emergingTechnologyPage.validatePageLoadAndNavigation(pageUrl, expectedContactUsTitle);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test WM-111 failed due to exception: " + e.getMessage();
        }
    }
}
