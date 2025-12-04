package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;

public class ContactPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By contactUsLink = By.linkText("contact us");
    private By firstNameInput = By.id("first_name");
    private By lastNameInput = By.id("last_name");
    private By companyInput = By.id("company");
    private By phoneInput = By.id("phone");
    private By emailInput = By.id("email");
    private By countryDropdown = By.id("country_code");
    private By cityInput = By.id("city");
    private By zipInput = By.id("zip");
    private By messageTextarea = By.id("description");
    private By productTypeDropdown = By.id("00NC00000052eAR");
    private By industrySectorDropdown = By.id("industry");
    private By stateProvinceDropdown = By.id("state_code");
    private By submitMessageButton = By.xpath("//button[@type='submit']");

    public ContactPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Complete the contact form flow: click contact, fill first/last name, and submit.
     * This is the comprehensive method for WM-666: Validate contact page.
     */
    @Step("Validate contact page: click contact, enter first/last name, and submit message")
    public void validateContactPageAndSubmit(String firstName, String lastName) {
        // Click on 'contact us' link (assume on homepage or relevant page)
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(contactUsLink), 10);
        driver.findElement(contactUsLink).click();

        // Wait for first name input to be visible
        WaitStatementUtils.waitForElementToBeVisible(driver, driver.findElement(firstNameInput), 10);

        // Enter first name
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);

        // Enter last name
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);

        // Click on Submit Message button
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(submitMessageButton), 10);
        driver.findElement(submitMessageButton).click();
    }
}
