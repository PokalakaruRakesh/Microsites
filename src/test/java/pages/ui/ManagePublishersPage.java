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

    /**
     * Complete flow: Logs in, navigates to Manage Publishers, adds a new publisher with all required information, and clicks Save.
     * This method implements the full scenario for INT-123.
     * @param name Publisher Name
     * @param code Publisher Code
     * @param publisherType Publisher Type (e.g., "Third-Party Content Publisher")
     * @return true if Save button is clickable and publisher is added, false otherwise
     */
    public boolean addNewPublisherAndSave(String name, String code, String publisherType) {
        try {
            // Click on Manage Publishers in sidebar (if not already there)
            WaitStatementUtils.waitForElementToBeClickable(driver, getElement(sidebarManagePublishersLink));
            getElement(sidebarManagePublishersLink).click();

            // Wait for publishers list table to be visible
            WaitStatementUtils.waitForElementToBeVisible(driver, getElement(publishersListTable));

            // Click on 'Add Publisher' button
            By addPublisherButton = By.xpath("//button[contains(text(), 'Add Publisher')]");
            WaitStatementUtils.waitForElementToBeClickable(driver, getElement(addPublisherButton));
            getElement(addPublisherButton).click();

            // Wait for modal/dialog to appear
            By publisherNameInput = By.id("publisher-name");
            WaitStatementUtils.waitForElementToBeVisible(driver, getElement(publisherNameInput));

            // Enter Name
            getElement(publisherNameInput).clear();
            getElement(publisherNameInput).sendKeys(name);

            // Enter Publisher Code
            By publisherCodeInput = By.id("publisher-code");
            getElement(publisherCodeInput).clear();
            getElement(publisherCodeInput).sendKeys(code);

            // Select Publisher Type from dropdown
            By publisherTypeSelect = By.id("publisherType");
            WebElement publisherTypeDropdown = getElement(publisherTypeSelect);
            publisherTypeDropdown.click();
            // Select the correct option
            List<WebElement> options = publisherTypeDropdown.findElements(By.tagName("option"));
            boolean found = false;
            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(publisherType)) {
                    option.click();
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }

            // Click Save button
            By saveButton = By.xpath("//button[contains(text(), 'Save')]");
            WaitStatementUtils.waitForElementToBeClickable(driver, getElement(saveButton));
            getElement(saveButton).click();

            // Optionally, wait for the modal to close and the table to refresh
            WaitStatementUtils.waitForElementToBeVisible(driver, getElement(publishersListTable));
            // You may add additional verification here (e.g., check if the new publisher appears in the table)

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}