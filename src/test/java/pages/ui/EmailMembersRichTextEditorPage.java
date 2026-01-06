package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import base.utils.WaitStatementUtils;

public class EmailMembersRichTextEditorPage extends BasePage {
    // Locators
    private By editorInput = By.cssSelector("div[data-lexical-editor='true']");
    private By boldButton = By.cssSelector("button[aria-label='Format Bold']");
    private By italicsButton = By.cssSelector("button[aria-label='Format Italics']");
    private By underlineButton = By.cssSelector("button[aria-label='Format Underline']");

    public EmailMembersRichTextEditorPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Complete flow: Enter text and apply bold, italics, and underline formatting in the rich text editor.
     * @param text The text to enter and format
     */
    @Step("Apply bold, italics, and underline formatting to text in the Email Members rich text editor")
    public void applyBoldItalicsUnderlineFormatting(String text) {
        // Wait for editor to be visible
        WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(editorInput), 20);
        WebElement editor = driver.findElement(editorInput);
        editor.click();
        editor.clear();
        editor.sendKeys(text);

        // Select all text (Ctrl+A)
        editor.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));

        // Apply Bold
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(boldButton), 10);
        driver.findElement(boldButton).click();
        // Apply Italics
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(italicsButton), 10);
        driver.findElement(italicsButton).click();
        // Apply Underline
        WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(underlineButton), 10);
        driver.findElement(underlineButton).click();
    }

    /**
     * (Optional) Get formatted text from the editor for verification.
     * @return String representing the HTML content of the editor
     */
    public String getFormattedTextHtml() {
        WebElement editor = driver.findElement(editorInput);
        return editor.getAttribute("innerHTML");
    }
}
