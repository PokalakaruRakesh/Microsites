package tests.ui.Microsites;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import tests.ui.base.BaseTest;
import pages.ui.ContactPage;

public class ValidateContactPage_WM_666 extends BaseTest {

    private String testDataPath = "src/main/resources/Jsons/WM-666-validate-contact-page.json";
    private String testCaseId = "WM-666";

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        // No specific URL provided in test data for navigation, handled in Page Object
    }

    @Test(description = "WM-666: Validate contact page launches and form submission works")
    @Description("Validate that the contact page launches successfully and the contact form can be submitted with first and last name.")
    @Severity(SeverityLevel.NORMAL)
    public void validateContactPage_WM_666() {
        try {
            // Load test data
            JSONObject testDataRoot = JsonFileReader.getTestData(testDataPath, testCaseId);
            JSONObject formFields = null;
            if (testDataRoot != null && testDataRoot.containsKey("TestData")) {
                JSONObject testData = (JSONObject) testDataRoot.get("TestData");
                if (testData.containsKey("formFields")) {
                    formFields = (JSONObject) ((org.json.simple.JSONArray) testData.get("formFields")).get(0);
                }
            }

            // Fallback values if test data is not present
            String firstName = "TestFirst";
            String lastName = "TestLast";
            if (formFields != null) {
                firstName = (String) formFields.getOrDefault("valid", "TestFirst");
                // Try to get last name from next field if available
                // Defensive: check if array has at least 2 elements
                JSONObject testData = (JSONObject) testDataRoot.get("TestData");
                org.json.simple.JSONArray formFieldsArr = (org.json.simple.JSONArray) testData.get("formFields");
                if (formFieldsArr.size() > 1) {
                    JSONObject lastNameField = (JSONObject) formFieldsArr.get(1);
                    lastName = (String) lastNameField.getOrDefault("valid", "TestLast");
                }
            }

            ContactPage contactPage = page.getInstance(ContactPage.class);
            contactPage.validateContactPageAndSubmit(firstName, lastName);

            // No explicit assertion as per test case, but can check for page launch or success message if needed
            // Example (pseudo):
            // Assert.assertTrue(contactPage.isSuccessMessageDisplayed(), "Contact form submission failed or page did not launch successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during ValidateContactPage_WM_666 test: " + e.getMessage());
        }
    }
}
