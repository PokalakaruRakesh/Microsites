package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import tests.ui.base.BaseTest;
import pages.ui.UASDroneStandardsPage;

public class ValidateUASDroneStandardsPageLoadLinksNavigation_WM_555 extends BaseTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/WM-555-validate-uas-standards-page-load-links-navigation.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-555
        testData = JsonFileReader.getTestData(TEST_DATA_PATH, "WM-555");
        // Navigate to the UAS Drone Standards page if URL is present in test data
        if (testData != null && testData.containsKey("TestData")) {
            JSONObject testDataObj = (JSONObject) testData.get("TestData");
            String pageUrl = (String) testDataObj.get("pageUrl");
            if (pageUrl != null && !pageUrl.isEmpty()) {
                driver.get(pageUrl);
            }
        }
    }

    @Test(description = "WM-555: Validate UAS Drone Standards page load, presence of all links, and navigation")
    @Description("Validate that the UAS standards page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateUASDroneStandardsPageLoadLinksNavigation_WM_555() {
        try {
            UASDroneStandardsPage uasDroneStandardsPage = page.getInstance(UASDroneStandardsPage.class);
            uasDroneStandardsPage.validatePageLoadLinksAndNavigation();
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Exception occurred during UAS Drone Standards page validation: " + e.getMessage();
        }
    }
}
