package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import pages.ui.ProductPurchaseFlowPage;
import base.utils.JsonFileReader;
import tests.ui.base.BaseTest;

public class VerifyProductPurchaseFlow_INT_369 extends BaseTest {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/VerifyProductPurchaseFlow-INT-369.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            // Load test data for INT-369 (first scenario: Valid Product Purchase)
            JSONParser parser = new JSONParser();
            JSONObject allData = (JSONObject) parser.parse(new FileReader(TEST_DATA_PATH));
            JSONObject caseData = (JSONObject) allData.get("VerifyProductPurchaseFlow-INT-369");
            // Use the first scenario for main positive flow
            testData = (JSONObject) ((org.json.simple.JSONArray) caseData.get("TestDataSets")).get(0);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data for INT-369: " + e.getMessage(), e);
        }
    }

    @Test(description = "INT-369: Verify end-to-end product purchase flow in ASTM member application")
    @Description("Verify end-to-end product purchase flow in ASTM member application. The Order Placed Successfully confirmation message should be displayed.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProductPurchaseFlow_INT_369() {
        try {
            ProductPurchaseFlowPage productPurchaseFlowPage = page.getInstance(ProductPurchaseFlowPage.class);

            // Extract test data fields explicitly
            JSONObject user = (JSONObject) testData.get("user");
            String username = (String) user.get("username");
            String password = (String) user.get("password");

            JSONObject product = (JSONObject) testData.get("product");
            String searchTerm = (String) product.get("searchKeyword");

            JSONObject shipping = (JSONObject) testData.get("shipping");
            String firstName = username.split("@")[0]; // Use username prefix as first name (or set as needed)
            String lastName = "Test"; // Placeholder, as not in test data
            String street1 = (String) shipping.get("addressLine1");
            String street2 = (String) shipping.get("addressLine2");
            String street3 = ""; // Not present in test data
            String city = (String) shipping.get("city");
            String state = (String) shipping.get("state");
            String postalCode = (String) shipping.get("zip");
            String country = (String) shipping.get("country");
            String phone = "1234567890"; // Not in test data, placeholder

            JSONObject payment = (JSONObject) testData.get("payment");
            String cardNumber = (String) payment.get("cardNumber");
            String cvv = (String) payment.get("cvv");
            String expirationDate = payment.get("expiryMonth") + "/" + payment.get("expiryYear");

            // Call the comprehensive Page Object method
            productPurchaseFlowPage.completeProductPurchaseFlow(
                username,
                password,
                searchTerm,
                firstName,
                lastName,
                street1,
                street2,
                street3,
                city,
                state,
                postalCode,
                country,
                phone,
                cardNumber,
                cvv,
                expirationDate
            );

            // Assert order confirmation
            Assert.assertTrue(productPurchaseFlowPage.isOrderSuccessMessageDisplayed(), "Order Placed Successfully confirmation message was not displayed.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
