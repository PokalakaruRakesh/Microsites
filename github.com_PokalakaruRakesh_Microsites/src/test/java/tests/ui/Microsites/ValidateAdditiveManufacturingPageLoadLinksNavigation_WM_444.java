package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.ui.AdditiveManufacturingPage;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;

public class ValidateAdditiveManufacturingPageLoadLinksNavigation_WM_444 {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/WM-444-validate-additive-manufacturing-page-load-links-navigation.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        try {
            // Load test data for WM-444
            testData = JsonFileReader.getTestData(TEST_DATA_PATH, "WM-444");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "WM-444: Validate Additive Manufacturing page load, all links presence & navigation")
    @Description("Validate that the Additive Manufacturing page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateAdditiveManufacturingPageLoadLinksNavigation_WM_444() {
        try {
            AdditiveManufacturingPage additiveManufacturingPage = page.getInstance(AdditiveManufacturingPage.class);
            // Extract only required parameters from testData (if needed in the method signature)
            String pageUrl = (String) ((JSONObject) testData.get("TestData")).get("pageUrl");
            String expectedHeader = (String) ((JSONObject) testData.get("TestData")).get("expectedHeader");
            // Call the comprehensive method (per codegen rules)
            additiveManufacturingPage.validatePageLoadLinksAndNavigation(pageUrl, expectedHeader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
