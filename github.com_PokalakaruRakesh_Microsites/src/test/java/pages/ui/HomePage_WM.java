package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.utils.WaitStatementUtils;
import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;

public class HomePage_WM extends BasePage {

    // Locators
    private By contactUsLink = By.linkText("contact us");
    private By firstNameInput = By.id("first_name");
    private By lastNameInput = By.id("last_name");
    private By submitMessageButton = By.xpath("//button[contains(@class, 'btn-primary') and contains(text(), 'Submit Message')]");
    private By homeLink = By.linkText("Home");
    private By meetWithAstmRepresentativeButton = By.linkText("meet with an astm representative");
    private By helpingOurWorldWorkBetterBanner = By.xpath("//p[contains(text(), 'Helping Our World Work Better')]");

    public HomePage_WM(WebDriver driver) {
        super(driver);
    }

    /**
     * Complete the Home Page validation flow as per WM-777:
     * 1. Click on Contact Us
     * 2. Enter First Name and Last Name
     * 3. Click Submit Message
     * 4. Click Home
     * 5. Click 'meet with an astm representative'
     * 6. Verify 'Helping Our World Work Better' header is displayed
     *
     * @param firstName First Name to enter
     * @param lastName Last Name to enter
     * @return true if banner is displayed, false otherwise
     */
    public boolean validateHomePageFlow(String firstName, String lastName) {
        try {
            // Click on Contact Us
            clickOnMethod(contactUsLink);

            // Enter First Name
            getElement(firstNameInput).clear();
            getElement(firstNameInput).sendKeys(firstName);

            // Enter Last Name
            getElement(lastNameInput).clear();
            getElement(lastNameInput).sendKeys(lastName);

            // Click Submit Message
            clickOnMethod(submitMessageButton);

            // Click Home link
            clickOnMethod(homeLink);

            // Click 'meet with an astm representative' button
            clickOnMethod(meetWithAstmRepresentativeButton);

            // Verify 'Helping Our World Work Better' banner is displayed
            waitForElementPresent(helpingOurWorldWorkBetterBanner);
            WebElement banner = getElement(helpingOurWorldWorkBetterBanner);
            return banner != null && banner.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
