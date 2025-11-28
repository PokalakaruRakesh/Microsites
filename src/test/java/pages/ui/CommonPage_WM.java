
package pages.ui;

import base.utils.ReusableMethods;
import base.utils.ScreenshotUtil;
import base.utils.WaitStatementUtils;

import com.astm.commonFunctions.WCMSICommon;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;


public class CommonPage_WM extends BasePage {

    public CommonPage_WM(WebDriver driver) {
        super(driver);

    }
    public By learnMore = By.xpath("//a[text()='Learn More']");
    private String menu = "(//button[contains(text(),'[MENU]')])[1]";
    private String submenu = "(//a[contains(@class,'submenuTitle') and contains(text(),'[SUBMENU]')])[2]";
    private String learnMoreLink = "//h3[text()='[HEADER]']/../a[text()='Learn More']|//h5[text()='[HEADER]']/../..//span[text()='Learn More']";
    private String contactUsBtn = "//*[contains(text(),'[HEADER]')]/../..//a[contains(text(),'Contact Us')]";
    private String committeesAndStandardsList = "//div[contains(@class,'textCard_certificationCardContent')]//li/a";


    public By getLearnMoreLink(String header) {
        return By.xpath(learnMoreLink.replace("[HEADER]", header));
    }
    public By getContactUsButton(String header) {
        return By.xpath(contactUsBtn.replace("[HEADER]", header));
    }
    public By getMenu(String menuName) {
        return By.xpath(menu.replace("[MENU]", menuName));
    }
    public By getSubmenu(String submenuName) {
        return By.xpath(submenu.replace("[SUBMENU]", submenuName));
    }
    public List<WebElement> getCommitteesAndStandardsList() {
        return driver.findElements(By.xpath(committeesAndStandardsList));
    }


