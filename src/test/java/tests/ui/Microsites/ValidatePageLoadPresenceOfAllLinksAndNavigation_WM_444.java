package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.AdditiveManufacturingPage;

public class ValidatePageLoadPresenceOfAllLinksAndNavigation_WM_444 {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/ValidatePageLoadPresenceOfAllLinksAndNavigation-WM-444.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-444
        testData = JsonFileReader.getTestData(TEST_DATA_PATH, "WM-444");
    }

    @Test(description = "WM-444: Validate Additive Manufacturing page load, presence of all links, and navigation")
    @Description("Validate that the Additive Manufacturing page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateAdditiveManufacturingPageLinksAndNavigation_WM_444() {
        try {
            AdditiveManufacturingPage additiveManufacturingPage = page.getInstance(AdditiveManufacturingPage.class);

            // Extract parameters explicitly from test data
            String url = "https://qa-regional.astm.org/emerging-technology/additive-manufacturing";
            String expectedContactUsUrl = "/standards-and-solutions/enterprise-solutions/salesforce";

            additiveManufacturingPage.validatePageLoadLinksAndNavigation(url, expectedContactUsUrl);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
