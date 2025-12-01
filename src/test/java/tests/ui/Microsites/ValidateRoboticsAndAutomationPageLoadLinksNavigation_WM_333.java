package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import tests.ui.base.BaseTest;
import pages.ui.RoboticsAndAutomationPage;

public class ValidateRoboticsAndAutomationPageLoadLinksNavigation_WM_333 extends BaseTest {

    private String testDataPath = "src/main/resources/Jsons/WM-333-validate-page-load-links-navigation.json";
    private String testCaseId = "WM-333";
    private String roboticsAutomationUrl;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data and extract the URL
        JSONObject testData = JsonFileReader.getTestData(testDataPath, testCaseId);
        if (testData != null && testData.containsKey("pageUrl")) {
            roboticsAutomationUrl = (String) testData.get("pageUrl");
        } else {
            // Fallback to default if not found
            roboticsAutomationUrl = "https://qa-regional.astm.org/emerging-technology/robotics-automation";
        }
    }

    @Test(description = "WM-333: Validate Robotics & Automation page load, all links presence, and navigation")
    @Description("Validate that the Robotics & Automation page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    @Step("WM-333: Validate Robotics & Automation page load, all links presence, and navigation")
    public void validateRoboticsAndAutomationPageLoadLinksNavigation_WM_333() {
        try {
            RoboticsAndAutomationPage roboticsAndAutomationPage = page.getInstance(RoboticsAndAutomationPage.class);
            roboticsAndAutomationPage.validateRoboticsAndAutomationPageLoadAndLinksNavigation(roboticsAutomationUrl);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
