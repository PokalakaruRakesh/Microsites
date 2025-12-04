package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;

public class UASDroneStandardsPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By headerBanner = By.cssSelector("div.infoBanner_infoBanner__42tGX.infoBanner_headerBanner__1iIRP");
    private By astmCompassLearnMoreButton = By.linkText("learn more about astm compass");
    private By droneAdvisoryContactUsButton = By.xpath("//div[contains(@class,'mediaTextGrid_content-section__Bn_j_')]//a[text()='contact us']");
    private By committeeF38Button = By.linkText("committee f38");
    private By footerContactUsButton = By.xpath("//div[contains(@class,'infoBanner_footerBanner__UzP8l')]//a[text()='contact us']");

    public UASDroneStandardsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Validate UAS Drone Standards page load, presence of all links, and navigation flow")
    public void validatePageLoadLinksAndNavigation() {
        // Wait for header to be visible
        WaitStatementUtils.waitForElementToBeVisible(driver, driver.findElement(headerBanner), 15);

        // Verify presence and click 'learn more about astm compass' button
        WebElement compassBtn = driver.findElement(astmCompassLearnMoreButton);
        WaitStatementUtils.waitForElementToBeClickable(driver, compassBtn, 10);
        compassBtn.click();
        driver.navigate().back();

        // Verify presence and click 'contact us' button in Drone Advisory Services section
        WebElement advisoryContactBtn = driver.findElement(droneAdvisoryContactUsButton);
        WaitStatementUtils.waitForElementToBeClickable(driver, advisoryContactBtn, 10);
        advisoryContactBtn.click();
        driver.navigate().back();

        // Verify presence and click 'committee f38' button
        WebElement committeeF38Btn = driver.findElement(committeeF38Button);
        WaitStatementUtils.waitForElementToBeClickable(driver, committeeF38Btn, 10);
        committeeF38Btn.click();
        driver.navigate().back();

        // Verify presence and click footer 'contact us' button
        WebElement footerContactBtn = driver.findElement(footerContactUsButton);
        WaitStatementUtils.waitForElementToBeClickable(driver, footerContactBtn, 10);
        footerContactBtn.click();
    }
}
