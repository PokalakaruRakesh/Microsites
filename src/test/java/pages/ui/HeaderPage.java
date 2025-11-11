package pages.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderPage extends BasePage{
    public HeaderPage(WebDriver driver) {
        super(driver);
    }
    private By headerSearchIcon = By.xpath("//header//button[contains(@class, 'search')]");


    public boolean isHeaderSearchIconDisplayed() {
        waitForElementPresent(headerSearchIcon);
        return getElement(headerSearchIcon).isDisplayed();
    }
}
