package tests.ui.WCMS;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import base.utils.JsonFileReader;
import org.json.simple.JSONObject;
import pages.ui.CacheManagementPage;

/**
 * TestNG test for TC-002: Verify Cache Management Settings under ASTM
 */
public class VerifyCacheManagementSettingsUnderASTM_TC002 {

    private String testDataPath = "src/main/resources/Jsons/verify-cache-management-settings-under-astm-data.json";
    private JSONObject testData;
    private String url;
    private String username;
    private String password;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Load test data for TC-002
        testData = JsonFileReader.getTestData(testDataPath, "TC-002");
        if (testData != null) {
            // Extract individual values as per Rule A
            url = "https://stage.astm.org/admin";
            username = "nganugapenta@astm.org";
            password = "Test@1234!";
        }
    }

    @Test(description = "TC-002: Verify Cache Management Settings under ASTM")
    @Description("Verify that Magento Cache can be flushed successfully via Cache Management in ASTM Admin.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCacheManagementSettingsUnderASTM_TC002() {
        try {
            CacheManagementPage cacheManagementPage = page.getInstance(CacheManagementPage.class);
            boolean isFlushed = cacheManagementPage.flushMagentoCache(url, username, password);
            Assert.assertTrue(isFlushed, "Magento cache was not flushed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during cache management test: " + e.getMessage());
        }
    }
}
