package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.ui.AdditiveManufacturingPage;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;

public class ValidateAdditiveManufacturingPageLoadLinksNavigation_WM_444 {

    private String testDataPath;
    private String baseUrl;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Set the path for test data JSON
        testDataPath = "src/main/resources/Jsons/validate-page-load-presence-of-all-links-and-navigation-wm-444.json";
        // Load the base URL from test data
        JSONObject testData = JsonFileReader.getTestData(testDataPath, "PageLoad");
        // Extract the full URL (handle both relative and absolute)
        Object urlObj = testData.get("url");
        if (urlObj != null) {
            String url = urlObj.toString();
            if (url.startsWith("http")) {
                baseUrl = url;
            } else {
                // Default to QA regional domain as per context
                baseUrl = "https://qa-regional.astm.org" + url;
            }
        } else {
            // Fallback in case test data is missing
            baseUrl = "https://qa-regional.astm.org/emerging-technology/additive-manufacturing";
        }
    }

    @Test(description = "WM-444: Validate Additive Manufacturing page load, presence of all links, and navigation")
    @Description("Validate that the Additive Manufacturing page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateAdditiveManufacturingPageLoadLinksNavigation_WM_444() {
        try {
            AdditiveManufacturingPage additiveManufacturingPage = page.getInstance(AdditiveManufacturingPage.class);
            additiveManufacturingPage.validatePageLoadLinksAndNavigation(baseUrl);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
