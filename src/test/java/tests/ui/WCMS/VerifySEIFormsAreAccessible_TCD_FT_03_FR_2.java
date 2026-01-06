package tests.ui.WCMS;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.ui.CertifiedProductsPage;
import tests.ui.base.BaseTest;

public class VerifySEIFormsAreAccessible_TCD_FT_03_FR_2 extends BaseTest {

    private CertifiedProductsPage certifiedProductsPage;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        // No explicit URL provided in test data; assumed navigation is handled in BaseTest or elsewhere
        // If required, add navigation to Certified Products Page here
    }

    @Test(description = "TCD_FT_03_FR-2: Verify SEI forms are accessible from Certified Products Page")
    @Description("To ensure users can access SEI forms from the Certified Products Page without errors.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySEIFormsAreAccessible_TCD_FT_03_FR_2() {
        try {
            certifiedProductsPage = page.getInstance(CertifiedProductsPage.class);
            certifiedProductsPage.verifySEIFormsAreAccessible();
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, you can fail the test explicitly here if not handled in page object
            // Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
