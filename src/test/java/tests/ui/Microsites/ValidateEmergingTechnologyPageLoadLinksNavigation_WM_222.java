package tests.ui.Microsites;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.EmergingTechnologyPage;
import tests.ui.base.BaseTest;

public class ValidateEmergingTechnologyPageLoadLinksNavigation_WM_222 extends BaseTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/WM-222-validate-page-load-links-navigation.json";
    private static final String TEST_CASE_ID = "WM-222";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        try {
            JSONObject testData = JsonFileReader.getTestData(TEST_DATA_PATH, TEST_CASE_ID);
            JSONObject testDataObj = (JSONObject) testData.get("TestData");
            String pageUrl = (String) testDataObj.get("pageUrl");
            if (pageUrl != null && !pageUrl.isEmpty()) {
                driver.get(pageUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "WM-222: Validate Page Load, Presence of All Links & Navigation on Emerging Technology page")
    @Description("Validate that the Emerging Technology page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked. Final expected result: User navigates to the Contact Us page.")
    @Severity(SeverityLevel.NORMAL)
    public void validateEmergingTechnologyPageLoadLinksNavigation_WM_222() {
        try {
            EmergingTechnologyPage emergingTechnologyPage = page.getInstance(EmergingTechnologyPage.class);
            emergingTechnologyPage.validateEmergingTechnologyPageLinksAndNavigation();
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
