package tests.ui.Microsites;

import base.utils.ConfigReader;
import base.utils.JsonFileReader;
import com.astm.commonFunctions.WCMSICommon;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ui.CommonPage_WM;
import pages.ui.StandardsPage_WM;
import tests.ui.base.BaseTest;

public class validateAATCCStandardsPageContentAssetsNavigationAndComponents_WM_324 extends BaseTest {
    static final String jiraTestID = "WM-324";
    JsonFileReader jsonFileReader;
    StandardsPage_WM standardsPage_WM;
    CommonPage_WM commonPageWM;

    @BeforeMethod(alwaysRun = true)
    public void beforeLoginTest() {
        driver.get(ConfigReader.getValue("BASE_URL_WM_ASTM"));
    }
    @Severity(SeverityLevel.NORMAL)
    @TmsLink(jiraTestID)
    @Description("Validate AATCC Standards  Page – Content, Assets, Navigation, and Components")
    @Test(description = jiraTestID
            + ": Validate AATCC Standards  Page – Content, Assets, Navigation, and Components", groups = {""})
    public void validateAATCCStandardsPageContent() {
        try {
            standardsPage_WM = page.getInstance(StandardsPage_WM.class);
            commonPageWM = page.getInstance(CommonPage_WM.class);
            jsonFileReader = new JsonFileReader();
            jsonFileReader.setJiraID(jiraTestID);

            commonPageWM.navigateToPage("Standards","AATCC Standards");
            validateAATCCStandardsPage();
        } catch (Exception e) {
            e.printStackTrace();
            WCMSICommon.reportFailAssert("Failed to Validate AATCC Standards  Page – Content, Assets, Navigation, and Components", e);
        }
        System.out.println("Successfully Validate AATCC Standards  Page – Content, Assets, Navigation, and Components");
    }

    @Step("Validate Link and button available on AATCC Standards  Page")
    public void validateAATCCStandardsPage() {
        try {
            Assert.assertTrue(commonPageWM.ValidateLink(standardsPage_WM.downloadAATCCBrochureBtn, "/other-publishers/aatcc", "AATCC Standards on ASTM Compass"));
            Assert.assertTrue(commonPageWM.ValidateLink(standardsPage_WM.learnMoreAboutASTMCompassBtn, "/standards/compass", "compass"));
            Assert.assertTrue(commonPageWM.ValidateLink(commonPageWM.getContactUsButton("For more information"), "/?ismsaljsauthenabled=true", "Bookings"));
        } catch (Exception e) {
            e.printStackTrace();
            WCMSICommon.reportFailAssert("Failed to Validate the Link and button available on AATCC Standards  Page", e);
        }
    }
}
