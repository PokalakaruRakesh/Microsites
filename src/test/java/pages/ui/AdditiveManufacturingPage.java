package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;

public class AdditiveManufacturingPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By additiveManufacturingHeader = By.cssSelector("div.infoBanner_infoBanner__42tGX.infoBanner_headerBanner__1iIRP");
    private By standardsLink = By.linkText("standards");
    private By learnMoreAboutASTMCompassButton = By.linkText("learn more about astm compass");
    private By learnMoreAboutTrainingButton = By.linkText("learn more about training");
    private By industryLink = By.linkText("industry");
    private By learnMoreAboutASTMSpecBuilderLink = By.linkText("Learn More About ASTM SpecBuilder®");
    private By icamConferenceButton = By.linkText("icam conference");
    private By contactUsButton = By.linkText("contact us");

    public AdditiveManufacturingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Step("Validate Additive Manufacturing page load, presence of all links, and navigation flow")
    public void validatePageLoadLinksAndNavigation() {
        // Wait for header to be visible
        WaitStatementUtils.waitForElementToBeVisible(driver, driver.findElement(additiveManufacturingHeader), 15);

        // Verify presence and click 'standards' link
        WebElement standards = driver.findElement(standardsLink);
        WaitStatementUtils.waitForElementToBeClickable(driver, standards, 10);
        standards.click();
        driver.navigate().back();

        // Verify presence and click 'learn more about astm compass' button
        WebElement compassBtn = driver.findElement(learnMoreAboutASTMCompassButton);
        WaitStatementUtils.waitForElementToBeClickable(driver, compassBtn, 10);
        compassBtn.click();
        driver.navigate().back();

        // Verify presence and click 'learn more about training' button
        WebElement trainingBtn = driver.findElement(learnMoreAboutTrainingButton);
        WaitStatementUtils.waitForElementToBeClickable(driver, trainingBtn, 10);
        trainingBtn.click();
        driver.navigate().back();

        // Verify presence and click 'industry' link (inside ICAM section)
        WebElement industry = driver.findElement(industryLink);
        WaitStatementUtils.waitForElementToBeClickable(driver, industry, 10);
        industry.click();
        driver.navigate().back();

        // Verify presence and click 'Learn More About ASTM SpecBuilder®' link
        WebElement specBuilder = driver.findElement(learnMoreAboutASTMSpecBuilderLink);
        WaitStatementUtils.waitForElementToBeClickable(driver, specBuilder, 10);
        specBuilder.click();
        driver.navigate().back();

        // Verify presence and click 'icam conference' button
        WebElement icamBtn = driver.findElement(icamConferenceButton);
        WaitStatementUtils.waitForElementToBeClickable(driver, icamBtn, 10);
        icamBtn.click();
        driver.navigate().back();

        // Verify presence and click 'contact us' button in footer
        WebElement contactUs = driver.findElement(contactUsButton);
        WaitStatementUtils.waitForElementToBeClickable(driver, contactUs, 10);
        contactUs.click();
    }
}
