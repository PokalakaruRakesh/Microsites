package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.ui.ExoTechnologyPage;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class ValidateExoTechnologyPageLoadLinksNavigation_WM_222 {

    private String testDataPath = "src/main/resources/Jsons/WM-222-validate-page-load-links-navigation.json";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // No URL navigation here; handled in Page Object or test method if needed
    }

    @Test(description = "WM-222: Validate Exo Technology page loads, all links are displayed, and navigation works")
    @Description("Validate that the Exo Technology page loads successfully, all links are displayed, and each link navigates to the correct destination when clicked.")
    @Severity(SeverityLevel.NORMAL)
    public void validateExoTechnologyPageLoadLinksNavigation_WM_222() {
        try {
            ExoTechnologyPage exoTechnologyPage = page.getInstance(ExoTechnologyPage.class);
            // Load test data (not passed to PO method, but available if needed for future expansion)
            JSONObject testData = JsonFileReader.getTestData(testDataPath, "WM-222");
            // Call the comprehensive PO method for this scenario
            exoTechnologyPage.validatePageLoadLinksAndNavigation();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            assert false : "Test data loading failed: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test execution failed: " + e.getMessage();
        }
    }
}
