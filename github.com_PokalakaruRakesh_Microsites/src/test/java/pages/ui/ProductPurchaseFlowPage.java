package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import com.astm.commonFunctions.WCMSICommon;

public class ProductPurchaseFlowPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By signInButton = By.xpath("//span[contains(text(),'Sign In')]");
    private By signInForm = By.id("loginModel");
    private By usernameInput = By.id("userName");
    private By passwordInput = By.id("encPass");
    private By signInSubmitButton = By.xpath("//button[text()='Sign In']");
    private By searchBox = By.xpath("(//input[@aria-label='Search'])[2]");
    private By searchIcon = By.xpath("(//button[@type='submit'])[2]");
    private By productResultTitle = By.xpath("//h4[contains(@class,'searchComponent_title')]/a");
    private By addToCartButton = By.xpath("//button[@title='Add to Cart']");
    private By checkoutButton = By.xpath("//a[@data-role='proceed-to-checkout']");
    // Billing Address
    private By firstNameInput = By.id("SKRUU64");
    private By lastNameInput = By.id("Q6V5FVD");
    private By streetAddressLine1Input = By.id("BUD2GX1");
    private By streetAddressLine2Input = By.id("IA43YQC");
    private By streetAddressLine3Input = By.id("QIBIEGI");
    private By cityInput = By.id("OR5K39M");
    private By stateInput = By.id("YI4J6AT");
    private By stateDropdown = By.id("JHLDM1W");
    private By postalCodeInput = By.id("R0V3M0C");
    private By countryDropdown = By.id("LGRG87E");
    private By phoneInput = By.id("ND09BPX");
    private By continueButton = By.xpath("//button[span[text()='Continue']]");
    // Payment (Braintree iframes)
    private By cardNumberIframe = By.id("braintree-hosted-field-number");
    private By cvvIframe = By.id("braintree-hosted-field-cvv");
    private By expirationDateIframe = By.id("braintree-hosted-field-expirationDate");
    // Place Order
    private By placeOrderButton = By.xpath("//button[@type='submit' and span[text()='Place Order']]");
    // Confirmation
    private By orderSuccessMessage = By.xpath("//span[contains(text(),'Order Placed Successfully')]");

    public ProductPurchaseFlowPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Complete end-to-end product purchase flow in ASTM member application")
    public void completeProductPurchaseFlow(String username, String password, String searchTerm, String firstName, String lastName, String street1, String street2, String street3, String city, String state, String postalCode, String country, String phone, String cardNumber, String cvv, String expirationDate) {
        try {
            // Click Sign In
            WaitStatementUtils.waitForElementToBeClickable(driver, signInButton);
            driver.findElement(signInButton).click();

            // Wait for Sign In Form
            WaitStatementUtils.waitForElementToBeVisible(driver, signInForm);
            driver.findElement(usernameInput).clear();
            driver.findElement(usernameInput).sendKeys(username);
            driver.findElement(passwordInput).clear();
            driver.findElement(passwordInput).sendKeys(password);
            driver.findElement(signInSubmitButton).click();

            // Wait for search box
            WaitStatementUtils.waitForElementToBeVisible(driver, searchBox);
            driver.findElement(searchBox).clear();
            driver.findElement(searchBox).sendKeys(searchTerm);
            driver.findElement(searchIcon).click();

            // Wait for product result and select
            WaitStatementUtils.waitForElementToBeClickable(driver, productResultTitle);
            driver.findElement(productResultTitle).click();

            // Add to Cart
            WaitStatementUtils.waitForElementToBeClickable(driver, addToCartButton);
            driver.findElement(addToCartButton).click();

            // Checkout
            WaitStatementUtils.waitForElementToBeClickable(driver, checkoutButton);
            driver.findElement(checkoutButton).click();

            // Billing Address
            WaitStatementUtils.waitForElementToBeVisible(driver, firstNameInput);
            driver.findElement(firstNameInput).clear();
            driver.findElement(firstNameInput).sendKeys(firstName);
            driver.findElement(lastNameInput).clear();
            driver.findElement(lastNameInput).sendKeys(lastName);
            driver.findElement(streetAddressLine1Input).clear();
            driver.findElement(streetAddressLine1Input).sendKeys(street1);
            driver.findElement(streetAddressLine2Input).clear();
            driver.findElement(streetAddressLine2Input).sendKeys(street2);
            driver.findElement(streetAddressLine3Input).clear();
            driver.findElement(streetAddressLine3Input).sendKeys(street3);
            driver.findElement(cityInput).clear();
            driver.findElement(cityInput).sendKeys(city);
            driver.findElement(stateInput).clear();
            driver.findElement(stateInput).sendKeys(state);
            driver.findElement(postalCodeInput).clear();
            driver.findElement(postalCodeInput).sendKeys(postalCode);
            driver.findElement(phoneInput).clear();
            driver.findElement(phoneInput).sendKeys(phone);
            // Country dropdown
            driver.findElement(countryDropdown).click();
            driver.findElement(countryDropdown).sendKeys(country);
            // Continue
            WaitStatementUtils.waitForElementToBeClickable(driver, continueButton);
            driver.findElement(continueButton).click();

            // Payment (Braintree hosted fields)
            // Switch to card number iframe
            driver.switchTo().frame(driver.findElement(cardNumberIframe));
            WebElement cardNumberField = driver.findElement(By.name("credit-card-number"));
            cardNumberField.clear();
            cardNumberField.sendKeys(cardNumber);
            driver.switchTo().defaultContent();

            // Switch to CVV iframe
            driver.switchTo().frame(driver.findElement(cvvIframe));
            WebElement cvvField = driver.findElement(By.name("cvv"));
            cvvField.clear();
            cvvField.sendKeys(cvv);
            driver.switchTo().defaultContent();

            // Switch to Expiration Date iframe
            driver.switchTo().frame(driver.findElement(expirationDateIframe));
            WebElement expDateField = driver.findElement(By.name("expiration"));
            expDateField.clear();
            expDateField.sendKeys(expirationDate);
            driver.switchTo().defaultContent();

            // Place Order
            WaitStatementUtils.waitForElementToBeClickable(driver, placeOrderButton);
            driver.findElement(placeOrderButton).click();

            // Wait for confirmation
            WaitStatementUtils.waitForElementToBeVisible(driver, orderSuccessMessage);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Product purchase flow failed: " + e.getMessage(), e);
        }
    }

    public boolean isOrderSuccessMessageDisplayed() {
        try {
            WaitStatementUtils.waitForElementToBeVisible(driver, orderSuccessMessage);
            return driver.findElement(orderSuccessMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
