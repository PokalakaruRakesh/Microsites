package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import tests.ui.base.BaseTest;
import pages.ui.EmergingTechnologyPage;

public class ValidatePageLoadPresenceOfAllLinksAndNavigation_WM_111 extends BaseTest {

    private String testDataPath = "src/main/resources/Jsons/ValidatePageLoadPresenceOfAllLinksAndNavigation-WM-111.json";

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        try {
            // Load test data
            JSONObject testData = JsonFileReader.getTestData(testDataPath, "WM-111");
            if (testData != null && testData.containsKey("TestData")) {
                JSONObject td = (JSONObject) testData.get("TestData");
                String pageUrl = (String) td.get("PageURL");
                if (pageUrl != null && !pageUrl.isEmpty()) {
                    driver.get(pageUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "WM-111: Validate Page Load, Presence of All Links & Navigation on Emerging Technology page")
    @Description("Validate that the Emerging Technology page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateEmergingTechnologyPageLoadAndLinksNavigation_WM_111() {
        try {
            EmergingTechnologyPage emergingTechnologyPage = page.getInstance(EmergingTechnologyPage.class);
            emergingTechnologyPage.validatePageLoadAndAllLinksNavigation();
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