    @Step("Validate {message} redirects to the correct page")
    public Boolean ValidateLink(By locator, String link, String expectedTitle) {
        try {
            WebElement element = getElement(locator);
            ReusableMethods.scrollIntoView(element, driver);
            WaitStatementUtils.waitForElementToBeClickable(driver, element,3);
            ScreenshotUtil.takeScreenshotForAllure(driver);
            WCMSICommon.JSClick(element,driver);
            String originalWindow = driver.getWindowHandle();
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    if (driver.getCurrentUrl().startsWith("chrome-extension://")) {
                        continue;
                    }
                    break;
                }
            }
            String pageTitle = driver.getTitle();
            ScreenshotUtil.takeScreenshotForAllure(driver);
            Assert.assertTrue(driver.getCurrentUrl().contains(link));
            if(driver.getTitle().contains("404 Not Found")){
                Assert.fail("Page returned 404 Not Found instead of the expected title.");
            }else if (driver.getCurrentUrl().contains(".pdf") && driver.getTitle().contains("")){
                System.out.println("This is pdf file");
            }else if(pageTitle != null && !pageTitle.trim().isEmpty()){
                Assert.assertTrue(pageTitle.contains(expectedTitle));
            }
            if (windowHandles.size() == 3 || windowHandles.size() == 2){
                driver.close();
                driver.switchTo().window(originalWindow);
            }else {
                driver.navigate().back();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Validation failed due to exception: " + e.getMessage());
        }
        return false;
    }

    @Step("Navigate to page")
    public void navigateToPage(String menu,String submenu) {
        try {
            WaitStatementUtils.explicitWaitForVisibility(driver,getElement(getMenu(menu)),10);
            getElement(getMenu(menu)).click();
            ScreenshotUtil.takeScreenshotForAllure(driver);
            WCMSICommon.waitForSec(2);
            WaitStatementUtils.explicitWaitForVisibility(driver,getElement(getSubmenu(submenu)),10);
            getElement(getSubmenu(submenu)).click();
            WCMSICommon.waitForSec(4);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Step("Validate Multiple Links")
    public void validateMultipleLinks(List<WebElement> links) {
        List<String> brokenLinks = new ArrayList<>();
        try {
            String parentWindow = driver.getWindowHandle();
            for (WebElement link : links) {
                String linkText = link.getText();
                String href = link.getAttribute("href");
                try {
                    ReusableMethods.scrollIntoView(link, driver);
                    link.click();
                    WCMSICommon.waitForSec(2);
                    for (String windowHandle : driver.getWindowHandles()) {
                        if (!windowHandle.equals(parentWindow)) {
                            driver.switchTo().window(windowHandle);
                            break;
                        }
                    }
                    String title = driver.getTitle();
                    String currentUrl = driver.getCurrentUrl();
                    if (title.contains("404") || title.contains("Not Found") || currentUrl.contains("404")) {
                        brokenLinks.add("Broken link: " + linkText + " | URL: " + href);
                    }
                    if (!driver.getWindowHandle().equals(parentWindow)) {
                        driver.close();
                    }

                    driver.switchTo().window(parentWindow);
                    WCMSICommon.waitForSec(2);

                } catch (Exception innerEx) {
                    brokenLinks.add("Exception while checking link: " + linkText + " | URL: " + href
                            + " | Error: " + innerEx.getMessage());
                }
            }
            if (!brokenLinks.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("\n=== BROKEN LINKS FOUND ===\n");
                for (String msg : brokenLinks) {
                    sb.append(msg).append("\n");
                }
                Assert.fail(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected error in validateMultipleLinks(): " + e.getMessage());
        }
    }
    @Step("Verify Header And Content")
    public void verifyHeaderAndContent() {

        List<WebElement> headers = driver.findElements(
                By.xpath("//h1 | //h2 | //h3 | //h4 | //h5 | //h6"));
        List<String> failedHeaders = new ArrayList<>();
        for (WebElement header : headers) {
            String headerText = header.getText().trim();
            if (headerText.isEmpty()) {
                continue;
            }
            WebElement content;
            try {
                content = header.findElement(
                        By.xpath(
                                "following::*[" +
                                        ".//text()[normalize-space()] " +
                                        "and not(self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6) " +
                                        "and not(self::a or self::button or self::svg or self::img) " +
                                        "and not(contains(@class,'btn'))" +
                                        "][1]"));
            } catch (Exception e) {
                failedHeaders.add("No content under header: " + headerText);
                continue;
            }
            String contentText = content.getText().trim();
            if (contentText.isEmpty()) {
                failedHeaders.add("No supporting text for header: " + headerText);
            } else {
                System.out.println("PASS: " + headerText + " -> " + contentText);
            }
        }
        if (!failedHeaders.isEmpty()) {
            System.out.println("------------ FAILED HEADERS ------------");
            for (String fail : failedHeaders) {
                System.out.println(fail);
            }
            throw new AssertionError("Header validation failed");
        }
    }
    @Step("Verify Header And Content")
    public void verifyHeaderAndContent1() {

        List<WebElement> headers = driver.findElements(
                By.xpath("//h1 | //h2 | //h3 | //h4 | //h5 | //h6")
        );

        List<String> failed = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {

            WebElement header = headers.get(i);
            String headerText = header.getText().trim();

            if (headerText.isEmpty()) {
                continue;
            }

            WebElement nextHeader = (i + 1 < headers.size()) ? headers.get(i + 1) : null;

            List<WebElement> followingElements = header.findElements(By.xpath("following::*"));
            boolean foundContent = false;

            for (WebElement el : followingElements) {

                if (nextHeader != null && el.equals(nextHeader)) {
                    break;
                }
                String tag = el.getTagName().toLowerCase();

                if (!(tag.equals("div") || tag.equals("p") || tag.equals("li"))) {
                    continue;
                }

                if (el.findElements(By.xpath(".//a | .//button | .//img | .//svg")).size() > 0) {
                    continue;
                }

                String text = el.getText().trim();

                if (!text.isEmpty()) {
                    System.out.println("PASS: " + headerText + " -> " + text);
                    foundContent = true;
                    break;
                }
            }

            if (!foundContent) {
                failed.add("No content under header: " + headerText);
            }
        }

        if (!failed.isEmpty()) {
            System.out.println("\n------------ FAILED HEADERS ------------");
            for (String msg : failed) {
                System.out.println(msg);
            }
            throw new AssertionError("Header validation failed");
        }
    }
    @Step("Verify Header And Content")
    public void verifyHeaderAndContent2() {

        List<WebElement> headers = driver.findElements(
                By.xpath("//h2 | //h3 | //h4 | //h5 | //h6")
        );

        List<String> failed = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {

            WebElement header = headers.get(i);
            String headerText = header.getText().trim();
            if (headerText.isEmpty()) {
                headerText = "[EMPTY HEADER " + (i + 1) + "]";
            }

            WebElement nextHeader = (i + 1 < headers.size()) ? headers.get(i + 1) : null;

            List<WebElement> following = header.findElements(By.xpath("following::*"));
            boolean foundContent = false;

            for (WebElement el : following) {

                if (nextHeader != null && el.equals(nextHeader)) {
                    break;
                }

                String tag = el.getTagName().toLowerCase();

                if (tag.equals("p") || tag.equals("li")) {

                    String text = el.getText().trim();

                    if (!text.isEmpty()) {
                        System.out.println("PASS: " + headerText + " -> " + text);
                        foundContent = true;
                        break;
                    }
                }
            }

            if (!foundContent) {
                failed.add("No <p> or <li> content under header: " + headerText);
            }
        }

        if (!failed.isEmpty()) {
            System.out.println("\n------------ FAILED HEADERS ------------");
            for (String msg : failed) {
                System.out.println(msg);
            }
            throw new AssertionError("Header validation failed");
        }
    }


}
