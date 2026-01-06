package tests.ui.WCMS;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import base.utils.JsonFileReader;
import pages.ui.ContactPage_WCMS;
import tests.ui.base.BaseTest;

public class ValidateContactPage_WM_666 extends BaseTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/WM-666-contact-page.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // Load test data for WM-666
        testData = JsonFileReader.getTestData(TEST_DATA_PATH, "WM-666");
        // No URL provided in test data, so no navigation here
    }

    @Test(description = "WM-666: Validate contact page content and submit message")
    @Description("Validate that the contact page allows the user to click on submit message button after entering first and last name.")
    @Severity(SeverityLevel.NORMAL)
    public void validateContactPage_WM_666() {
        try {
            ContactPage_WCMS contactPage = page.getInstance(ContactPage_WCMS.class);

            // Extract test data for first and last name (using first data set as example)
            JSONObject dataSet = null;
            if (testData.containsKey("TestDataSets")) {
                Object dataSetsObj = testData.get("TestDataSets");
                if (dataSetsObj instanceof org.json.simple.JSONArray && ((org.json.simple.JSONArray) dataSetsObj).size() > 0) {
                    dataSet = (JSONObject) ((org.json.simple.JSONArray) dataSetsObj).get(0);
                }
            }
            String firstName = dataSet != null && dataSet.get("name") != null ? dataSet.get("name").toString() : "TestFirstName";
            String lastName = dataSet != null && dataSet.get("name") != null ? dataSet.get("name").toString() : "TestLastName";
            // If the data set has separate first/last name fields, adjust accordingly
            // For now, split the name if possible
            if (firstName.contains(" ")) {
                String[] names = firstName.split(" ", 2);
                firstName = names[0];
                lastName = names.length > 1 ? names[1] : "TestLastName";
            }

            // Call the comprehensive method in the Page Object
            contactPage.completeContactFormAndSubmit(firstName, lastName);

            // No explicit assertion as per requirements; the method will throw if action fails
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Exception occurred in validateContactPage_WM_666: " + e.getMessage();
        }
    }
}
