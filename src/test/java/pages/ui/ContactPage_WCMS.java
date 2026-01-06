package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;

public class ContactPage_WCMS extends BasePage {

    // Locators
    private By contactUsLink = By.linkText("contact us");
    private By firstNameInput = By.id("first_name");
    private By lastNameInput = By.id("last_name");
    private By submitMessageButton = By.xpath("//button[contains(@class, 'btn-primary') and contains(., 'Submit Message')]");

    // Constructor
    public ContactPage_WCMS(WebDriver driver) {
        super(driver);
    }

    /**
     * Complete the contact form and submit the message. This method covers the entire flow:
     * - Clicks the 'contact us' link
     * - Fills in first name and last name
     * - Clicks the 'Submit Message' button
     *
     * @param firstName The first name to enter
     * @param lastName The last name to enter
     */
    @Step("Complete the contact form and submit message")
    public void completeContactFormAndSubmit(String firstName, String lastName) {
        // Click on 'contact us' link
        WaitStatementUtils.waitForElementToBeClickable(driver, contactUsLink);
        driver.findElement(contactUsLink).click();
        WCMSICommon.waitForSec(2);

        // Enter First Name
        WaitStatementUtils.waitForElementToBeVisible(driver, firstNameInput);
        WebElement firstNameField = driver.findElement(firstNameInput);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Enter Last Name
        WaitStatementUtils.waitForElementToBeVisible(driver, lastNameInput);
        WebElement lastNameField = driver.findElement(lastNameInput);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        ScreenshotUtil.takeScreenshotForAllure(driver);

        // Click on 'Submit Message' button
        WaitStatementUtils.waitForElementToBeClickable(driver, submitMessageButton);
        driver.findElement(submitMessageButton).click();
        ScreenshotUtil.takeScreenshotForAllure(driver);
    }
}
