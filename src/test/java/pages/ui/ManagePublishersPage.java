package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;
import com.astm.commonFunctions.Common;

public class ManagePublishersPage extends BasePage {

    // Locators
    private By sidebarManagePublishersLink = By.id("manage-publishers");
    private By publishersListTable = By.id("publishers-list");
    private By actionsDropdownToggle = By.xpath("//table[@id='publishers-list']//tr/td[last()]//a[contains(@class,'dropdown-toggle')]");
    private By viewDropdownItem = By.xpath("//a[contains(@class, 'dropdown-item') and contains(text(), 'View')]");

    public ManagePublishersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Complete flow: Navigates to Manage Publishers section and verifies that the View button is displayed for each publisher listed.
     * @return true if View button is displayed for all publishers, false otherwise
     */
    @Step("Navigate to Manage Publishers and verify View button for each publisher")
    public boolean verifyViewButtonDisplayedForAllPublishers() {
        try {
            // Click on Manage Publishers in sidebar
            WaitStatementUtils.waitForElementToBeClickable(driver, getElement(sidebarManagePublishersLink));
            getElement(sidebarManagePublishersLink).click();

            // Wait for publishers list table to be visible
            WaitStatementUtils.waitForElementToBeVisible(driver, getElement(publishersListTable));

            // Get all action dropdown toggles (one per publisher row)
            List<WebElement> actionToggles = getElements(actionsDropdownToggle);
            if (actionToggles == null || actionToggles.isEmpty()) {
                return false;
            }

            // For each publisher row, open the actions dropdown and verify View item is present
            for (WebElement toggle : actionToggles) {
                WaitStatementUtils.waitForElementToBeClickable(driver, toggle);
                toggle.click();
                // Wait for View dropdown item to be visible
                List<WebElement> viewItems = driver.findElements(viewDropdownItem);
                boolean viewFound = false;
                for (WebElement view : viewItems) {
                    if (view.isDisplayed()) {
                        viewFound = true;
                        break;
                    }
                }
                if (!viewFound) {
                    return false;
                }
                // Optionally close the dropdown (if needed)
                toggle.click();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
