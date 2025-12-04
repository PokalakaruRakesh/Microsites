package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.AdditiveManufacturingPage;
import tests.ui.base.BaseTest;

public class ValidateAdditiveManufacturingPageLoadLinksNavigation_WM_444 extends BaseTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/WM-444-validate-additive-manufacturing-page-load-links-navigation.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-444
        testData = JsonFileReader.getTestData(TEST_DATA_PATH, "WM-444");
        // Extract page URL from test data and navigate
        if (testData != null && testData.containsKey("TestData")) {
            JSONObject testDataObj = (JSONObject) testData.get("TestData");
            String url = (String) testDataObj.get("pageUrl");
            if (url != null && !url.isEmpty()) {
                driver.get(url);
            }
        }
    }

    @Test(description = "WM-444: Validate Additive Manufacturing page load, presence of all links & navigation")
    @Description("Validate that the Additive Manufacturing page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateAdditiveManufacturingPageLoadLinksNavigation_WM_444() {
        try {
            AdditiveManufacturingPage additiveManufacturingPage = page.getInstance(AdditiveManufacturingPage.class);
            // Comprehensive method call as per Page Object
            additiveManufacturingPage.validatePageLoadLinksAndNavigation();
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
