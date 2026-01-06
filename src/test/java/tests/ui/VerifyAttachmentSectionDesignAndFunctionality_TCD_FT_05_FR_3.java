package tests.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.simple.JSONObject;
import pages.ui.EmailMembersRichTextEditorPage;
import src.main.java.base.utils.JsonFileReader;
import java.nio.file.Paths;

public class VerifyAttachmentSectionDesignAndFunctionality_TCD_FT_05_FR_3 {

    private static final String TEST_DATA_PATH = "src/main/resources/Jsons/verify-attachment-section-design-and-functionality-data.json";
    private JSONObject testData;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Load test data for this test case
        testData = JsonFileReader.getTestData(TEST_DATA_PATH, "VerifyAttachmentSection");
    }

    @Test(description = "TCD_FT_05_FR-3: Verify attachment section design and functionality")
    @Description("To ensure the attachment section design matches the application format and supports file uploads.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyAttachmentSectionDesignAndFunctionality() {
        try {
            EmailMembersRichTextEditorPage emailMembersPage = page.getInstance(EmailMembersRichTextEditorPage.class);

            // Extract the file name from test data (always use explicit extraction)
            JSONObject validFile = (JSONObject) ((org.json.simple.JSONArray) ((JSONObject) testData.get("TestData")).get("ValidFiles")).get(0);
            String fileName = (String) validFile.get("fileName");
            // Assuming test resources are placed under src/test/resources/uploadFiles/
            String filePath = Paths.get("src", "test", "resources", "uploadFiles", fileName).toAbsolutePath().toString();

            // Call the comprehensive page object method
            emailMembersPage.verifyAttachmentSectionDesignAndFunctionality(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }
}
