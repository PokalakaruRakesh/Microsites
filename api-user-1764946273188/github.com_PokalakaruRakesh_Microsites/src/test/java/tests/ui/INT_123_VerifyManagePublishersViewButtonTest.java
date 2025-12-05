package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import pages.ui.ManagePublishersPage;
import org.json.simple.JSONObject;

public class INT_123_VerifyManagePublishersViewButtonTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/INT-123-verify-manage-publishers-view-button-data.json";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // No URL navigation or login here as per repository pattern.
        // If login is required, it should be handled in the Page Object or via a reusable login utility.
    }

    @Test(description = "INT-123: Verify that the user can access the Manage Publishers section, ensuring that the View button is displayed for each publisher listed.")
    @Description("Verify that the user can access the Manage Publishers section, ensuring that the View button is displayed for each publisher listed.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyManagePublishersViewButtonDisplayed() {
        try {
            // Load test data (for future extensibility, not strictly needed for this test as per Page Object design)
            JSONObject testData = JsonFileReader.getTestData(TEST_DATA_PATH, "INT-123");

            // Initialize page object
            ManagePublishersPage managePublishersPage = page.getInstance(ManagePublishersPage.class);

            // Call comprehensive method to verify View button for all publishers
            boolean allViewButtonsDisplayed = managePublishersPage.verifyViewButtonDisplayedForAllPublishers();

            Assert.assertTrue(allViewButtonsDisplayed, "Not all publishers have the View button displayed in the Manage Publishers section.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during test execution: " + e.getMessage());
        }
    }
}
