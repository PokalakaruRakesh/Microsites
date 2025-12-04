package com.astm.commonFunctions;

import base.utils.ScreenshotUtil;
import base.utils.WaitStatementUtils;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PublicAdminCommons {

    static Logger log = Logger.getLogger(PublicAdminCommons.class.getName());

    /**
     * Match the String with Equals
     *
     * @param validationOnPage
     * @param actualText
     * @param expectedText
     * @return True/False
     */
    public static boolean verifyElementTextOnPage(String validationOnPage, String actualText, String expectedText) {

        return actualText.equals(expectedText);

    }

    /**
     * Match the String with contains
     *
     * @param actual
     * @param expected
     * @return True/False
     */
    public static boolean verifyContentOnPage(String actual, String expected) {

        return actual.contains(expected);

    }
    public static boolean verifyContentOnPage1(String actual, String expected) {

        return expected.contains(actual);

    }

    /**
     * Check Visibility of Element
     *
     * @param driver
     * @param locator
     * @return True/False
     */
    public static boolean isWebElementDisplayed(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            return elm.isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public static void clickonWebElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(locator));
        WebElement elm = driver.findElement(locator);
        if (elm.isDisplayed() == true)
            elm.click();
    }

    /**
     * Select Value from Drop down
     *
     * @param element
     * @param value
     * @return
     */
    /*
     * public static boolean selectValueInDropdown(WebElement element, String value)
     * { try { Select select = new Select(element);
     * select.selectByVisibleText(value);
     * System.out.println(select.getFirstSelectedOption().getText()); return true; }
     * catch (Exception e) { e.getStackTrace(); } return false; }
     */

    /**
     * Get the Selected Option from Dropdown
     *
     * @param element
     * @return String
     */
    public static String getSelectedValuefromDropDown(WebElement element) {
        try {
            Select select = new Select(element);
            System.out.println(select.getFirstSelectedOption().getText());
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public static boolean SelectValueDropDownByIndex(WebElement element, int index) {
        try {
            Select select = new Select(element);
            select.selectByIndex(index);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static boolean clearTxtFieldsendKeys(WebDriver driver, By locator, String keys) {
        try {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(keys);
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    public static String getElementText(WebElement ele) {
        try {
            return ele.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getElementTextByAttribute(WebElement ele, String attribute) {
        try {
            return ele.getAttribute(attribute);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Step("Exception Occured in Test: Message: {message}  ")
    public static void reportFailAssert(String message, Exception realCause) {
        try {
            Assert.fail(message, realCause);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectValueInDropdown(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public static void selectValueInDropdownByVisibleTextWithoutException(WebDriver driver, By Locater, String value) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
        WebElement selectElement = driver.findElement(Locater);
        Select select = new Select(selectElement);
        select.selectByVisibleText(value);
    }

    public static void selectValueInDropdownByIndexWithoutException(WebDriver driver, By Locater, int index) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
        WebElement selectElement = driver.findElement(Locater);
        Select select = new Select(selectElement);
        select.selectByIndex(index);
    }

    public static void waitForElementToDisappear(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForSec(long milsec) {
        try {
            Thread.sleep(milsec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTextOfWebElements(WebDriver driver, By Locater) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
        List<WebElement> elms = driver.findElements(Locater);
        List<String> getTexts = new ArrayList<String>();
        for (WebElement element : elms) {
            getTexts.add(element.getText());
        }
        log.info("Get text for elements :" + getTexts);
        return getTexts;
    }

    public static List<String> getAllDropDownValues(WebDriver driver, By Locater) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
        WebElement selectElement = driver.findElement(Locater);
        Select select = new Select(selectElement);
        List<WebElement> allOptions = select.getOptions();
        List<String> getTexts = new ArrayList<String>();
        for (WebElement element : allOptions) {
            getTexts.add(element.getText());
        }
        return getTexts;
    }

    public static void waitForElementToAppearAndThenDisappear(WebDriver driver, By locater) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locater));
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(locater));
    }

    public static boolean clickAndWaitForOtherElementToAppearRecursion(By elementToClick, By elementToAppear,
                                                                       WebDriver driver) {
        Boolean elementVisible = false;
        for (int i = 0; i <= 10; i++) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(elementToClick));
            driver.findElement(elementToClick).click();
            try {
                new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(elementToAppear));
                return true;
            } catch (Exception e) {
                log.info(e);
                e.printStackTrace();
            }
        }
        return elementVisible;
    }

    public static boolean clickWebElement(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            if (elm.isDisplayed() == true)
                elm.click();
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clickWebElementWithoutWait(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            if (elm.isDisplayed() == true)
                elm.click();
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clickWebElementWithoutAnyWait(WebDriver driver, By locator) {
        try {
            WebElement elm = driver.findElement(locator);
            elm.click();
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clickElementByJavaScript(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean enterTextInTextField(WebDriver driver, By locator, String value, boolean clear) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            if (clear == true)
                elm.clear();
            elm.sendKeys(value);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearTextField(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            elm.clear();
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean waitForElementToAppearThenDisappear(WebDriver driver, By locater) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locater));
            new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(locater));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean waitElementToDisappearFromScreen(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 50).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }
    public static boolean waitElementToDisappearFromScreen(WebDriver driver, WebElement locator) {
        try {
            new WebDriverWait(driver, 50).until(ExpectedConditions.invisibilityOfElementLocated((By) locator));
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static String getTextOfElement(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            log.info("Get Text For Element :" + locator + " : " + elm.getText());
            return elm.getText();
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static boolean selectValueFromDropdown(WebDriver driver, By locator, String value) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            Select select = new Select(elm);
            select.selectByVisibleText(value);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean selectValueInDropdownbyValue(WebDriver driver,By locator, int value) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            Select select = new Select(elm);
            select.selectByValue(String.valueOf(value));
            //elm.click();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            e.getStackTrace();
            return false;
        }
    }

    public static boolean selectValueFromDropdownByIndex(WebDriver driver, By locator, int index) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            Select select = new Select(elm);
            select.selectByIndex(index);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getAllValuesFromDropDown(WebDriver driver, By Locater) {
        List<String> getTexts = new ArrayList<String>();
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            WebElement selectElement = driver.findElement(Locater);
            Select select = new Select(selectElement);
            List<WebElement> allOptions = select.getOptions();
            for (WebElement element : allOptions) {
                getTexts.add(element.getText());
            }
            return getTexts;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return getTexts;
        }
    }

    public static List<String> getAllValuesFromDropDownWithTrimedOptions(WebDriver driver, By Locater) {
        List<String> getTexts = new ArrayList<String>();
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            WebElement selectElement = driver.findElement(Locater);
            Select select = new Select(selectElement);
            List<WebElement> allOptions = select.getOptions();
            for (WebElement element : allOptions) {
                getTexts.add(element.getText().trim());
            }
            return getTexts;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return getTexts;
        }
    }

    public static String getMeElementFromListExcept(List<String> list, String elementToExclude) {
        for (String element : list) {
            if (!(elementToExclude.equals(element)) && !(element.equals("Please Select")))
                return element;
        }
        return null;
    }

    public static String getSelectedValueFromDropDown(WebDriver driver, By Locater) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
        WebElement selectElement = driver.findElement(Locater);
        Select select = new Select(selectElement);
        WebElement option = select.getFirstSelectedOption();
        String defaultItem = option.getText();
        return defaultItem;
    }

    public static boolean clickWebElement(WebDriver driver, By locator, boolean scrollToElement) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            if (elm.isDisplayed() == true) {
                if (scrollToElement == true) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView(false);", elm);
                }
                elm.click();
            }
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getAttributeValueForElement(WebDriver driver, By Locater) {

            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            List<WebElement> elms = driver.findElements(Locater);
            List<String> getTexts = new ArrayList<String>();
            for (WebElement element : elms) {
                getTexts.add(element.getAttribute("value"));
            }
            log.info("Get text for elements :" + getTexts);
            return getTexts;

            /*new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            WebElement element = driver.findElement(Locater);
            String attributeValue = element.getAttribute("value");
            log.info("Attribute Value : " + attributeValue);
            return attributeValue;*/
    }

    public static String getAttributeValueForElementWithoutWait(WebDriver driver, By Locater) {
        try {
            WebElement element = driver.findElement(Locater);
            String attributeValue = element.getAttribute("value");
            log.info("Attribute Value : " + attributeValue);
            return attributeValue;
        } catch (Exception e) {
            log.info("Unable to find attribute " + e);
            e.printStackTrace();
            return "";
        }
    }

    public static String getSelectedValueForDropDown(WebDriver driver, By Locater) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            WebElement selectElement = driver.findElement(Locater);
            Select select = new Select(selectElement);
            WebElement option = select.getFirstSelectedOption();
            String defaultItem = option.getText();
            log.info("Default Selected Value for dropdown " + defaultItem);
            return defaultItem;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean scrollToElement(WebDriver driver, By locator) {
        try {
//			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(false);", elm);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean scrollElementToMiddleOfPage(WebDriver driver, By locator) {
        try {
            WebElement elm = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"nearest\"})", elm);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean scrollToTopOfPage(WebDriver driver) {
        try {
//			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, 0);");
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static void scrollToOffSet(WebDriver driver, String X, String Y) {
        try {
//			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(" + X + ","+ Y + ");");
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
        }
    }

    public static boolean scrollToElement(WebDriver driver, By locator, String argument) {
        try {
//			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(" + argument + ");", elm);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean scrollToElement(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(false);", element);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean scrollIntoView(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isWebElementPresentOnPage(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            return elm.isDisplayed();
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isWebElementPresentOnPage(WebDriver driver, By locator, int timeInSec) {
        try {
            new WebDriverWait(driver, timeInSec).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            return elm.isDisplayed();
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isElementLocatedOnPage(WebDriver driver, By locator, int timeInSec) {
        try {
            WebElement elm = driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getListWithoutLeadingAndTrailingSpace(List<String> listToUpdate) {
        List<String> updatedList = new ArrayList<String>();
        for (String element : listToUpdate) {
            String new_element = element.trim();
            updatedList.add(new_element);
            log.info("Removed space :" + new_element);
        }
        return updatedList;
    }

    public static boolean waitForVisible(WebDriver driver, By locator, int sec) {
        try {
            new WebDriverWait(driver, sec).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean handleSpinnerIconOnMagento(WebDriver driver, By locator) {
        try {
           boolean  found = driver.findElements(locator).size()>0;
            if (found) {
                new WebDriverWait(driver, 100).pollingEvery(Duration.ofMillis(400))
                        .until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));

                return true;
            }
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        }
    }



    /**
     * Method used to handle loader and click on the element
     *
     * @param driver
     * @param locator
     */
    public static boolean loaderClick(WebDriver driver, By locator) {
        int count = 0;
        if (waitForVisible(driver, locator, 3))
            while (count < 6) {
                waitForSec(1000);
                count++;
                if (clickElement(locator, driver))
                    return true;
            }
        return false;
    }

    /**
     * Method used to handle loader and click on the element
     *
     * @param element
     */
    public static boolean loaderClick(WebElement element) {
        int count = 0;
        while (count < 6) {
            waitForSec(1000);
            count++;
            if (clickElement(element))
                return true;
        }
        return false;
    }

    /**
     * Method used by loaderClick locally
     *
     * @param locator
     * @param driver
     * @return
     */
    private static boolean clickElement(By locator, WebDriver driver) {
        try {
            driver.findElement(locator).click();
            return true;
        } catch (ElementClickInterceptedException exception) {
            return false;
        }
    }

    /**
     * Method used by loaderClick locally
     *
     * @param element
     * @return
     */
    public static boolean clickElement(WebElement element) {
        try {
            element.click();
            return true;
        } catch (ElementClickInterceptedException exception) {
            return false;
        }
    }

    public static void clickElementIfVisible(WebDriver driver, By locator) {
        try {
            WebElement elm = driver.findElement(locator);
            if (elm.isDisplayed() == true)
                elm.click();
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
        }
    }

    public static String getValueForAttributeForElement(WebDriver driver, By Locater) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            WebElement element = driver.findElement(Locater);
            String value = element.getAttribute("value");
            log.info("Value Attribute :" + value);
            return value;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static String getValueOfAttributeForElement(WebDriver driver, By Locater, String attribute) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            WebElement element = driver.findElement(Locater);
            String value = element.getAttribute(attribute);
            log.info(attribute + " Attribute :" + value);
            return value;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isWebElementIsSelected(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            return elm.isSelected();
        } catch (Exception e) {
            return false;
        }

    }

    public static int numberOfELements(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            List<WebElement> elm = driver.findElements(locator);
            return elm.size();
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return 0;
        }
    }

    public static void scrollAndTakScreenShot(WebDriver driver, By locator) {
        try {
            scrollToElement(driver, locator);
            PublicCommon.waitForSec(3);
            ScreenshotUtil.takeScreenshotForAllure(driver);
        } catch (IOException e) {
            log.info(e);
            e.printStackTrace();
        }
    }

    public static List<WebElement> getListOfWebElementsWithoutWait(WebDriver driver, By locator) {
        List<WebElement> elms = driver.findElements(locator);
        return elms;
    }

    public static boolean isWebElementDisabled(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            return elm.isEnabled();
        } catch (Exception e) {
            log.info(e);
            return false;
        }
    }

    public static boolean isWebElementEnabled(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elm = driver.findElement(locator);
            return elm.isEnabled();
        } catch (Exception e) {
            log.info(e);
            return false;
        }
    }

    public static String getCurrentDateWithFormat(String format) {
        try {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            //formatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
            formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            String strDate = formatter.format(date);
            return strDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateByAddSubtractingMonth(String date, String format, int month) {

        try {
            final Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
            cal.setTime(sdf.parse(date));
            cal.add(Calendar.MONTH, month);
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateByAddSubtractingDate(String date, String format, int dDate) {

        try {
            final Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
            cal.setTime(sdf.parse(date));
            cal.add(Calendar.DATE, dDate);
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getYesterdayDate(String format) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        return dateFormat.format(cal.getTime());
    }

    public static String getPreviousDateBySubtractingDays(String format, int days) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        return dateFormat.format(cal.getTime());
    }

    public static String getTomorrowDate(String format) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        return dateFormat.format(cal.getTime());
    }

    public static String getExpectedDate(String format, int dDays) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +dDays);
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        return dateFormat.format(cal.getTime());
    }

    public static void refreshPageTimes(WebDriver driver, int timeToRefresh) {
        for (int i = 0; i < timeToRefresh; i++) {
            driver.navigate().refresh();
        }
    }

    public static boolean openUrl(WebDriver driver, String url) {
        try {
            driver.get(url);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean switchToIframe(WebDriver driver, String id) {
        try {
            driver.switchTo().frame(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }



    public static boolean switchToDefaultFrame(WebDriver driver) {
        try {
            driver.switchTo().defaultContent();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }
    public static boolean openNewTabAndSwitch(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1)); // switches to new tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }
    public static boolean openNewTab(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.open()");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }
    public static boolean switchToNewOpenedTab(WebDriver driver) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1)); // switches to new tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean closeOriginalAndswitchToNewOpenedTab(WebDriver driver) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            if(tabs.size()>1) {
                driver.switchTo().window(tabs.get(0));
                driver.close();
                tabs = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(0));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean closeNewTabAndSwitchToOriginalTab(WebDriver driver) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0)); // switches to older tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean closeCurrentTabAndSwitchToNewTab(WebDriver driver) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(1)); // switches to older tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean closeCurrentTabAndSwitchToRequiredTab(WebDriver driver,int tab) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(tab)); // switches to older tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean switchToNewOpenedTab(WebDriver driver, int tabIndex) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(tabIndex)); // switches to new tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean closeTab(WebDriver driver) {
        try {
            driver.close(); // closes the tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean switchToTabByIndex(WebDriver driver, int tabIndex) {
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(tabIndex)); // switches to new tab
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static String getTitle(WebDriver driver) {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return "";
        }
    }

    public static boolean clickOnElement(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            if (elm.isDisplayed() == true) {
                elm.click();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean clickAndCheckSateOfToggleButtonIsChanged(WebDriver driver, By locator, By locaterInputTag) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            WebElement elmInputTag = driver.findElement(locaterInputTag);
            boolean initialStateOfToggleButton = elmInputTag.isSelected();
            elm.click();
            if (initialStateOfToggleButton != elmInputTag.isSelected())
                return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean turnOnOrOffToggleButton(WebDriver driver, By locator, By locaterInputTag, boolean state) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            WebElement elmInputTag = driver.findElement(locaterInputTag);
            boolean currentStateOfToggleButton = elmInputTag.isSelected();
            if (currentStateOfToggleButton != state)
                elm.click();
            if (state == elmInputTag.isSelected())
                return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean checkUncheckCheckbox(WebDriver driver, By locator, boolean state) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(locator));
            WebElement elm = driver.findElement(locator);
            boolean currentStateOfCheckboxButton = elm.isSelected();
            if (currentStateOfCheckboxButton != state)
                elm.click();
            if (state == elm.isSelected())
                return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean turnOnOrOffToggleButtonWithJavaScriptClick(WebDriver driver, By locator, By locaterInputTag, boolean state) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            WebElement elm = driver.findElement(locator);
            WebElement elmInputTag = driver.findElement(locaterInputTag);
            boolean currentStateOfToggleButton = elmInputTag.isSelected();
            if (currentStateOfToggleButton != state)
                executor.executeScript("arguments[0].click();", elm);
            if (state == elmInputTag.isSelected())
                return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static String getValueSelectedInDropDown(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(locator));
            Select select = new Select(driver.findElement(locator));
            WebElement option = select.getFirstSelectedOption();
            return option.getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return "";
        }
    }

    public static String getPageTitle(WebDriver driver) {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return "";
        }
    }

    public static boolean refreshPage(WebDriver driver) {
        try {
            driver.navigate().refresh();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e);
            return false;
        }
    }

    public static boolean makeSureElementIsNotPresent(WebDriver driver, By locator) {
        try {
            WebElement elm = driver.findElement(locator);
            return false;
        } catch (Exception e) {
            return true;
        }

    }

    public static String convertDateFromOneFormatToAnother(String dateToConvert, String formatOne, String formatTwo) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(formatOne);
            SimpleDateFormat format2 = new SimpleDateFormat(formatTwo);
            Date date = format1.parse(dateToConvert);
            return format2.format(date);
        } catch (Exception e) {
            log.info(e);
            return "";
        }
    }

    public static List<String> getTextOfWebElementsWithException(WebDriver driver, By Locater) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            List<WebElement> elms = driver.findElements(Locater);
            List<String> getTexts = new ArrayList<String>();
            for (WebElement element : elms) {
                getTexts.add(element.getText());
            }
            return getTexts;
        } catch (Exception e) {
            log.info(e);
            return null;
        }
    }

    public static List<String> getTextOfWebElementsWithExceptionWithoutWait(WebDriver driver, By Locater) {
        try {
            List<WebElement> elms = driver.findElements(Locater);
            List<String> getTexts = new ArrayList<String>();
            for (WebElement element : elms) {
                getTexts.add(element.getText());
            }
            return getTexts;
        } catch (Exception e) {
            log.info(e);
            return null;
        }
    }

    public static boolean isElementPresentOnPageWithException(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            WebElement elm = driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }

    }

    public static boolean waitForGetTextLengthToBeChanged(WebDriver driver, By locator, int length) {
        try {
            new WebDriverWait(driver, 20).until((ExpectedCondition<Boolean>) drive -> drive.findElement(locator).getText().length() > length);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean waitForGetTextToBeChangedTo(WebDriver driver, By locator, String actualValue) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.textToBePresentInElementLocated(locator, actualValue));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getCountOfElement(WebDriver driver, By locator) {
        try {
            return driver.findElements(locator).size();
        } catch (Exception e) {
            return 0;
        }

    }

    public static boolean isWebElementClickable(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            log.info(e);
            return false;
        }
    }

    public static List<String> getSelectedValuesForMultiSelectDropDown(WebDriver driver, By Locater) {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(Locater));
            WebElement selectElement = driver.findElement(Locater);
            Select select = new Select(selectElement);
            List<WebElement> options = select.getAllSelectedOptions();
            List<String> selectedOptionText = new ArrayList<>();
            for (WebElement option : options) {
                selectedOptionText.add(option.getText());
            }
            return selectedOptionText;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return null;
        }
    }

    public static String makeListOfStringIntoString(List<String> listOfString) {
        try {
            String delim = "\n";
            String res = String.join(delim, listOfString);
            return res;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static void clickOnElementWithSelectorShowingMultipleResult(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                element.click();
                break;
            }
        }
    }

    public static WebElement getElementWithSelectorShowingMultipleResult(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        return null;
    }

    public static String getTextForElementWithSelectorShowingMultipleResult(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return element.getText();
            }
        }
        return "";
    }

    public static boolean verifyElementIsNotPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            WebElement elm = driver.findElement(locator);
            return false;
        } catch (Exception e) {
            return true;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public static boolean verifyElementIsPresentWithNewWaitMethod(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            WebElement elm = driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public static boolean sendSeleniumKeys(WebDriver driver, By locator, Keys keys) {
        try {
            driver.findElement(locator).sendKeys(keys);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int generateRandomNumberBetweenSeries(int min,int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
    public static String returnDiscountedAmountForStringPriceWithFormat(String priceInString, String TaxAmountInString,int discountPercentage) {
        try {
            double priceInDouble = Double.parseDouble(priceInString);
            double TaxAmountInDouble = Double.parseDouble(TaxAmountInString);
            double discountedPrice = priceInDouble - (priceInDouble * discountPercentage / 100);
            double RowTotalInDouble=discountedPrice+TaxAmountInDouble;
            return String.format("%.2f", RowTotalInDouble);

        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static String returnDiscountedAmountForStringPriceWithFormat(String priceInString, int discountPercentage) {
        try {
            priceInString = priceInString.replace("$", "");
            double priceInDouble = Double.parseDouble(priceInString);
            double discountedPrice = priceInDouble - (priceInDouble * discountPercentage / 100);
            return String.format("%.2f", discountedPrice);
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static String returnDiscountedAmountWithTaxAmount(String priceInString, int discountPercentage, String TaxAmount) {
        try {
            priceInString = priceInString.replace("$", "");
            double priceInDouble = Double.parseDouble(priceInString);
            TaxAmount = TaxAmount.replace("$", "");
            double TaxpriceInDouble = Double.parseDouble(TaxAmount);

            double discountedPrice = priceInDouble - (priceInDouble * discountPercentage / 100);
            double AfterTaxPrice = discountedPrice + TaxpriceInDouble;
            return String.format("%.2f", AfterTaxPrice);
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static String returnDiscountAmountForStringPriceWithFormat(String priceInString, int discountPercentage) {
        try {
            priceInString = priceInString.replace("$", "");
            double priceInDouble = Double.parseDouble(priceInString);
            double discountedPrice = priceInDouble - (priceInDouble * discountPercentage / 100);
            String discountAmount = String.format("%.2f", priceInDouble - discountedPrice);
            return discountAmount;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static String returnBookDealerDiscountAmount(String priceInString, int discountPercentage) {
        try {
            priceInString = priceInString.replace("$", "");
            double priceInDouble = Double.parseDouble(priceInString);
            double discountedPrice = priceInDouble - (priceInDouble * discountPercentage / 100);
            String discountAmount = String.format("%.2f", priceInDouble - discountedPrice);
            return discountAmount;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return "";
        }
    }

    public static Double returnPriceInDoubleFormat(String priceInString) {
        priceInString = priceInString.replace("$", "").replace("-", "");
        return Double.parseDouble(priceInString);
    }
    public static boolean handleSpinnerIcon(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            boolean found = waitForVisible(driver, locator, 3);
            if (found) {
//				new WebDriverWait(driver, 40).until(ExpectedConditions.invisibilityOfElementLocated(locator));
                new WebDriverWait(driver, 300).pollingEvery(Duration.ofMillis(60000))
                        .until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
                return true;
            }
            return true;
        } catch (Exception e) {
            log.info(e);
            e.printStackTrace();
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        }
    }

    public static Boolean isElementDisplayed(WebElement Element)
    {
        Boolean Value=false;
        try{
            if(Element.isDisplayed())
                Value=true;
            else
                Value=false;
        } catch (Exception e) {
            Value=false;
        }
        return Value;
    }

}
